package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class AuthCodeAction extends ManagementAction {
	private static final long serialVersionUID = 1353808040784186537L;

	private String code;

	public AuthCodeAction(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, VirtualActor actor) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return super.toString() + " content: " + code;
	}
}
