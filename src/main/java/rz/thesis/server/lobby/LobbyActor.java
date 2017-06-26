package rz.thesis.server.lobby;

import rz.thesis.server.lobby.actors.VirtualActor;

public abstract class LobbyActor {
	protected ServerLobby lobby;
	private VirtualActor virtualActor;

	public LobbyActor(VirtualActor actor) {
		this.virtualActor = actor;
	}

	public ServerLobby getLobby() {
		return lobby;
	}

	public void setLobby(ServerLobby lobby) {
		this.lobby = lobby;
	}

}
