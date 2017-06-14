package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.actors.concrete.MobileScreenActor;
<<<<<<< Updated upstream
import rz.thesis.server.serialization.action.auth.AuthCodeAction;
=======
>>>>>>> Stashed changes

/**
 * this class is the action that every mobile screen sends to the server to
 * announce himself as mobile screen, it creates the corresponding actor and
 * adds it into the lobby's waiting room
 * 
 * 
 * @author achelius
 *
 */
public class MobileScreenAnnounceAction extends ActorAnnounceAction {

	private static final long serialVersionUID = 2202061539670703277L;

	public MobileScreenAnnounceAction() {

	}

	@Override
	public void execute(LobbiesManagerInterface router, Subscriber wrapper) {
		MobileScreenActor actor = new MobileScreenActor(wrapper);
<<<<<<< Updated upstream
		String token = router.addLobbyActorToWaitingRoom(actor);
		wrapper.sendAction(wrapper, new AuthCodeAction(token));
=======
		router.addActorToLobby(wrapper.getServerSession().getUsername(), actor);
>>>>>>> Stashed changes
	}

}
