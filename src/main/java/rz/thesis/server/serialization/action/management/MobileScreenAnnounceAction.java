package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.actors.concrete.MobileScreenActor;

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
	public void execute(LobbiesManager router, Subscriber wrapper) {
		MobileScreenActor actor = new MobileScreenActor(wrapper);
		router.addLobbyActorToWaitingRoom(actor);
	}

}
