package rz.thesis.server.lobby.actors;

import rz.thesis.server.sensors.SensorType;

public abstract class SensorDataListener {
	public abstract void onSensorData(SensorActor actor,SensorType type,SensorDataContainer data);
}
