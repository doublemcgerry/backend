package rz.thesis.server.serialization.action.lobby.experience;

import java.util.UUID;

import rz.thesis.modules.experience.Experience;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.ExperienceDevicesStatus;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.AdminConcrete;
import rz.thesis.server.serialization.action.lobby.LobbyAction;

public class SelectExperienceAction extends LobbyAction {
	private static final long serialVersionUID = 2096847257069776706L;
	private UUID experienceId;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
			VirtualActor actor) {
		if (actor.hasLobbyActor() && actor.getLobbyActor() instanceof AdminConcrete) {
			Experience exp = experiencesModule.getController().getExperience(0, experienceId);
			ExperienceDevicesStatus status = lobby.initializeExperience(exp);
			status.addScreen(actor.getAddress());
			lobby.broadcastEvent(new SelectedExperienceEvent(exp, status));
		}
	}

}
