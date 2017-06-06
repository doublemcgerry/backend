package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class SendCodeAction extends ManagementAction {
	private static final long serialVersionUID = 1353808040784186537L;

	private String code;

	public SendCodeAction(String code) {
		this.code=code;
	}

	@Override
	public void execute(LobbiesManager lobbyManager, Subscriber wrapper) {
		
	}

	public String getCode() {
		return code;
	}
}
