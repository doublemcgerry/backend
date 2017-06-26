package rz.thesis.server.serialization.action.management;

import java.util.List;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.DeviceConcrete;
import rz.thesis.server.sensors.SensorType;

public class DeviceAnnounceAction extends ActorAnnounceAction {

	private static final long serialVersionUID = -3018483622075602666L;

	private List<SensorType> sensorTypes;

	public DeviceAnnounceAction() {
	}

	@Override
	public void execute(LobbiesManagerInterface router, VirtualActor actor) {
		DeviceConcrete deviceActor = new DeviceConcrete(actor, sensorTypes);
		actor.setLobbyActor(deviceActor);
		router.addActorToLobby(actor.getUserName(), actor);
	}

}
