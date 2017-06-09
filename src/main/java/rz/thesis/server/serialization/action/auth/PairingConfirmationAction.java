package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class PairingConfirmationAction extends ManagementAction {

	private static final long serialVersionUID = -1692216914762512270L;
	private String deviceName;
	private String userName;

	public PairingConfirmationAction(String deviceName, String userName) {
		this.deviceName = deviceName;
		this.userName = userName;
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, Subscriber wrapper) {

	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getUserName() {
		return userName;
	}

}
