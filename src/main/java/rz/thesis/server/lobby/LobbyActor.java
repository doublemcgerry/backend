package rz.thesis.server.lobby;

import java.util.List;
import java.util.UUID;

import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

public abstract class LobbyActor {
	private VirtualActor virtualActor;
	private String name;

	public LobbyActor(String name, VirtualActor actor) {
		this.name = name;
		this.virtualActor = actor;
	}

	public String getName() {
		return name;
	}

	public abstract SubscriberType getActorType();

	public abstract List<SensorType> getSupportedSensors();

	public UUID getAddress() {
		return this.virtualActor.getAddress();
	}

	/**
	 * Returns true if the actor has the permission to start the experience, false
	 * otherwise
	 * 
	 * @return
	 */
	public abstract boolean canStartExperience();
}
