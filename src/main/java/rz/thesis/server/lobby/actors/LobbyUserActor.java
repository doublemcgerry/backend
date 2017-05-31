package rz.thesis.server.lobby.actors;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.Subscriber;

public abstract class LobbyUserActor extends LobbyActor {

	public LobbyUserActor(Subscriber subscriber) {
		super(subscriber);
	}

}
