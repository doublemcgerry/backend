package rz.thesis.server.lobby.actors.concrete;

import java.util.ArrayList;
import java.util.List;

import rz.thesis.server.lobby.SubscriberType;
import rz.thesis.server.lobby.actors.UserActor;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

public class MobileScreenActor extends UserActor {

	public MobileScreenActor(String name, VirtualActor actor) {
		super(name, actor);
	}

	@Override
	public SubscriberType getActorType() {
		return SubscriberType.SCREEN;
	}

	@Override
	public List<SensorType> getSupportedSensors() {
		return new ArrayList<>();
	}
}
