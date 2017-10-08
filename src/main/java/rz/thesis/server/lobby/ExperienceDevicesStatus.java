package rz.thesis.server.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rz.thesis.modules.experience.ExperienceDefinitionParameters;
import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.lobby.DeviceDefinition;

//TODO maximum numbers check
public class ExperienceDevicesStatus {

	private List<UUID> screens = new ArrayList<>();
	private int maxScreens = 1;
	private Map<SensorType, List<UUID>> sensors = new HashMap<>();
	private Map<SensorType, Integer> neededSensors;
	private boolean ready = false;

	public ExperienceDevicesStatus(ExperienceDefinitionParameters params) {
		this.neededSensors = params.getRequiredSensors();
		this.maxScreens = params.getMaxUsersCount();
		for (Map.Entry<SensorType, Integer> neededSensorsEntry : neededSensors.entrySet()) {
			sensors.put(neededSensorsEntry.getKey(), new ArrayList<UUID>());
		}
	}

	public int getMaxScreens() {
		return maxScreens;
	}

	public void setMaxScreens(int maxScreens) {
		this.maxScreens = maxScreens;
	}

	public Map<SensorType, List<UUID>> getSensors() {
		return sensors;
	}

	public Map<SensorType, Integer> getNeededSensors() {
		return neededSensors;
	}

	protected void addSensor(SensorType type, UUID address) {
		if (!this.sensors.containsKey(type)) {
			this.sensors.put(type, new ArrayList<UUID>());
		}
		this.sensors.get(type).add(address);
		this.setReady(calculateReadysness());
	}

	protected boolean canAddSensor(SensorType type) {
		if (!this.neededSensors.containsKey(type)) {
			return false;
		} else {
			return this.sensors.get(type).size() < this.neededSensors.get(type);
		}

	}

	protected void removeSensor(SensorType type, UUID address) {
		synchronized (this.sensors) {
			if (!this.sensors.containsKey(type)) {
				return;
			}
			this.sensors.get(type).remove(address);
		}
		this.setReady(calculateReadysness());
	}

	public void addScreen(UUID screen) {
		this.screens.add(screen);
		this.setReady(calculateReadysness());
	}

	public void removeScreen(UUID screen) {
		this.screens.remove(screen);
		this.setReady(calculateReadysness());
	}

	public boolean calculateReadysness() {
		if (screens.size() != maxScreens) {
			return false;
		}
		for (Map.Entry<SensorType, Integer> neededSensorsEntry : neededSensors.entrySet()) {
			if (!sensors.containsKey(neededSensorsEntry.getKey())) {
				return false;
			}
			if (sensors.get(neededSensorsEntry.getKey()).size() != neededSensorsEntry.getValue()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Screens (count:" + screens.size() + ")");
		for (UUID uuid : screens) {
			builder.append(uuid.toString() + "\n");
		}
		for (Map.Entry<SensorType, Integer> neededSensorsEntry : neededSensors.entrySet()) {
			builder.append(neededSensorsEntry.getKey().toString() + " "
					+ sensors.get(neededSensorsEntry.getKey()).size() + "/" + neededSensorsEntry.getValue() + "\n");
		}
		return builder.toString();
	}

	public void removeDevice(DeviceDefinition deviceDefinition) {
		if (deviceDefinition.getType() == SubscriberType.SCREEN) {
			this.removeScreen(deviceDefinition.getAddress());
		}
		List<SensorType> sensors = deviceDefinition.getSensorTypes();
		for (SensorType sensorType : sensors) {
			this.removeSensor(sensorType, deviceDefinition.getAddress());
		}

	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

}
