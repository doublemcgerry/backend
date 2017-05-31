package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class AuthenticateAction extends ManagementAction {
	private static final long serialVersionUID = 4557855760874407416L;
	private String deviceKey;

	@Override
	public void execute(LobbiesManager lobbyManager, Subscriber wrapper) {
		lobbyManager.authenticate(wrapper, deviceKey);
	}
	
}
