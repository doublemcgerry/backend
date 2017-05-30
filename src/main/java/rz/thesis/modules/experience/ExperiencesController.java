package rz.thesis.modules.experience;

import rz.thesis.core.Core;
import rz.thesis.server.lobby.ExperienceId;

public class ExperiencesController {
	private static final String FILENAME = "ExperiencesDB";
	private Core core;
	private ExperienceModuleDBHelper db;

	public ExperiencesController(Core core,ExperiencesModuleSettings settings) {
		this.core = core;
		this.db = new ExperienceModuleDBHelper(settings);
	}
	
	public Experience getExperience(ExperienceId id){
		return this.db.retrieveExperience(id);
	}
	
	public boolean containsExperienceId(ExperienceId id){
		return this.db.containsExperience(id);
	}

}
