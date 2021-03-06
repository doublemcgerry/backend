package rz.thesis.server.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import rz.thesis.core.Core;
import rz.thesis.core.modules.discovery.DiscoveryModule;
import rz.thesis.core.modules.discovery.DiscoveryModuleSettings;
import rz.thesis.core.modules.http.HttpModule;
import rz.thesis.core.modules.http.HttpModuleSettings;
import rz.thesis.core.modules.http.MappingsProvider;
import rz.thesis.core.modules.http.handlers.LoginHttpHandler;
import rz.thesis.core.options.SoftwareOptionsReader;
import rz.thesis.core.project.images.ImagesModule;
import rz.thesis.core.project.security.UserAuthentication;
import rz.thesis.core.save.SaveModule;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.modules.experience.ExperiencesModuleSettings;
import rz.thesis.modules.experience.handlers.ExperienceHandler;
import rz.thesis.server.devices.DeviceDefinitionsHandler;
import rz.thesis.server.modules.ServerModule;
import rz.thesis.server.modules.ServerSettings;
import rz.thesis.server.modules.http.handlers.PairingHandler;
import rz.thesis.server.modules.http.handlers.ServerDebugHandler;
import rz.thesis.server.websocket.WebSocketFactory;

public class RelayMain {
	private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
	private Core core;

	public static void main(String[] args) {
		Logger.getRootLogger().addAppender(new LogAppender());
		RelayMain main = new RelayMain();
		main.initialize();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String command = sc.nextLine();
			if (command.startsWith("convert")) {

			} else if (command.startsWith("quit")) {
				main.core.stop();
				break;
			}
		}
		sc.close();
	}

	public void initialize() {
		LOGGER.debug("Initialization");
		String startDir = System.getProperty("user.dir");
		File prjsDir = new File(startDir + File.separator + "relay");
		if (!prjsDir.exists()) {
			prjsDir.mkdir();
		}
		File includeDir = new File(startDir + File.separator + "include");
		if (!includeDir.exists()) {
			includeDir.mkdir();
		}
		SoftwareOptionsReader sor = new SoftwareOptionsReader(new File(prjsDir.getPath(), "config.xml"));
		sor.setValue("projectFolder", prjsDir.getPath());
		core = new Core(sor, includeDir.getPath(), new LogAppender());

		UserAuthentication authentication = new UserAuthentication(core.getModule(SaveModule.class));

		ServerModule serverModule = new ServerModule(core, new ServerSettings());
		core.addModule(serverModule);

		WebSocketFactory websocketFactory = new WebSocketFactory(serverModule);
		List<Class<? extends MappingsProvider>> handlers = new ArrayList<>();
		handlers.add(LoginHttpHandler.class);
		handlers.add(DeviceDefinitionsHandler.class);
		handlers.add(ExperienceHandler.class);
		handlers.add(PairingHandler.class);
		handlers.add(ServerDebugHandler.class);
		HttpModule httpmodule = new HttpModule(core, new HttpModuleSettings(8010, "../Framework/"), authentication,
				handlers, websocketFactory);
		core.addModule(httpmodule);

		ImagesModule imagesModule = new ImagesModule(core);
		core.addModule(imagesModule);
		DiscoveryModuleSettings dmSettings = new DiscoveryModuleSettings(9000, "DISCOVER_SERVICES");
		DiscoveryModule discoveryModule = new DiscoveryModule(core, dmSettings);
		core.addModule(discoveryModule);

		ExperiencesModuleSettings expModuleSettings = new ExperiencesModuleSettings("experiences.db", "expStorage");
		ExperiencesModule expModule = new ExperiencesModule(core, expModuleSettings);
		core.addModule(expModule);

		core.start();
	}

}
