package rz.thesis.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.joda.time.DateTime;

import rz.thesis.core.Core;
import rz.thesis.core.modules.discovery.DiscoveryModule;
import rz.thesis.core.modules.discovery.DiscoveryModuleSettings;
import rz.thesis.core.modules.http.HttpModule;
import rz.thesis.core.modules.http.HttpModuleSettings;
import rz.thesis.core.modules.http.MappingsProvider;
import rz.thesis.core.options.SoftwareOptionsReader;
import rz.thesis.core.project.images.ImagesModule;
import rz.thesis.core.project.security.UserAuthentication;
import rz.thesis.core.save.SaveModule;
import rz.thesis.modules.ServerModule;
import rz.thesis.modules.ServerSettings;
import rz.thesis.websocket.WebSocketFactory;

public class ServerMain {
	private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
	private Core core;

	public static void main(String[] args) {
		Logger.getRootLogger().addAppender(new LogAppender());
		ServerMain main = new ServerMain();
		main.initialize();
		SaveModule sm = main.core.getModule(SaveModule.class);
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

	public ServerMain() {

	}

	public void initialize() {
		LOGGER.debug("Initialization");
		String startDir = System.getProperty("user.dir");
		File prjsDir = new File(startDir + File.separator + "data");
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
		HttpModule httpmodule = new HttpModule(core, new HttpModuleSettings(8010, "../Framework/"), authentication,
				handlers, websocketFactory);
		core.addModule(httpmodule);

		ImagesModule imagesModule = new ImagesModule(core);
		core.addModule(imagesModule);
		DiscoveryModuleSettings dmSettings = new DiscoveryModuleSettings(9000, "DISCOVER_SERVICES");
		DiscoveryModule discoveryModule = new DiscoveryModule(core, dmSettings);
		core.addModule(discoveryModule);

		core.start();
	}

}

class LogAppender extends AppenderSkeleton {

	protected LogAppender() {

	}

	@Override
	protected void append(LoggingEvent event) {

		if (event.getLoggerName().startsWith("rz.thesis.")) {
			System.out.print(DateTime.now().toString("dd-mm-yyyy HH:mm:ss"));
			System.out.print(":");
			System.out.print(new String(event.getThreadName()));
			System.out.print("-->");
			System.out.println((String) (event.getMessage()));
		} else {
			System.out.println(event.getLoggerName());
		}
	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}

class DummyLogAppender extends AppenderSkeleton {

	@Override
	protected void append(LoggingEvent event) {
	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}
}
