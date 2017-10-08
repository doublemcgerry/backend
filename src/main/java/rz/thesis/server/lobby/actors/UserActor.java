package rz.thesis.server.lobby.actors;

import rz.thesis.server.lobby.LobbyActor;

public abstract class UserActor extends LobbyActor {

	public UserActor(String name, VirtualActor actor) {
		super(name, actor);
	}

}
