package rz.thesis.server.serialization.action.lobby;

import java.util.List;

public class LobbyMembersListEvent extends LobbyEvent {
	private static final long serialVersionUID = -2709952093019777438L;
	private List<DeviceDefinition> devices;
	private String lobbyName;

	public LobbyMembersListEvent(List<DeviceDefinition> devices, String lobbyName) {
		super();
		this.devices = devices;
		this.lobbyName = lobbyName;
	}

	public List<DeviceDefinition> getDevices() {
		return devices;
	}

	public String getLobbyName() {
		return lobbyName;
	}

}
