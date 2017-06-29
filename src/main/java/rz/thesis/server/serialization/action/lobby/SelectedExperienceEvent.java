package rz.thesis.server.serialization.action.lobby;

import rz.thesis.modules.experience.ExperienceDefinitionParameters;

public class SelectedExperienceEvent extends LobbyEvent {
	private static final long serialVersionUID = -7363529515015050795L;
	ExperienceDefinitionParameters info;

	public SelectedExperienceEvent(ExperienceDefinitionParameters params) {
		this.info = params;
	}
}
