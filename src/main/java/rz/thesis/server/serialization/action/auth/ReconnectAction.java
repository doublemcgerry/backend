package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.lobby.SuccessfulConnectionEvent;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class ReconnectAction extends ManagementAction {
	private static final long serialVersionUID = 1658463358559129074L;

	private String lobby;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, VirtualActor actor) {
		boolean reconnected = lobbyManager.reconnectToLobby(lobby, actor);
		if (reconnected) {
			SuccessfulConnectionEvent successConnEvent = new SuccessfulConnectionEvent(lobby);
			actor.sendActionToRemote(successConnEvent);
		}
	}

}
