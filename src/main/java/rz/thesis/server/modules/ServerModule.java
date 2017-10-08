package rz.thesis.server.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rz.thesis.core.Core;
import rz.thesis.core.modules.CoreDependency;
import rz.thesis.core.modules.CoreModule;
import rz.thesis.core.modules.ServiceDefinition;
import rz.thesis.core.modules.http.HttpModule;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.LobbiesManagerInterface;

public class ServerModule extends CoreModule {
	private final static List<CoreDependency> DEPENDENCIES = new ArrayList<>(
			Arrays.asList(new CoreDependency(HttpModule.class), new CoreDependency(ExperiencesModule.class)));
	private static final String NAME = ServerModule.class.getSimpleName();
	private LobbiesManagerInterface router;

	public ServerModule(Core core, ServerSettings settings) {
		super(NAME, core, settings, DEPENDENCIES);
	}

	@Override
	public void initializeModule() {

	}

	@Override
	public void startModule() {
		router = new LobbiesManager(getCore());
	}

	@Override
	public void stopModule() {

	}

	@Override
	public List<ServiceDefinition> getServiceDefinition() {
		return null;
	}

	public LobbiesManagerInterface getRouter() {
		return router;
	}

}
