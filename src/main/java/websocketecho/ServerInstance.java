package websocketecho;

import java.util.ArrayList;
import java.util.List;

import serialization.action.Action;
import serialization.action.lobby.ConnectedDeviceEvent;
import serialization.action.lobby.DisconnectedDeviceEvent;

public class ServerInstance {
	
	private List<Subscriber> spectator= new ArrayList<Subscriber>();
	private Subscriber smartwatch;
	private Subscriber mobile;

	public void addSmartwatch(Subscriber smartwatch){
		this.smartwatch = smartwatch;
		this.broadcastAction(new ConnectedDeviceEvent(SubscriberType.SMARTWATCH));
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
		this.broadcastAction(new ConnectedDeviceEvent(SubscriberType.MOBILE));
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
		this.broadcastAction(new ConnectedDeviceEvent(SubscriberType.SPECTATOR));
	}

	public void removeSubscriber(Subscriber subscriber){
		this.spectator.remove(subscriber);
		this.broadcastAction(new DisconnectedDeviceEvent(SubscriberType.SPECTATOR));
	}
	
	public void broadcastAction(Action action){
		for (Subscriber subscriber : spectator) {
			subscriber.sendActionToSubscriber(action);
		}
		smartwatch.sendActionToSubscriber(action);
		mobile.sendActionToSubscriber(action);
	}
	
}
