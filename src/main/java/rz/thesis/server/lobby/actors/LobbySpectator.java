package rz.thesis.server.lobby.actors;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.Subscriber;

public abstract class LobbySpectator extends LobbyActor{

	public LobbySpectator(Subscriber subscriber) {
		super(subscriber);
	}

}
