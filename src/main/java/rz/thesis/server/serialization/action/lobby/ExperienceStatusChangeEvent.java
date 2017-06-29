package rz.thesis.server.serialization.action.lobby;

import rz.thesis.modules.experience.ExperienceDevicesStatus;

public class ExperienceStatusChangeEvent extends LobbyEvent {
	private static final long serialVersionUID = 7684472324843580729L;
	private ExperienceDevicesStatus status;
	private boolean ready;

	public ExperienceStatusChangeEvent(ExperienceDevicesStatus status) {
		this.status = status;
		this.ready = status.isReady();
	}
}
