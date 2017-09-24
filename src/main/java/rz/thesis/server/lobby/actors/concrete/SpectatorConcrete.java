package rz.thesis.server.lobby.actors.concrete;

import java.util.ArrayList;
import java.util.List;

import rz.thesis.server.lobby.SubscriberType;
import rz.thesis.server.lobby.actors.LobbySpectator;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

public class SpectatorConcrete extends LobbySpectator {

	public SpectatorConcrete(String name, VirtualActor wrapper) {
		super(name, wrapper);
	}

	@Override
	public SubscriberType getActorType() {
		return SubscriberType.SPECTATOR;
	}

	@Override
	public List<SensorType> getSupportedSensors() {
		return new ArrayList<>();
	}

	@Override
	public boolean canStartExperience() {
		return false;
	}

}
