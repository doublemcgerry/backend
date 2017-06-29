package rz.thesis.server.serialization.action.lobby;

import rz.thesis.modules.experience.ExperienceDevicesStatus;

public class SelectedExperienceEvent extends LobbyEvent {
	private static final long serialVersionUID = -7363529515015050795L;
	ExperienceDevicesStatus status;

	public SelectedExperienceEvent(ExperienceDevicesStatus status) {
		this.status = status;
	}
}
