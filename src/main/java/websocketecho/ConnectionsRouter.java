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
	
	private void createLobby(Subscriber wrapper, String lobby){
		logger.logMainServerActivity(wrapper.getName() + " has requested to create a Lobby with name "+ lobby);
		LobbyLoggerInterface log = lobbyManager.createNewLobby(lobby);
		ServerInstance gameinstance = new ServerInstance(log);
		lobbyMap.put(lobby, gameinstance);
	}

	public void changeLobby(Subscriber wrapper, String lobby, SubscriberType type) {
		switch (type) {
			case SMARTWATCH:
				if(lobbyMap.containsKey(lobby)){
					ServerInstance instance = lobbyMap.get(lobby);
					instance.addSmartwatch(wrapper);
					logger.logMainServerActivity("The SmartWatch "+ wrapper.getName() + " has entered in the "+ lobby + " Lobby");
				}
				else{
					createLobby(wrapper, lobby);
					this.lobbyMap.get(lobby).addSmartwatch(wrapper);
					logger.logMainServerActivity("The SmartWatch "+ wrapper.getName() + " has entered in the "+ lobby + " Lobby");
				}
				break;
			case MOBILE:
				if(lobbyMap.containsKey(lobby)){
					ServerInstance instance = lobbyMap.get(lobby);
					instance.addMobile(wrapper);
					logger.logMainServerActivity("The Mobile Application "+ wrapper.getName() + " has entered in the "+ lobby + " Lobby");
				}
				else{
					//TODO put error state
					return;
				}
				break;
			default:
				if(lobbyMap.containsKey(lobby)){
					ServerInstance instance = lobbyMap.get(lobby);
					instance.addSpectator(wrapper);
					logger.logMainServerActivity("The Spectator "+ wrapper.getName() + " has entered in the "+ lobby + " Lobby");
				}
				else{
					//TODO put error state
					return;
				}
				break;
		}
		
		wrapper.setCurrentServerInstance(this.lobbyMap.get(lobby));
	}

	public void exitLobby(Subscriber wrapper) {
		//TODO da sistemare
		wrapper.removeFromServerInstance();
		logger.logMainServerActivity("The Spectator "+ wrapper.getName() + " has gone out from the Lobby");
	}

}
