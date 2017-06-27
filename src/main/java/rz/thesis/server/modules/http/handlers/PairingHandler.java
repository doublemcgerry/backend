package rz.thesis.server.modules.http.handlers;

import java.util.Map;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.thesis.core.Core;
import rz.thesis.core.modules.http.HttpServer;
import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.core.modules.http.HttpSessionsManager;
import rz.thesis.core.modules.http.MappingsProvider;
import rz.thesis.core.modules.http.handlers.CommandHandler;
import rz.thesis.core.modules.http.handlers.JsonResponse;
import rz.thesis.core.modules.http.handlers.Serializer;
import rz.thesis.core.modules.http.handlers.WebVisHTTPD;
import rz.thesis.server.lobby.AuthenticationInformation;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.modules.ServerModule;
import rz.thesis.server.serialization.action.auth.AnnounceDemandAction;
import rz.thesis.server.serialization.action.auth.PairingConfirmationAction;

public class PairingHandler extends MappingsProvider {

	public static class PairingRequestCommand extends CommandHandler {

		@Override
		protected Response onPost(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			return null;
		}

		@Override
		protected Response onGet(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			Core core = uriResource.initParameter(0, Core.class);
			HttpSessionsManager sessionsManager = uriResource.initParameter(1, HttpSessionsManager.class);
			HttpServerSession serverSession = sessionsManager.retrieveSession(session);

			if (serverSession.isAuthenticated()) {
				ServerModule module = core.getModule(ServerModule.class);
				LobbiesManagerInterface lobbiesManager = module.getRouter();
				String deviceKey = urlParams.get("code");
				final AuthenticationInformation info = lobbiesManager.getAuthenticator().authenticate(serverSession,
						deviceKey);
				lobbiesManager.getAuthenticator().removeFromWaitingRoom(deviceKey);
				info.getactor().authenticate(info.getUsername());
				final PairingConfirmationAction confirmation = new PairingConfirmationAction(deviceKey,
						info.getUsername(), info.getactor().getAddress().toString());
				final AnnounceDemandAction demandAction = new AnnounceDemandAction();
				new Thread(new Runnable() {
					@Override
					public void run() {
						info.getactor().sendActionToRemote(confirmation);
						info.getactor().sendActionToRemote(demandAction);
					}
				}).start();
				JsonResponse resp = new JsonResponse();
				resp.put("code", deviceKey);
				resp.put("address", info.getactor().getAddress());
				resp.put("username", info.getUsername());
				return WebVisHTTPD.newFixedLengthResponse(Serializer.serialize(resp));
			}
			return null;
		}

		@Override
		public void addMappings(HttpServer server) {
			server.addMapping("/pairing/request/:code", this.getClass());
		}

	}
}
