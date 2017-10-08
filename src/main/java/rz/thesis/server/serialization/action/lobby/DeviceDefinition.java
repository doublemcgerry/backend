package rz.thesis.server.serialization.action.lobby;

import java.util.List;
import java.util.UUID;

import rz.thesis.server.lobby.SubscriberType;
import rz.thesis.server.sensors.SensorType;

public class DeviceDefinition {
	private SubscriberType type;
	private List<SensorType> sensorTypes;
	private String deviceName;
	private UUID address;

	public DeviceDefinition(String name, UUID address, SubscriberType type, List<SensorType> sensorTypes) {
		this.type = type;
		this.deviceName = name;
		this.sensorTypes = sensorTypes;
		this.address = address;
	}

	public SubscriberType getType() {
		return type;
	}

	public List<SensorType> getSensorTypes() {
		return sensorTypes;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setType(SubscriberType type) {
		this.type = type;
	}

	public UUID getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return super.toString() + " Device, type:" + type + " deviceName:" + deviceName + " address:" + this.address
				+ "{" + this.sensorTypes.toString() + "}";
	}

}
