package websocketecho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import interfaces.AdderInterface;
import interfaces.WriterInterface;
import serialization.action.management.ManagementAction;

public class ConnectionsRouter {
	
	private Map<String,ServerInstance> lobbyMap;
	private WriterInterface writer;
	private AdderInterface lobbyAdder;

	public ConnectionsRouter(WriterInterface writerInterface, AdderInterface lobbyAdder) {
		this.writer = writerInterface;
		this.lobbyAdder = lobbyAdder;
		lobbyMap = new HashMap<String, ServerInstance>();
	}

	public UUID generateUUID() {
		return UUID.randomUUID();
	}

	public void handleManagementAction(Subscriber wrapper, ManagementAction action) {
		action.execute(this, wrapper);
	}
	
	public ArrayList<String> getAvailableLobby(Subscriber wrapper){
		writer.addMainServerText(wrapper.getUUID() + " has requested the list of available lobbies");
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
		writer.addMainServerText(wrapper.getUUID() + " has requested to create a Lobby with name "+ zone);
		ServerInstance gameinstance = new ServerInstance();
		lobbyMap.put(zone, gameinstance);
	}

	public void changeLobby(Subscriber wrapper, String zone, SubscriberType type) {
		switch (type) {
			case SMARTWATCH:
				if(lobbyMap.containsKey(zone)){
					ServerInstance instance = lobbyMap.get(zone);
					instance.addSmartwatch(wrapper);
					writer.addMainServerText("The SmartWatch "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
				}
				else{
					createLobby(wrapper, zone);
					this.lobbyMap.get(zone).addSmartwatch(wrapper);
					writer.addMainServerText("The SmartWatch "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
				}
				break;
			case MOBILE:
				if(lobbyMap.containsKey(zone)){
					ServerInstance instance = lobbyMap.get(zone);
					instance.addMobile(wrapper);
					writer.addMainServerText("The Mobile Application "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
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
					writer.addMainServerText("The Spectator "+ wrapper.getUUID() + " has entered in the "+ zone + " Lobby");
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
		writer.addMainServerText("The Spectator "+ wrapper.getUUID() + " has gone out from the Lobby");
	}

}
