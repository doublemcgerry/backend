package rz.thesis.server.devices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.thesis.core.Core;
import rz.thesis.core.modules.http.HttpServer;
import rz.thesis.core.modules.http.MappingsProvider;
import rz.thesis.core.modules.http.handlers.CommandHandler;
import rz.thesis.core.modules.http.handlers.WebVisHTTPD;

public class DeviceDefinitionsHandler extends MappingsProvider {
	public static class DownloadDefinition extends CommandHandler {
		private static final int[] REQUIRED_PERMISSIONS = {};

		@Override
		protected int[] getRequiredPermissions() {
			return REQUIRED_PERMISSIONS;
		}

		@Override
		protected boolean needAuthentication() {
			return false;
		}

		@Override
		protected Response onPost(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			return null;
		}

		@Override
		protected Response onGet(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			String id = urlParams.get("id"); // TODO remove path trasversal
												// attack
			if (id != null) {
				Core core = uriResource.initParameter(0, Core.class);
				File fileobj = new File(core.getProjectFolder() + "/drivers/", id);
				if (fileobj.exists()) {
					FileInputStream file = null;
					try {
						file = new FileInputStream(fileobj);
					} catch (FileNotFoundException e) {

					}
					return WebVisHTTPD.newChunkedResponse(Status.OK, "application/json", file);
				} else {
					return WebVisHTTPD.newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "ucc'è");
				}
			} else {
				return WebVisHTTPD.newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "ucc'è");
			}

		}

		@Override
		public void addMappings(HttpServer server) {
			server.addMapping("/drivers/:id", this.getClass());
		}

	}
}
