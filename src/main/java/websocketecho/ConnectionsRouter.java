package websocketecho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import serialization.action.management.ManagementAction;

public class ConnectionsRouter {
	
	private Map<String,ServerInstance> lobbyMap;

	public ConnectionsRouter() {
		lobbyMap = new HashMap<String, ServerInstance>();
	}

	public UUID generateUUID() {
		return UUID.randomUUID();
	}

	public void handleManagementAction(Subscriber wrapper, ManagementAction action) {
		action.execute(this, wrapper);
	}
	
	public ArrayList<String> getAvailableLobby(){
		ArrayList<String> availableLobbies = new ArrayList<String>();
		for (Map.Entry<String, ServerInstance> entry : lobbyMap.entrySet()) {
			ServerInstance instance = entry.getValue();
			if(!instance.isSmartwatchPresent()){
				availableLobbies.add(entry.getKey());
			}
		}
		return availableLobbies;
	}
	
	private void createLobby(Subscriber wrapper, String zone){
		ServerInstance gameinstance = new ServerInstance();
		lobbyMap.put(zone, gameinstance);
	}

	public void changeLobby(Subscriber wrapper, String zone, SubscriberType type) {
		switch (type) {
			case SMARTWATCH:
				if(lobbyMap.containsKey(zone)){
					ServerInstance instance = lobbyMap.get(zone);
					instance.addSmartwatch(wrapper);
				}
				else{
					createLobby(wrapper, zone);
					this.lobbyMap.get(zone).addSmartwatch(wrapper);
				}
				break;
			case MOBILE:
				if(lobbyMap.containsKey(zone)){
					ServerInstance instance = lobbyMap.get(zone);
					instance.addMobile(wrapper);
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
		wrapper.removeFromGameInstance();
	}

}
