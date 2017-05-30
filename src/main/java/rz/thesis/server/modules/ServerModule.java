package rz.thesis.server.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rz.thesis.core.Core;
import rz.thesis.core.modules.CoreDependency;
import rz.thesis.core.modules.CoreModule;
import rz.thesis.core.modules.ServiceDefinition;
import rz.thesis.core.modules.http.HttpModule;
import rz.thesis.core.save.SaveModule;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManager;

public class ServerModule extends CoreModule {
	private final static List<CoreDependency> DEPENDENCIES = new ArrayList<>(
			Arrays.asList(new CoreDependency(HttpModule.class),new CoreDependency(ExperiencesModule.class)));
	private static final String NAME = ServerModule.class.getSimpleName();
	private SaveModule save;
	private LobbiesManager router;

	public ServerModule(Core core, ServerSettings settings) {
		super(NAME, core, settings, DEPENDENCIES);
	}

	@Override
	public void initializeModule() {
		save = this.getCore().getModule(SaveModule.class);
	}

	@Override
	public void startModule() {
		router = new LobbiesManager(getCore());
	}

	@Override
	public void stopModule() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ServiceDefinition> getServiceDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	public LobbiesManager getRouter() {
		return router;
	}

}
