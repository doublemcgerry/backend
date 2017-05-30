package rz.thesis.server.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import rz.thesis.core.Core;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.actors.concrete.MobileUserActor;
import rz.thesis.server.lobby.actors.concrete.SensorActorConcrete;
import rz.thesis.server.lobby.actors.concrete.SpectatorActorConcrete;
import rz.thesis.server.logger.interfaces.LobbyLoggerInterface;
import rz.thesis.server.logger.interfaces.LobbyManagerInterface;
import rz.thesis.server.logger.interfaces.MainServerLoggerInterface;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.management.ManagementAction;
import rz.thesis.server.serialization.action.reply.AvailableLobbiesReply;

public class LobbiesManager {

	private static final Logger LOGGER = Logger.getLogger(LobbiesManager.class.getName());
	private Map<String, ServerLobby> lobbyMap;
	private MainServerLoggerInterface logger;
	private LobbyManagerInterface lobbyManager;
	private List<Subscriber> waitingRoom;
	private Core core;
	private ExperiencesModule experiencesModule;

	public LobbiesManager(Core core) {
		this.logger = new MainServerLoggerInterface() {

			@Override
			public void logMainServerActivity(String textToLog) {
				LOGGER.debug(textToLog);
			}

			@Override
			public void logDiscoveryServerActivity(String textToLog) {
				LOGGER.debug(textToLog);
			}
		};
		this.lobbyManager = new LobbyManagerInterface() {

			@Override
			public LobbyLoggerInterface createNewLobby(final String lobbyToAdd) {
				// TODO Auto-generated method stub
				return new LobbyLoggerInterface() {

					@Override
					public void logLobbyActivity(String textToLog) {
						LOGGER.debug(lobbyToAdd + " - " + textToLog);

					}
				};
			}
		};
		lobbyMap = new HashMap<String, ServerLobby>();
		waitingRoom = new ArrayList<>();
		this.experiencesModule = this.core.getModule(ExperiencesModule.class);
		this.core = core;
	}

	public UUID generateUUID() {
		return UUID.randomUUID();
	}

	public void handleAction(Subscriber wrapper, Action action) {
		if (action instanceof ManagementAction) {
			ManagementAction manag_action = (ManagementAction) action;
			this.handleManagementAction(wrapper, manag_action);
		}
	}

	public void handleManagementAction(Subscriber wrapper, ManagementAction action) {
		action.execute(this, wrapper);
	}

	public ArrayList<String> getAvailableLobby() {
		ArrayList<String> availableLobbies = new ArrayList<String>();
		for (Map.Entry<String, ServerLobby> entry : lobbyMap.entrySet()) {
			ServerLobby instance = entry.getValue();
			availableLobbies.add(entry.getKey());
			
		}
		return availableLobbies;
	}

	private void createLobby(Subscriber wrapper, String lobby) {
		logger.logMainServerActivity(wrapper.getName() + " has requested to create a Lobby with name " + lobby);
		LobbyLoggerInterface log = lobbyManager.createNewLobby(lobby);
		ServerLobby gameinstance = new ServerLobby(log);
		lobbyMap.put(lobby, gameinstance);

	}

	public void changeLobby(Subscriber wrapper, String lobby, SubscriberType type) {
		switch (type) {
		case SENSOR:
			if (lobbyMap.containsKey(lobby)) {
				ServerLobby instance = lobbyMap.get(lobby);
				instance.addSensor(new SensorActorConcrete(wrapper));
				logger.logMainServerActivity(
						"The SmartWatch " + wrapper.getName() + " has entered in the " + lobby + " Lobby");
				waitingRoom.remove(wrapper);
			}
			break;
		case USER:
			if (lobbyMap.containsKey(lobby)) {
				ServerLobby instance = lobbyMap.get(lobby);
				instance.addUser(new MobileUserActor(wrapper));
				logger.logMainServerActivity(
						"The Mobile Application " + wrapper.getName() + " has entered in the " + lobby + " Lobby");
				waitingRoom.remove(wrapper);
			} else {
				// TODO put error state
				return;
			}
			break;
		default:
			if (lobbyMap.containsKey(lobby)) {
				ServerLobby instance = lobbyMap.get(lobby);
				instance.addSpectator(new SpectatorActorConcrete(wrapper));
				logger.logMainServerActivity(
						"The Spectator " + wrapper.getName() + " has entered in the " + lobby + " Lobby");
				waitingRoom.remove(wrapper);
			} else {
				// TODO put error state
				return;
			}
			break;
		}

		wrapper.setCurrentServerInstance(this.lobbyMap.get(lobby));
	}

	public void exitLobby(Subscriber wrapper) {
		// TODO da sistemare
		wrapper.removeFromServerInstance();
		logger.logMainServerActivity("The Spectator " + wrapper.getName() + " has gone out from the Lobby");
	}

	public void broadcastToWaitingRoom(Action action) {
		for (Subscriber subscriber : waitingRoom) {
			try {
				subscriber.sendAction(action);
			} catch (Exception e) {
				logger.logMainServerActivity("Error while sending to subscriber:" + e.getMessage());
			}
		}
	}

	public void addInWaitingRoom(Subscriber wrapper) {
		waitingRoom.add(wrapper);
	}

}
