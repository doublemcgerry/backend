package rz.thesis.server.lobby.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.sensors.SensorType;

public abstract class LobbyDevice extends LobbyActor {
	public LobbyDevice(VirtualActor actor, List<SensorType> sensorTypes) {
		super(actor);
		this.sensorTypes = sensorTypes;
	}

	protected List<SensorType> sensorTypes;
	protected Map<SensorType, List<SensorDataListener>> listeners;

	protected abstract void startSensorDataStream(SensorType type);

	protected abstract void stopSensorDataStream(SensorType type);

	protected void addSensorDataListener(SensorType type, SensorDataListener listener) {
		if (!listeners.containsKey(type)) {
			listeners.put(type, new ArrayList<SensorDataListener>());
		}
		listeners.get(type).add(listener);
	}

	protected void removeSensorsDataListener(SensorType type, SensorDataListener listener) {
		if (!listeners.containsKey(type)) {
			listeners.put(type, new ArrayList<SensorDataListener>());
			return;
		}
		listeners.get(type).remove(listener);
	}
}
