package rz.thesis.server.lobby;

import java.util.List;

import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

public abstract class LobbyActor {
	private VirtualActor virtualActor;

	public LobbyActor(VirtualActor actor) {
		this.virtualActor = actor;
	}
	
	public abstract SubscriberType getActorType();
	
	public abstract List<SensorType> getSupportedSensors();
}
