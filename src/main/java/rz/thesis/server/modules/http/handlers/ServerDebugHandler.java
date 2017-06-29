package rz.thesis.server.modules.http.handlers;

import java.util.Map;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.thesis.core.Core;
import rz.thesis.core.modules.http.HttpServer;
import rz.thesis.core.modules.http.MappingsProvider;
import rz.thesis.core.modules.http.handlers.CommandHandler;
import rz.thesis.core.modules.http.handlers.WebVisHTTPD;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.modules.ServerModule;

public class ServerDebugHandler extends MappingsProvider {
	public static class getLobbiesStatus extends CommandHandler {

		@Override
		protected Response onPost(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Response onGet(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			Core core = uriResource.initParameter(0, Core.class);
			ServerModule module = core.getModule(ServerModule.class);
			LobbiesManagerInterface lobbiesManager = module.getRouter();

			return WebVisHTTPD.newFixedLengthResponse(lobbiesManager.getLobbiesStatus());

		}

		@Override
		public void addMappings(HttpServer server) {
			server.addMapping("/debug/lobby/list", this.getClass());
		}

	}
}
