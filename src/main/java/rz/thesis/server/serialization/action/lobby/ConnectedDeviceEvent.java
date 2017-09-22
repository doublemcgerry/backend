package rz.thesis.server.serialization.action.lobby;

import java.util.List;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.SubscriberType;
import rz.thesis.server.sensors.SensorType;

public class ConnectedDeviceEvent extends LobbyEvent {

	private static final long serialVersionUID = 3826952011751893194L;
	private SubscriberType type;
	private List<SensorType> sensorTypes;
	private String lobbyName;
	private String deviceName;

	public ConnectedDeviceEvent(String lobbyName, String deviceName, SubscriberType type,
	        List<SensorType> sensorTypes) {
		super();
		this.type = type;
		this.deviceName = deviceName;
		this.lobbyName = lobbyName;
		this.sensorTypes = sensorTypes;
	}

	public ConnectedDeviceEvent(String lobbyName, LobbyActor lobbyActor) {
		this.type = lobbyActor.getActorType();
		this.deviceName = lobbyActor.getName();
		this.lobbyName = lobbyName;
		this.sensorTypes = lobbyActor.getSupportedSensors();
	}

	public SubscriberType getType() {
		return type;
	}

	public void setType(SubscriberType type) {
		this.type = type;
	}

	public String getLobbyName() {
		return lobbyName;
	}

	public void setLobbyName(String lobbyName) {
		this.lobbyName = lobbyName;
	}

}
