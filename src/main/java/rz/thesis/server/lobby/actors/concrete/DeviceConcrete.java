package rz.thesis.server.lobby.actors.concrete;

import java.util.List;

import rz.thesis.server.lobby.SubscriberType;
import rz.thesis.server.lobby.actors.LobbyDevice;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

public class DeviceConcrete extends LobbyDevice {

	public DeviceConcrete(String name, VirtualActor actor, List<SensorType> sensorTypes) {
		super(name, actor, sensorTypes);
	}

	@Override
	protected void startSensorDataStream(SensorType type) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void stopSensorDataStream(SensorType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (SensorType sensorType : sensorTypes) {
			builder.append(sensorType.toString());
			builder.append(" ");
		}
		return super.toString() + " sensorTypes: " + builder.toString();
	}

	@Override
	public SubscriberType getActorType() {
		return SubscriberType.SENSOR;
	}

	@Override
	public List<SensorType> getSupportedSensors() {
		return this.sensorTypes;
	}

	@Override
	public boolean canStartExperience() {
		return false;
	}

	@Override
	public boolean canStopExperience() {
		return false;
	}

}
