package rz.thesis.server.lobby;

import java.util.UUID;

public class ExperienceId {

	private final UUID id;

	private ExperienceId(UUID id) {
		this.id = id;
	}

	public static ExperienceId randomExperienceId() {
		return new ExperienceId(UUID.randomUUID());
	}

	public static boolean Equals(ExperienceId id, ExperienceId id2) {
		if (id == null && id2 == null) {
			return true;
		}
		if (id == null ^ id2 == null) {
			return false;
		}
		return id.equals(id2);
	}

	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(obj);
	}

	public static ExperienceId fromString(String id) {
		return new ExperienceId(UUID.fromString(id));
	}

}
