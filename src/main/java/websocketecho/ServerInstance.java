package websocketecho;

import java.util.ArrayList;
import java.util.List;

import interfaces.LobbyLoggerInterface;
import serialization.action.Action;
import serialization.action.lobby.ConnectedDeviceEvent;
import serialization.action.lobby.DisconnectedDeviceEvent;

public class ServerInstance {
	
	private List<Subscriber> spectator= new ArrayList<Subscriber>();
	private Subscriber smartwatch;
	private Subscriber mobile;
	private LobbyLoggerInterface log;
	
	public ServerInstance(LobbyLoggerInterface log){
		this.log = log;
	}

	public void addSmartwatch(Subscriber smartwatch){
		this.smartwatch = smartwatch;
		this.broadcastAction(new ConnectedDeviceEvent(smartwatch.getName(), SubscriberType.SMARTWATCH));
	}
	
	public boolean isSmartwatchPresent(){
		if(smartwatch == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void addMobile(Subscriber mobile){
		this.mobile = mobile;
		this.broadcastAction(new ConnectedDeviceEvent(mobile.getName(),SubscriberType.MOBILE));
		//TODO messaggi nella lobby
	}
	
	public boolean isMobilePresent(){
		if(mobile == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void addSpectator(Subscriber subscriber){
		this.spectator.add(subscriber);
		this.broadcastAction(new ConnectedDeviceEvent(subscriber.getName(),SubscriberType.SPECTATOR));
	}

	public void removeSubscriber(Subscriber subscriber){
		this.spectator.remove(subscriber);
		this.broadcastAction(new DisconnectedDeviceEvent(subscriber.getName(),SubscriberType.SPECTATOR));
	}
	
	public void broadcastAction(Action action){
		for (Subscriber subscriber : spectator) {
			subscriber.sendActionToSubscriber(action);
		}
		if(smartwatch != null){
			smartwatch.sendActionToSubscriber(action);
		}
		if(mobile != null){
			mobile.sendActionToSubscriber(action);
		}
		log.logLobbyActivity(action.toString());
	}
	
}
