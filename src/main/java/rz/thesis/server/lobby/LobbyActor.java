package rz.thesis.server.lobby;

import rz.thesis.server.lobby.actors.VirtualActor;

public abstract class LobbyActor {
	private VirtualActor virtualActor;

	public LobbyActor(VirtualActor actor) {
		this.virtualActor = actor;
	}
}
