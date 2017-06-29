package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class ConnectAction extends ManagementAction {
	private static final long serialVersionUID = 3571817470750638337L;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, VirtualActor actor) {
		String token = lobbyManager.getAuthenticator().generateNewToken();
		lobbyManager.getAuthenticator().addActorToWaitingRoom(token, actor);
		AuthCodeAction authCodeAction = new AuthCodeAction(token);
		actor.sendActionToRemote(authCodeAction);
	}

}
