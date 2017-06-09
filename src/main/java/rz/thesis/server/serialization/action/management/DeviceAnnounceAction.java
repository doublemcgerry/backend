package rz.thesis.server.serialization.action.management;

import java.util.List;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.actors.concrete.DeviceConcrete;
import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.auth.SendCodeAction;

public class DeviceAnnounceAction extends ActorAnnounceAction {

	private static final long serialVersionUID = -3018483622075602666L;

	private List<SensorType> sensorTypes;

	public DeviceAnnounceAction() {
	}

	@Override
	public void execute(LobbiesManagerInterface router, Subscriber wrapper) {
		DeviceConcrete actor = new DeviceConcrete(wrapper, sensorTypes);
		String token = router.addLobbyActorToWaitingRoom(actor);
		wrapper.sendAction(wrapper, new SendCodeAction(token));
	}

}
