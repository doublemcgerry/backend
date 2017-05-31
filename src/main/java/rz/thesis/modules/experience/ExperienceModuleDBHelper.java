package rz.thesis.modules.experience;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rz.thesis.server.lobby.ExperienceId;

/**
 * this class enables the communication with the experiences db
 * 
 * @author achelius
 *
 */
public class ExperienceModuleDBHelper {
	private static final Logger LOGGER = Logger.getLogger(ExperienceModuleDBHelper.class.getName());
	private String experiencesDBFilename;
	private String experiencesStorageLocation;
	private Connection DBConnection;
	private String projectPath;

	/**
	 * Creates a new database helper, it requires project path and base settings
	 * to load the database and the storage
	 * 
	 * @param projectPath
	 *            base path of the project (retrieve it from core)
	 * @param settings
	 *            specific settings for the database and storage (relative
	 *            paths)
	 */
	public ExperienceModuleDBHelper(String projectPath, ExperiencesModuleSettings settings) {
		this.experiencesDBFilename = settings.getDBFilename();
		this.experiencesStorageLocation = settings.getStorageFolder();
		this.projectPath = projectPath;
		String absoluteDBPath = projectPath + "/" + experiencesDBFilename;
		String url = "jdbc:sqlite:" + absoluteDBPath;
		if (!new File(absoluteDBPath).exists()) {
			try {
				this.DBConnection = DriverManager.getConnection(url);
				String createExperiencesTableSql = "CREATE TABLE IF NOT EXISTS experiences ( id text PRIMARY KEY,"
						+ " dataFilename text, infoFilename text)";
				Statement stmt = this.DBConnection.createStatement();
				stmt.execute(createExperiencesTableSql);
				String createAssociationsTableSql = "CREATE TABLE IF NOT EXISTS associations ( userId INTEGER PRIMARY KEY,"
						+ " experienceId text)";
				stmt.execute(createAssociationsTableSql);
			} catch (SQLException e) {
				LOGGER.error(e);
			}
		} else {
			try {
				this.DBConnection = DriverManager.getConnection(url);
			} catch (SQLException e) {
				LOGGER.error(e);
			}
		}
	}

	/**
	 * the experiences database filename, this database contains the experiences
	 * properties and user-experiences associations
	 * 
	 * @return
	 */
	public String getExperiencesDBFilename() {
		return experiencesDBFilename;
	}

	/**
	 * the storage location folder where the experiences files are stored
	 * 
	 * @return
	 */
	public String getExperiencesStorageLocation() {
		return experiencesStorageLocation;
	}

	/**
	 * retrieves the experience from the db, if the user has access to it
	 * 
	 * @param id
	 *            id of the experience to retrieve from the db
	 * @return the experience object if found, null if not found
	 */
	public Experience retrieveExperience(int userId, ExperienceId id) {

		String sql = "SELECT id FROM experiences"; // TODO also check
													// permissions
		try {
			PreparedStatement stmt = this.DBConnection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (!rs.isAfterLast()) {
				String dataFilename = rs.getString(2);
				String infoFilename = rs.getString(3);
				return new Experience(projectPath + "/" + experiencesStorageLocation, id, dataFilename, infoFilename);
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return null;
	}

	/**
	 * Checks if the experience is accessible and present into the database
	 * 
	 * @param userId
	 *            user that you need to check if has access to the experience
	 * @param id
	 *            id of the experience to check the presence
	 * @return true if the user has access to the experience, false otherwise
	 */
	public boolean containsExperience(int userId, ExperienceId id) {
		return retrieveExperience(userId, id) == null;
		// TODO migliorare questa evitando di chiamare retrieve experience
	}

	/**
	 * Returns the list of the experiences that the user has access to
	 * 
	 * @param userId
	 *            id of the user to retrieve the list of experiences
	 * @return a list of experiences
	 */
	public List<Experience> getExperiencesList(int userId) {
		List<Experience> retList = new ArrayList<>();
		String sql = "SELECT id FROM experiences"; // TODO also check
													// permissions
		try {
			PreparedStatement stmt = this.DBConnection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (!rs.isAfterLast()) {
				ExperienceId id = (ExperienceId.fromString(rs.getString(1)));
				String dataFilename = rs.getString(2);
				String infoFilename = rs.getString(3);
				retList.add(
						new Experience(projectPath + "/" + experiencesStorageLocation, id, dataFilename, infoFilename));
				rs.next();
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return retList;
	}

}
