package websocketecho;

import serialization.action.game.GameAction;
import serialization.action.sensors.SensorsAction;

public interface Publisher {

	void onSubscriberAction(Subscriber subscriber, GameAction action);

	void onSensorsAction(Subscriber subscriber, SensorsAction action);

}
