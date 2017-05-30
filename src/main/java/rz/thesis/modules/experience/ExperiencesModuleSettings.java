package rz.thesis.modules.experience;

import rz.thesis.core.modules.CoreModuleSettings;

/**
 * this class is used to indicate to the experience module which database to open
 * and what folder to use for storage
 * @author achelius
 *
 */
public class ExperiencesModuleSettings extends CoreModuleSettings {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2916487641839065596L;
	private String DBFilename;
	private String storageFolder;
	public ExperiencesModuleSettings(String DBFilename, String storageFolder) {
		this.DBFilename=DBFilename;
		this.storageFolder=storageFolder;
	}
	public String getDBFilename() {
		return DBFilename;
	}
	public String getStorageFolder() {
		return storageFolder;
	}
	
	
}
