package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.AdminConcrete;
import rz.thesis.server.serialization.action.lobby.ConnectedDeviceEvent;
import rz.thesis.server.serialization.action.lobby.SuccessfulConnectionEvent;

/**
 * this class is the action that every experience administrator sends to the server to
 * announce himself as an experience administrator, it creates the corresponding actor and
 * adds it into the lobby's waiting room
 * 
 * @author lollo
 */
public class AdminAnnounceAction extends ActorAnnounceAction {
	private static final long serialVersionUID = 5403490968952087253L;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, VirtualActor actor) {
		AdminConcrete adminActor = new AdminConcrete(actor);
		actor.setLobbyActor(adminActor);
		lobbyManager.addActorToLobby(actor.getUserName(), actor);
		actor.sendActionToRemote(new SuccessfulConnectionEvent(actor.getUserName()));
		lobbyManager.broadcastToWaitingRoom(new ConnectedDeviceEvent(actor.getUserName(), name, adminActor.getActorType(), adminActor.getSupportedSensors()));
	}

}
