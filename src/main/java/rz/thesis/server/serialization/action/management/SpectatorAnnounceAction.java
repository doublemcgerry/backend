package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.SpectatorConcrete;

public class SpectatorAnnounceAction extends ActorAnnounceAction {

	private static final long serialVersionUID = -3018483622075602666L;

	public SpectatorAnnounceAction() {
	}

	@Override
	public void execute(LobbiesManagerInterface router, VirtualActor actor) {
		SpectatorConcrete spectatorActor = new SpectatorConcrete(actor);
		actor.setLobbyActor(spectatorActor);
		router.addActorToLobby(actor.getUserName(), actor);
	}

}
