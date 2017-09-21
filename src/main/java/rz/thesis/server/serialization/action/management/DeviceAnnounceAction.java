package rz.thesis.server.serialization.action.management;

import java.util.List;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.DeviceConcrete;
import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.lobby.ConnectedDeviceEvent;
import rz.thesis.server.serialization.action.lobby.SuccessfulConnectionEvent;

/**
 * this class is the action that every sensor device sends to the server to
 * announce himself as sensor device, it creates the corresponding actor and
 * adds it into the lobby's waiting room
 * 
 * @author achelius
 *
 */
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
		actor.sendActionToRemote(new SuccessfulConnectionEvent(actor.getUserName()));
		router.broadcastToWaitingRoom(new ConnectedDeviceEvent(actor.getUserName(), name, deviceActor.getActorType(), sensorTypes));
	}

}
