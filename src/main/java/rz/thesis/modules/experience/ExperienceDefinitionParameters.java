package rz.thesis.modules.experience;

import java.util.HashMap;
import java.util.Map;

import rz.thesis.server.sensors.SensorType;

/**
 * This class contains all the information for a specific experience options
 * 
 * @author achelius
 *
 */
public class ExperienceDefinitionParameters {
	/**
	 * Simply the name of the experience, what did you expect?
	 */
	private String name;
	/**
	 * the max number of acting users that can connect to this experience
	 * (default = 1)
	 */
	private int maxUsersCount = 1;
	/**
	 * the required sensors to fully enjoy the experience
	 */
	private Map<SensorType, Integer> requiredSensors = new HashMap<>();
	/**
	 * the max number of spectators allowed into the experience (default = 0)
	 */
	private int maxSpectators = 0;

	/**
	 * thumbnail image address
	 */
	private String thumbnailHandle = "";

	/**
	 * background image handle
	 */
	private String backgroundHandle = "";

	/**
	 * description of the experience
	 */
	private String description = "experience description";

	public ExperienceDefinitionParameters() {

	}

	public ExperienceDefinitionParameters(String name, int maxUsersCount, int maxSpectators) {
		this.name = name;
		this.maxUsersCount = maxUsersCount;
		this.maxSpectators = maxSpectators;
	}

	public String getName() {
		return name;
	}

	public int getMaxUsersCount() {
		return maxUsersCount;
	}

	public Map<SensorType, Integer> getRequiredSensors() {
		return requiredSensors;
	}

	public int getMaxSpectators() {
		return maxSpectators;
	}

	public String getDescription() {
		return description;
	}

	public String getThumbnailHandle() {
		return thumbnailHandle;
	}

	public String getBackgroundHandle() {
		return backgroundHandle;
	}

}
