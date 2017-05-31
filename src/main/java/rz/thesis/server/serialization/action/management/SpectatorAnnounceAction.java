package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.actors.concrete.SpectatorConcrete;

public class SpectatorAnnounceAction extends ActorAnnounceAction {

	private static final long serialVersionUID = -3018483622075602666L;

	public SpectatorAnnounceAction() {
	}

	@Override
	public void execute(LobbiesManager router, Subscriber wrapper) {
		SpectatorConcrete actor = new SpectatorConcrete(wrapper);
		router.addLobbyActorToWaitingRoom(actor);
	}

}
