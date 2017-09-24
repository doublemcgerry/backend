package rz.thesis.server.serialization.action.lobby.experience;

import rz.thesis.modules.experience.Experience;
import rz.thesis.server.lobby.ExperienceDevicesStatus;
import rz.thesis.server.serialization.action.lobby.LobbyEvent;

public class ExperienceStartedEvent extends LobbyEvent {

	private static final long serialVersionUID = 1581927946741025401L;
	private Experience experience;
	private ExperienceDevicesStatus devices;

	public ExperienceStartedEvent(Experience experience, ExperienceDevicesStatus devicesStatus) {
		this.experience = experience;
		this.devices = devicesStatus;
	}

	public Experience getExperience() {
		return experience;
	}

	public ExperienceDevicesStatus getDevices() {
		return devices;
	}

}
