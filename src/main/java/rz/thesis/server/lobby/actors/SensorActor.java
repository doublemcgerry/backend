package rz.thesis.server.lobby.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.sensors.SensorType;

public abstract class SensorActor extends LobbyActor {
	public SensorActor(Subscriber subscriber) {
		super(subscriber);
	}
	private List<SensorType> sensorTypes;
	private Map<SensorType,List<SensorDataListener>> listeners;
	protected abstract void startSensorRead(SensorType type);
	protected abstract void stopSensorRead(SensorType type);
	protected void addSensorDataListener(SensorType type, SensorDataListener listener){
		if (!listeners.containsKey(type)){
			listeners.put(type, new ArrayList<SensorDataListener>());
		}
		listeners.get(type).add(listener);
	}
	protected void removeSensorsDataListener(SensorType type, SensorDataListener listener){
		if (!listeners.containsKey(type)){
			listeners.put(type, new ArrayList<SensorDataListener>());
			return;
		}
		listeners.get(type).remove(listener);
	}
}
 