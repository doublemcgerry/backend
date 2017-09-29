package rz.thesis.server.serialization.action.lobby.experience;

import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.lobby.DeviceDefinition;
import rz.thesis.server.serialization.action.lobby.LobbyEvent;

public class BindSlotConfirmationEvent extends LobbyEvent {

	private static final long serialVersionUID = 7394773610117295770L;
	private SensorType type;
	private DeviceDefinition device;

	public BindSlotConfirmationEvent(SensorType type, DeviceDefinition device) {
		this.type = type;
		this.device = device;
	}

	public DeviceDefinition getDevice() {
		return device;
	}

	public SensorType getType() {
		return type;
	}

}
