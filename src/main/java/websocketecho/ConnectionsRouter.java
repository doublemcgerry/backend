package websocketecho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import interfaces.LobbyLoggerInterface;
import interfaces.LobbyManagerInterface;
import interfaces.MainServerLoggerInterface;
import serialization.action.management.ManagementAction;

public class ConnectionsRouter {
	
	private Map<String,ServerInstance> lobbyMap;
	private MainServerLoggerInterface logger;
	private LobbyManagerInterface lobbyManager;

	public ConnectionsRouter(MainServerLoggerInterface writerInterface, LobbyManagerInterface lobbyAdder) {
		this.logger = writerInterface;
		this.lobbyManager = lobbyAdder;
		lobbyMap = new HashMap<String, ServerInstance>();
	}

	public UUID generateUUID() {
		return UUID.randomUUID();
	}

	public void handleManagementAction(Subscriber wrapper, ManagementAction action) {
		action.execute(this, wrapper);
	}
	
	public ArrayList<String> getAvailableLobby(Subscriber wrapper){
		logger.logMainServerActivity(wrapper.getUUID() + " has requested the list of available lobbies");
		ArrayList<String> availableLobbies = new ArrayList<String>();
		for (Map.Entry<String, ServerInstance> entry : lobbyMap.entrySet()) {
			ServerInstance instance = entry.getValue();
			if(instance.isSmartwatchPresent()){
				availableLobbies.add(entry.getKey());
			}
		}
		return availableLobbies;
	}
	
	private void createLobby(Subscriber wrapper, String zone){
		logger.logMainServerActivity(wrapper.getUUID() + " has requested to create a Lobby with name "+ zone);
		LobbyLoggerInterface log = lobbyManager.createNewLobby(zone);
		ServerInstance gameinstance = new ServerInstance(log);
		lobbyMap.put(zone, gameinstance);
	}

	public void changeLobby(Subscriber wrapper, String zone, SubscriberType type) {
		switch (type) {
			case SMARTWATCH:
				if(lobbyMap.containsKey(zone)){
					ServerInstance instance = lobbyMap.get(zone);
					instance.addSmartwatch(wrapper);
					logger.logMainServerActivity("The SmartWatch "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
				}
				else{
					createLobby(wrapper, zone);
					this.lobbyMap.get(zone).addSmartwatch(wrapper);
					logger.logMainServerActivity("The SmartWatch "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
				}
				break;
			case MOBILE:
				if(lobbyMap.containsKey(zone)){
					ServerInstance instance = lobbyMap.get(zone);
					instance.addMobile(wrapper);
					logger.logMainServerActivity("The Mobile Application "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
				}
				else{
					//TODO put error state
					return;
				}
				break;
			default:
				if(lobbyMap.containsKey(zone)){
					ServerInstance instance = lobbyMap.get(zone);
					instance.addSpectator(wrapper);
					logger.logMainServerActivity("The Spectator "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
				}
				else{
					//TODO put error state
					return;
				}
				break;
		}
		
		wrapper.setCurrentGameInstance(this.lobbyMap.get(zone));
	}

	public void exitLobby(Subscriber wrapper) {
		//TODO da sistemare
		wrapper.removeFromGameInstance();
		logger.logMainServerActivity("The Spectator "+ wrapper.getUUID() + " has gone out from the Lobby");
	}

}
