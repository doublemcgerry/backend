package rz.thesis.server.lobby.actors;

import rz.thesis.server.lobby.LobbyActor;

public abstract class LobbySpectator extends LobbyActor {

	public LobbySpectator(String name, VirtualActor wrapper) {
		super(name, wrapper);
	}

}
