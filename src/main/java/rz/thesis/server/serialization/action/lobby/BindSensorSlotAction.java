package rz.thesis.server.serialization.action.lobby;

import rz.thesis.modules.experience.ExperienceDevicesStatus;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;

public class BindSensorSlotAction extends LobbyAction {
	private static final long serialVersionUID = 1212827102979125738L;

	private SensorType type;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
	        VirtualActor actor) {
		if (lobby.isExperienceInitiating()) {
			ExperienceDevicesStatus devicesStatus = lobby.getDeviceStatus();
			if (devicesStatus.canAddSensor(type)) {
				lobby.getDeviceStatus().addSensor(type, actor.getAddress());
				ExperienceStatusChangeEvent changeEvent = new ExperienceStatusChangeEvent(devicesStatus);
				lobby.broadcastEvent(changeEvent);
			}
		}
	}

}
