package rz.thesis.modules.experience;

import java.util.List;

import rz.thesis.core.Core;
import rz.thesis.server.lobby.ExperienceId;

public class ExperiencesController {
	private ExperienceModuleDBHelper db;

	public ExperiencesController(Core core, ExperiencesModuleSettings settings) {
		this.db = new ExperienceModuleDBHelper(core.getProjectFolder(), settings);
	}

	public Experience getExperience(int userId, ExperienceId id) {
		return this.db.retrieveExperience(userId, id);
	}

	public boolean containsExperienceId(int userId, ExperienceId id) {
		return this.db.containsExperience(userId, id);
	}

	public List<Experience> getExperiencesList(int userId) {
		return this.db.getExperiencesList(userId);
	}
}
