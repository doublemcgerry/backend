package rz.thesis.server.lobby;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rz.thesis.server.lobby.actors.SensorActor;
import rz.thesis.server.lobby.actors.SpectatorActor;
import rz.thesis.server.lobby.actors.UserActor;
import rz.thesis.server.logger.interfaces.LobbyLoggerInterface;
import rz.thesis.server.main.ServerMain;
import rz.thesis.server.serialization.action.Action;

public class ServerLobby {
	private static final Logger LOGGER = Logger.getLogger(ServerLobby.class.getName());
	private List<SpectatorActor> spectators = new ArrayList<SpectatorActor>();
	private List<SensorActor> sensors = new ArrayList<SensorActor>();
	private List<UserActor> users = new ArrayList<UserActor>();
	private LobbyLoggerInterface log;
	

	public ServerLobby(LobbyLoggerInterface log) {
		this.log = log;
	}

	public void addSensor(SensorActor sensor){
		synchronized (sensors) {
			sensors.add(sensor);	
		}
	}
	
	public void addUser(UserActor actor){
		synchronized (users) {
			users.add(actor);
		}
		
	}
	
	public void addSpectator(SpectatorActor actor){
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
		log.logLobbyActivity(action.toString());
	}

}
