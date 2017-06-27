package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class PairingRequestAction extends ManagementAction {
	private static final long serialVersionUID = 4557855760874407416L;
	private String deviceKey;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, final VirtualActor actor) {
		// final AuthenticationInformation info =
		// lobbyManager.getAuthenticator().authenticate(actor.getTunnel(),
		// deviceKey);
		// if (info != null) {
		// lobbyManager.getAuthenticator().removeFromWaitingRoom(deviceKey);
		// info.getactor().authenticate(info.getUsername());
		// final PairingConfirmationAction confirmation = new
		// PairingConfirmationAction(deviceKey, info.getUsername(),
		// info.getactor().getAddress().toString());
		// final AnnounceDemandAction demandAction = new AnnounceDemandAction();
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// actor.sendActionToRemote(confirmation);
		// info.getactor().sendActionToRemote(confirmation);
		// info.getactor().sendActionToRemote(demandAction);
		// }
		// }).start();
		// }
	}

}
