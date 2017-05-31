package rz.thesis.server.lobby.actors;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.Subscriber;

public abstract class UserActor extends LobbyActor {

	public UserActor(Subscriber subscriber) {
		super(subscriber);
	}

}
