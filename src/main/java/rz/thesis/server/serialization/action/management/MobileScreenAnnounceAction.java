package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.MobileScreenActor;
import rz.thesis.server.serialization.action.lobby.SuccessfulConnectionEvent;

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
	public void execute(LobbiesManagerInterface router, VirtualActor actor) {
		MobileScreenActor mobileactor = new MobileScreenActor(this.name, actor);
		actor.setLobbyActor(mobileactor);
		actor.sendActionToRemote(new SuccessfulConnectionEvent(actor.getUserName()));
		router.addActorToLobby(actor.getUserName(), actor);

	}

}
