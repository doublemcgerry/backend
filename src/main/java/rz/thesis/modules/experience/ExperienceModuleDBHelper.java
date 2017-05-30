package rz.thesis.modules.experience;

import rz.thesis.core.save.PersistentSettings;
import rz.thesis.server.lobby.ExperienceId;
/**
 * this class enables the communication with the experiences db
 * @author achelius
 *
 */
public class ExperienceModuleDBHelper extends PersistentSettings {
	private static final long serialVersionUID = 2262318424485219716L;
	private String experiencesDBFilename;
	private String experiencesStorageLocation;
		
	public ExperienceModuleDBHelper(ExperiencesModuleSettings settings) {
		this.experiencesDBFilename=settings.getDBFilename();
		this.experiencesStorageLocation=settings.getStorageFolder();
	}
	
	public String getExperiencesDBFilename() {
		return experiencesDBFilename;
	}

	public String getExperiencesStorageLocation() {
		return experiencesStorageLocation;
	}


	public Experience retrieveExperience(ExperienceId id){
		return new Experience(); //TODO really retrieve experience
	}
	
	public boolean containsExperience(ExperienceId id) {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	
	
	
}
