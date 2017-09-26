package rz.thesis.server.lobby.actors.concrete;

import java.util.ArrayList;
import java.util.List;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.SubscriberType;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

/***
 * This class represent the concrete Actor for the experience administrator
 * 
 * @author lollo
 *
 */
public class AdminConcrete extends LobbyActor {

	public AdminConcrete(String name, VirtualActor actor) {
		super(name, actor);
	}

	@Override
	public SubscriberType getActorType() {
		return SubscriberType.USER;
	}

	@Override
	public List<SensorType> getSupportedSensors() {
		return new ArrayList<>();
	}

	@Override
	public boolean canStartExperience() {
		return true;
	}

	@Override
	public boolean canStopExperience() {
		return true;
	}

}
