package rz.thesis.server.serialization.action.lobby.experience;

import rz.thesis.modules.experience.Experience;
import rz.thesis.server.lobby.ExperienceDevicesStatus;
import rz.thesis.server.serialization.action.lobby.LobbyEvent;

public class SelectedExperienceEvent extends LobbyEvent {
	private static final long serialVersionUID = -7363529515015050795L;
	Experience experience;
	ExperienceDevicesStatus status;

	public SelectedExperienceEvent(Experience experience, ExperienceDevicesStatus status) {
		this.status = status;
		this.experience = experience;
	}
}
