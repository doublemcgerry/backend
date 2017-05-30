package rz.thesis.server.lobby.actors;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.Subscriber;

public abstract class SpectatorActor extends LobbyActor{

	public SpectatorActor(Subscriber subscriber) {
		super(subscriber);
	}

}
