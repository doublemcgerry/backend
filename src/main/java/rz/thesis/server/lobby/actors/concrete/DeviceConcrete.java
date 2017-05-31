package rz.thesis.server.lobby.actors.concrete;

import java.util.UUID;

import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.actors.SensorActor;
import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.Action;

public class SensorActorConcrete extends SensorActor{

	public SensorActorConcrete(Subscriber subscriber) {
		super(subscriber);
	}

	@Override
	protected void startSensorRead(SensorType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stopSensorRead(SensorType type) {
		// TODO Auto-generated method stub
		
	}

}
