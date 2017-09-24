package rz.thesis.server.serialization.action.lobby;

import rz.thesis.server.lobby.ServerLobby.LobbyState;

public class LobbyStateChanged extends LobbyEvent {
	private static final long serialVersionUID = 8436434360946539467L;
	private LobbyState state;

	public LobbyStateChanged(LobbyState state) {
		super();
		this.state = state;
	}

	public LobbyState getState() {
		return state;
	}

}
