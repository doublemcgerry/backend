package rz.thesis.server.serialization.action.lobby.experience;

import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.lobby.LobbyAction;

public class TransitionPauseAction extends LobbyAction {

	private static final long serialVersionUID = 4779880273688496723L;
	private String transitionName;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
			VirtualActor actor) {
		lobby.broadcastAction(this);
	}

	public String getTransitionName() {
		return transitionName;
	}

}
