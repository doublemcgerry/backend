package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class SendTokenAction extends ManagementAction {
	private static final long serialVersionUID = 1353808040784186537L;

	private String token;

	public SendTokenAction(String token) {
		this.token=token;
	}

	@Override
	public void execute(LobbiesManager lobbyManager, Subscriber wrapper) {
		
	}

	public String getToken() {
		return token;
	}
	
	

}
