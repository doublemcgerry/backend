package rz.thesis.modules.experience;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rz.thesis.server.sensors.SensorType;

//TODO maximum numbers check
public class ExperienceDevicesStatus {

	private List<UUID> screens = new ArrayList<>();
	private int maxScreens = 1;
	private Map<SensorType, List<UUID>> sensors = new HashMap<>();
	private Map<SensorType, Integer> neededSensors;

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

	public void addSensor(SensorType type, UUID address) {
		if (!this.sensors.containsKey(type)) {
			this.sensors.put(type, new ArrayList<UUID>());
		}
		this.sensors.get(type).add(address);
	}

	public boolean canAddSensor(SensorType type) {
		if (!this.neededSensors.containsKey(type)) {
			return false;
		} else {
			return this.sensors.get(type).size() < this.neededSensors.get(type);
		}

	}

	public void removeSensor(SensorType type, UUID address) {
		if (!this.sensors.containsKey(type)) {
			return;
		}
		this.sensors.get(type).remove(address);
	}

	public void addScreen(UUID screen) {
		this.screens.add(screen);
	}

	public void removeScreen(UUID screen) {
		this.screens.remove(screen);
	}

	public boolean isReady() {
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

}
