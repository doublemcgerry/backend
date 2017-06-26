package rz.thesis.server.lobby.actors.concrete;

import java.util.List;

import rz.thesis.server.lobby.actors.LobbyDevice;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

public class DeviceConcrete extends LobbyDevice {

	public DeviceConcrete(VirtualActor actor, List<SensorType> sensorTypes) {
		super(actor, sensorTypes);
	}

	@Override
	protected void startSensorDataStream(SensorType type) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void stopSensorDataStream(SensorType type) {
		// TODO Auto-generated method stub

	}

}
