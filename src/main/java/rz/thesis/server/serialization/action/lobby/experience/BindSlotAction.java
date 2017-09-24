package rz.thesis.server.serialization.action.lobby.experience;

import java.util.UUID;

import rz.thesis.modules.experience.ExperienceDevicesStatus;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.lobby.LobbyAction;

public class BindSlotAction extends LobbyAction {
	private static final long serialVersionUID = 1212827102979125738L;
	private UUID address;
	private SensorType type;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
	        VirtualActor actor) {
		if (lobby.isExperienceInitiating()) {
			ExperienceDevicesStatus devicesStatus = lobby.getDeviceStatus();
			if (devicesStatus.canAddSensor(type)) {
				lobby.getDeviceStatus().addSensor(type, this.address);
				ExperienceStatusChangeEvent changeEvent = new ExperienceStatusChangeEvent(devicesStatus);
				lobby.broadcastEvent(changeEvent);
			}
		}
	}

}
