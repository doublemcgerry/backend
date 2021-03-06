package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class PairingConfirmationAction extends ManagementAction {

	private static final long serialVersionUID = -1692216914762512270L;
	private String deviceName;
	private String userName;
	private String sessionId;

	public PairingConfirmationAction(String deviceName, String userName, String sessionId) {
		this.deviceName = deviceName;
		this.userName = userName;
		this.sessionId = sessionId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getUserName() {
		return userName;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, VirtualActor actor) {
		// TODO Auto-generated method stub

	}

}
