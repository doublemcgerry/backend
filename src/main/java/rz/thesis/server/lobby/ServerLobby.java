package rz.thesis.server.lobby;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rz.thesis.server.lobby.actors.LobbyDevice;
import rz.thesis.server.lobby.actors.LobbySpectator;
import rz.thesis.server.lobby.actors.LobbyUserActor;
import rz.thesis.server.main.ServerMain;
import rz.thesis.server.serialization.action.Action;

public class ServerLobby {
	private static final Logger LOGGER = Logger.getLogger(ServerLobby.class.getName());
	private List<LobbySpectator> spectators = new ArrayList<LobbySpectator>();
	private List<LobbyDevice> sensors = new ArrayList<LobbyDevice>();
	private List<LobbyUserActor> users = new ArrayList<LobbyUserActor>();
	private String userName;

	public ServerLobby(String userName) {
		this.userName=userName;
	}

	public void addSensor(LobbyDevice sensor){
		synchronized (sensors) {
			sensors.add(sensor);	
		}
	}
	
	public void addUser(LobbyUserActor actor){
		synchronized (users) {
			users.add(actor);
		}
		
	}
	
	public void addSpectator(LobbySpectator actor){
		synchronized (spectators) {
			spectators.add(actor);
		}		
	}
	
	public void removeSubscriber(Subscriber wrapper){
		synchronized (sensors) {
			for (int i = spectators.size()-1; i >0 ; i--) {
				if (sensors.get(i).isWrapper(wrapper)){
					sensors.remove(i);
				}
			}
		}
		synchronized (users) {
			for (int i = users.size()-1; i >0 ; i--) {
				if (users.get(i).isWrapper(wrapper)){
					users.remove(i);
				}
			}
		}
		synchronized (sensors) {
			for (int i = spectators.size()-1; i >0 ; i--) {
				if (spectators.get(i).isWrapper(wrapper)){
					spectators.remove(i);
				}
			}
		}
		
	}

	

	public void broadcastAction(Action action) {
		synchronized (sensors) {
			for (int i = spectators.size()-1; i >0 ; i--) {
				try {
					sensors.get(i).sendAction(action);
				} catch (Exception e) {
					LOGGER.error(e);
				}
				
			}
		}
		synchronized (users) {
			for (int i = users.size()-1; i >0 ; i--) {
				try {
					users.get(i).sendAction(action);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		synchronized (sensors) {
			for (int i = spectators.size()-1; i >0 ; i--) {
				try {
					users.get(i).sendAction(action);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		LOGGER.debug(action.toString());
	}

}
