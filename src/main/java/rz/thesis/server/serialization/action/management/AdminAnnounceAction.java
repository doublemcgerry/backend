package rz.thesis.server.serialization.action.management;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.core.modules.http.handlers.LoginHttpHandler;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.AdminConcrete;
import rz.thesis.server.serialization.action.lobby.SuccessfulConnectionEvent;

/**
 * this class is the action that every experience administrator sends to the
 * server to announce himself as an experience administrator, it creates the
 * corresponding actor and adds it into the lobby's waiting room
 * 
 * @author lollo
 */
public class AdminAnnounceAction extends ActorAnnounceAction {
	private static final long serialVersionUID = 5403490968952087253L;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, VirtualActor actor) {
		HttpServerSession session = actor.getTunnel().getServerSession();
		if (LoginHttpHandler.isAuthenticated(session)) {
			AdminConcrete adminActor = new AdminConcrete(this.name, actor);
			actor.setLobbyActor(adminActor);
			lobbyManager.addActorToLobby(actor.getUserName(), actor);
			actor.sendActionToRemote(new SuccessfulConnectionEvent(actor.getUserName()));
		}

	}

}
