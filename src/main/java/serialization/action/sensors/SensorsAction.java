package serialization.action.sensors;

import serialization.action.Action;
import websocketecho.ServerController;
import websocketecho.Subscriber;

public abstract class SensorsAction extends Action {
	private static final long serialVersionUID = -2774926282070444319L;
	public abstract void execute(ServerController serverController, Subscriber subscriber);
}
