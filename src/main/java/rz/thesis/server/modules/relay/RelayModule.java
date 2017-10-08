package rz.thesis.server.modules.relay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rz.thesis.core.Core;
import rz.thesis.core.modules.CoreDependency;
import rz.thesis.core.modules.CoreModule;
import rz.thesis.core.modules.ServiceDefinition;
import rz.thesis.core.save.SaveModule;

public class RelayModule extends CoreModule {
	private final static List<CoreDependency> DEPENDENCIES = new ArrayList<>(
			Arrays.asList(new CoreDependency(SaveModule.class)));
	private static final String NAME = RelayModule.class.getSimpleName();

	public RelayModule(Core core, RelayServerSettings settings) {
		super(NAME, core, settings, DEPENDENCIES);
	}

	@Override
	public void initializeModule() {

	}

	@Override
	public void startModule() {
		// TODO Auto-generated method stub

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

}
