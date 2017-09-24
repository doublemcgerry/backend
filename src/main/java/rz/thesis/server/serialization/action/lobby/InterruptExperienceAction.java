package rz.thesis.server.serialization.action.lobby;

import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;

public class InterruptExperienceAction extends LobbyAction {

	private static final long serialVersionUID = -1996463883795846943L;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
			VirtualActor actor) {
		if (actor.canStartExperience()) {
			lobby.startExperience();
		}
	}

}
