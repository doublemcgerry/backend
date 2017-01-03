package websocketecho;

import serialization.action.game.GameAction;
import serialization.action.sensors.SensorsAction;

public class ServerController implements Publisher{

	private ServerInstance instance;
	
	public ServerController() {
		super();
	}
	
	@Override
	public void onSubscriberAction(Subscriber subscriber, GameAction action) {
		action.execute(this, subscriber);
	}

	public void linkToGameInstance(ServerInstance instance) {
		this.instance=instance;
	}

	@Override
	public void onSensorsAction(Subscriber subscriber, SensorsAction action) {
		action.execute(this, subscriber);
	}

}
