package rz.thesis.modules.experience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rz.thesis.core.Core;
import rz.thesis.core.modules.CoreDependency;
import rz.thesis.core.modules.CoreModule;
import rz.thesis.core.modules.CoreModuleSettings;
import rz.thesis.core.modules.ServiceDefinition;
import rz.thesis.core.save.SaveModule;

public class ExperiencesModule extends CoreModule {
	
	private final static List<CoreDependency> DEPENDENCIES = new ArrayList<>(
			Arrays.asList(new CoreDependency(SaveModule.class)));
	private static final String NAME = ExperiencesModule.class.getSimpleName();
	
	
	private ExperiencesController controller;
	public ExperiencesModule(Core core, ExperiencesModuleSettings settings) {
		super(NAME, core, settings, DEPENDENCIES);
	}

	
	@Override
	public void initializeModule() {
		controller= new ExperiencesController(getCore(), (ExperiencesModuleSettings) getSettings());
	}

	@Override
	public void startModule() {
		
	}

	@Override
	public void stopModule() {
		
	}

	@Override
	public List<ServiceDefinition> getServiceDefinition() {
		return null;
	}

}
