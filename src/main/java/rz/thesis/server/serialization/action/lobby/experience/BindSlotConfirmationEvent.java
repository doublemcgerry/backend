package rz.thesis.server.serialization.action.lobby.experience;

import rz.thesis.modules.experience.Experience;
import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.lobby.DeviceDefinition;
import rz.thesis.server.serialization.action.lobby.LobbyEvent;

public class BindSlotConfirmationEvent extends LobbyEvent {

	private static final long serialVersionUID = 7394773610117295770L;
	private SensorType type;
	private DeviceDefinition device;
	private Experience experience;

	public BindSlotConfirmationEvent(SensorType type, DeviceDefinition device, Experience experience) {
		this.type = type;
		this.device = device;
		this.experience = experience;
	}

	public DeviceDefinition getDevice() {
		return device;
	}

	public SensorType getType() {
		return type;
	}

	public Experience getExperience() {
		return experience;
	}

}
