package rz.thesis.server.serialization.action.lobby;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.SubscriberType;

public class DisconnectedDeviceEvent extends LobbyEvent {

	private static final long serialVersionUID = 3826952011751893194L;
	private String lobbyName;
	private DeviceDefinition device;

	public DisconnectedDeviceEvent(String lobbyName, DeviceDefinition device) {
		super();
		this.device = device;
		this.lobbyName = lobbyName;
	}

	public DisconnectedDeviceEvent(String lobbyName, LobbyActor lobbyActor) {
		this.device = new DeviceDefinition(lobbyActor.getName(), lobbyActor.getAddress(), lobbyActor.getActorType(),
				lobbyActor.getSupportedSensors());
		this.lobbyName = lobbyName;

	}

	public SubscriberType getType() {
		return this.device.getType();
	}

	public void setType(SubscriberType type) {
		this.device.setType(type);
	}

	public String getLobbyName() {
		return lobbyName;
	}

	public void setLobbyName(String lobbyName) {
		this.lobbyName = lobbyName;
	}

	@Override
	public String toString() {
		return super.toString() + " " + this.device.toString();
	}

}
