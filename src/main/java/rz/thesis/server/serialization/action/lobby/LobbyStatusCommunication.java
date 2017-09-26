package rz.thesis.server.serialization.action.lobby;

import java.util.List;

import rz.thesis.server.lobby.ServerLobby.LobbyState;

public class LobbyStatusCommunication extends LobbyEvent {
	private static final long serialVersionUID = -2709952093019777438L;
	private LobbyState state;
	private List<DeviceDefinition> devices;
	private String lobbyName;

	public LobbyStatusCommunication(LobbyState state, List<DeviceDefinition> devices, String lobbyName) {
		super();
		this.state = state;
		this.devices = devices;
		this.lobbyName = lobbyName;
	}

	public List<DeviceDefinition> getDevices() {
		return devices;
	}

	public String getLobbyName() {
		return lobbyName;
	}

	public LobbyState getState() {
		return state;
	}

	@Override
	public String toString() {
		return super.toString() + " name " + this.lobbyName + " state:" + this.state + " devices:"
				+ this.devices.toString();
	}

}
