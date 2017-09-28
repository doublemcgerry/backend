package rz.thesis.server.serialization.action.lobby.experience;

import rz.thesis.server.serialization.action.lobby.DeviceDefinition;
import rz.thesis.server.serialization.action.lobby.LobbyEvent;

public class BindSlotConfirmationEvent extends LobbyEvent {

	private static final long serialVersionUID = 7394773610117295770L;
	private DeviceDefinition device;

	public BindSlotConfirmationEvent(DeviceDefinition device) {
		this.device = device;
	}

	public DeviceDefinition getDevice() {
		return device;
	}

	public void setDevice(DeviceDefinition device) {
		this.device = device;
	}

}
