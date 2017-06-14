package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.actors.concrete.SpectatorConcrete;
<<<<<<< Updated upstream
import rz.thesis.server.serialization.action.auth.AuthCodeAction;
=======
>>>>>>> Stashed changes

public class SpectatorAnnounceAction extends ActorAnnounceAction {

	private static final long serialVersionUID = -3018483622075602666L;

	public SpectatorAnnounceAction() {
	}

	@Override
	public void execute(LobbiesManagerInterface router, Subscriber wrapper) {
		SpectatorConcrete actor = new SpectatorConcrete(wrapper);
<<<<<<< Updated upstream
		String token = router.addLobbyActorToWaitingRoom(actor);
		wrapper.sendAction(wrapper, new AuthCodeAction(token));
=======
		router.addActorToLobby(wrapper.getServerSession().getUsername(), actor);
>>>>>>> Stashed changes
	}

}
