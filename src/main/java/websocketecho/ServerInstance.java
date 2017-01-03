package websocketecho;

import java.util.ArrayList;
import java.util.List;

import serialization.action.Action;
import serialization.action.game.ConnectedAction;
import serialization.action.game.DisconnectedAction;
import serialization.action.game.GameAction;
import serialization.action.sensors.SensorsAction;



public class ServerInstance {
	
	private List<Subscriber> spectator= new ArrayList<Subscriber>();
	private Subscriber smartwatch;
	private Subscriber mobile;
	private Publisher publisher;
	
	public ServerInstance(Publisher publisher) {
		this.publisher=publisher;
	}

	public void addSmartwatch(Subscriber smartwatch){
		this.smartwatch = smartwatch;
	}
	
	public boolean isSmartwatchPresent(){
		if(smartwatch == null){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void addMobile(Subscriber mobile){
		this.mobile = mobile;
	}
	
	public boolean isMobilePresent(){
		if(mobile == null){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void addSpectator(Subscriber subscriber){
		this.spectator.add(subscriber);
		this.publisher.onSubscriberAction(subscriber, new ConnectedAction());
	}

	public void removeSpectator(Subscriber subscriber){
		this.spectator.remove(subscriber);
		this.publisher.onSubscriberAction(subscriber, new DisconnectedAction());
	}
	
	public void broadcastAction(Action action){
		for (Subscriber subscriber : spectator) {
			subscriber.sendActionToSubscriber(action);
		}
	}

	public void onSubscriberMessage(Subscriber subscriber, GameAction action) {
		publisher.onSubscriberAction(subscriber,action);
	}

	public void handleSensorsAction(Subscriber subscriber, SensorsAction action) {
		publisher.onSensorsAction(subscriber,action);
	}
	
}
