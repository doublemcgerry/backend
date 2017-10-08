package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;

/**
 * this action triggers an actor disconnection without disconnecting the tunnel
 * 
 * @author achelius
 *
 */
public class DisconnectAction extends ManagementAction {

	private static final long serialVersionUID = 2768145624266150415L;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, VirtualActor actor) {
		lobbyManager.disconnectFromLobby(actor);
		if (actor.getTunnel() != null) {
			actor.getTunnel().removeActor(actor);
		}
	}

}
