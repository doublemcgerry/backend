package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

import rz.thesis.core.Core;
import rz.thesis.core.modules.http.HttpSessionsManager;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.auth.SendTokenAction;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class LobbiesManager {

	private static final Logger LOGGER = Logger.getLogger(LobbiesManager.class.getName());
	/***
	 * maps of the lobbies, the key is the username, the value is the specific server lobby
	 */
	private Map<String, ServerLobby> lobbyMap;
	private Map<String, LobbyActor> waitingRoom;
	private Core core;
	private ExperiencesModule experiencesModule;

	public LobbiesManager(Core core) {

		lobbyMap = new HashMap<String, ServerLobby>();
		waitingRoom = new HashMap<>();
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

	public void addLobbyActor(LobbyActor actor) {
		String token = getToken(4);
		synchronized (waitingRoom) {
			while (waitingRoom.containsKey(token)) {
				token = getToken(4);
			}

			waitingRoom.put(token, actor);
		}
		actor.sendAction(new SendTokenAction(token));
	}
	
	/**
	 * creates a new lobby for the subscriber 
	 * @param userActor
	 */
	public void createLobby(Subscriber userActor) {
		if (userActor.getServerSession().isAuthenticated()) {
			LOGGER.debug(
					userActor.getServerSession().getUsername() + " has requested to create a Lobby with name " + userActor.getServerSession().getUsername());
			ServerLobby gameinstance = new ServerLobby(userActor.getServerSession().getUsername());
			lobbyMap.put(userActor.getServerSession().getUsername(), gameinstance);
		} else {
			throw new RuntimeException(
					"Rogue actor tried to screw us by creating a lobby without authentication first");
		}

	}
	/**
	 * broadcast to the subscribers currently connected to the waiting room
	 * @param action
	 */
	public void broadcastToWaitingRoom(Action action) {
		for (Map.Entry<String, LobbyActor> subscriber : waitingRoom.entrySet()) {
			try {
				subscriber.getValue().sendAction(action);
			} catch (Exception e) {
				LOGGER.debug("Error while sending to subscriber:" + e.getMessage());
			}
		}
	}
	
	/***
	 * authenticates the session with the selected device key as the user defined by in the session
	 * of the authenticator subscriber
	 * @param authenticator the currently logged account
	 * @param deviceKey the key of the device to associate to the account
	 */
	public void authenticate(Subscriber authenticator, String deviceKey) {
		if (authenticator.getServerSession().isAuthenticated()){
			String username = authenticator.getServerSession().getUsername();
			if (waitingRoom.containsKey(deviceKey)){
				LobbyActor actor = waitingRoom.get(deviceKey);
				HttpSessionsManager.authenticateSession(actor.getServerSession(), username);
			}
		}else{
			throw new RuntimeException("no authentication, no party");
		}
		
	}

	private static final Random random = new Random();
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";

	public static String getToken(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			token.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}
		return token.toString();
	}

}
