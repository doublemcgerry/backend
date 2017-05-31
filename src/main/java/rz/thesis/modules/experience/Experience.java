package rz.thesis.modules.experience;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.stream.JsonReader;

import rz.thesis.core.modules.http.handlers.Serializer;
import rz.thesis.server.lobby.ExperienceId;

public class Experience {
	private ExperienceDefinitionParameters parameters = null;

	private ExperienceId id;
	private String dataFilename;
	private String infoFilename;
	private String baseExperiencePath;

	public Experience(String baseExperiencePath, ExperienceId id, String dataFilename, String infoFilename) {
		this.baseExperiencePath = baseExperiencePath;
		this.id = id;
		this.dataFilename = dataFilename;
		this.infoFilename = infoFilename;
	}

	public ExperienceId getId() {
		return id;
	}

	public String getDataFilename() {
		return dataFilename;
	}

	public String getInfoFilename() {
		return infoFilename;
	}

	public ExperienceDefinitionParameters getParameters() throws FileNotFoundException {
		synchronized (parameters) {
			if (parameters == null) {
				FileReader reader = new FileReader(baseExperiencePath + "/" + infoFilename);
				JsonReader jsonReader = new JsonReader(reader);
				this.parameters = Serializer.getSerialiser().fromJson(jsonReader, ExperienceDefinitionParameters.class);
			}
			return this.parameters;
		}
	}

}
