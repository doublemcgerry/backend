package rz.thesis.server.serialization.action.lobby.experience;

import rz.thesis.modules.experience.ExperienceDevicesStatus;
import rz.thesis.server.serialization.action.lobby.LobbyEvent;

public class SelectedExperienceEvent extends LobbyEvent {
	private static final long serialVersionUID = -7363529515015050795L;
	ExperienceDevicesStatus status;

	public SelectedExperienceEvent(ExperienceDevicesStatus status) {
		this.status = status;
	}
}
