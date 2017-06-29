package rz.thesis.server.serialization.action.lobby;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;

public class SuccesfulConnectionEvent extends LobbyAction {
	private static final long serialVersionUID = 8229417269965927669L;

	private String lobby;

	public SuccesfulConnectionEvent(String lobby) {
		this.lobby = lobby;
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ServerLobby lobby, VirtualActor actor) {
	}

	public String getLobby() {
		return lobby;
	}

}
