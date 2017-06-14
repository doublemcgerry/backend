package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.AuthenticationInformation;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class PairingRequestAction extends ManagementAction {
	private static final long serialVersionUID = 4557855760874407416L;
	private String deviceKey;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, Subscriber wrapper) {
		final AuthenticationInformation info = lobbyManager.getAuthenticator().authenticate(wrapper, deviceKey);
		if (info != null) {
			lobbyManager.getAuthenticator().removeFromWaitingRoom(deviceKey);
			final PairingConfirmationAction confirmation = new PairingConfirmationAction(deviceKey, info.getUsername(),
					info.getServerSession().getId());
			new Thread(new Runnable() {
				@Override
				public void run() {
					info.getAuthenticator().sendAction(info.getAuthenticator(), confirmation);
					info.getDeviceSubscriber().sendAction(info.getDeviceSubscriber(), confirmation);

				}
			}).start();
		}

	}

}
