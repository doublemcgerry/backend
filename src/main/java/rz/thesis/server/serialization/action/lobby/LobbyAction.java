package rz.thesis.server.serialization.action.lobby;

import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.Action;

public abstract class LobbyAction extends Action {
	private static final long serialVersionUID = 3628061055565407564L;

	public abstract void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule,
	        ServerLobby lobby, VirtualActor actor);
}
