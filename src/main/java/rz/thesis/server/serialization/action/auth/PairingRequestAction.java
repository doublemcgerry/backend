package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.AuthenticationInformation;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Tunnel;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class PairingRequestAction extends ManagementAction {
	private static final long serialVersionUID = 4557855760874407416L;
	private String deviceKey;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, Tunnel wrapper) {
		final AuthenticationInformation info = lobbyManager.getAuthenticator().authenticate(wrapper, deviceKey);
		if (info != null) {
			lobbyManager.getAuthenticator().removeFromWaitingRoom(deviceKey);
			final PairingConfirmationAction confirmation = new PairingConfirmationAction(deviceKey, info.getUsername(),
					info.getServerSession().getId());
			final AnnounceDemandAction demandAction = new AnnounceDemandAction();
			new Thread(new Runnable() {
				@Override
				public void run() {
					info.getAuthenticator().sendAction(confirmation);
					info.getDeviceSubscriber().sendAction(confirmation);
					info.getDeviceSubscriber().sendAction(demandAction);
				}
			}).start();
		}

	}

}
