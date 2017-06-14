package rz.thesis.modules.experience;

import java.util.List;
import java.util.UUID;

import rz.thesis.core.Core;

public class ExperiencesController {
	private ExperienceModuleDBHelper db;

	public ExperiencesController(Core core, ExperiencesModuleSettings settings) {
		this.db = new ExperienceModuleDBHelper(core.getProjectFolder(), settings);
	}

	public Experience getExperience(int userId, UUID id) {
		return this.db.retrieveExperience(userId, id);
	}

	public boolean containsExperienceId(int userId, UUID id) {
		return this.db.containsExperience(userId, id);
	}

	public List<Experience> getExperiencesList(int userId) {
		return this.db.getExperiencesList(userId);
	}
}
