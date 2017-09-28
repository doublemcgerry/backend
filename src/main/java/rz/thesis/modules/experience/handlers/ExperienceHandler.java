package rz.thesis.modules.experience.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.UUID;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.thesis.core.Core;
import rz.thesis.core.modules.http.HttpServer;
import rz.thesis.core.modules.http.MappingsProvider;
import rz.thesis.core.modules.http.handlers.CommandHandler;
import rz.thesis.core.modules.http.handlers.JsonResponse;
import rz.thesis.core.modules.http.handlers.Serializer;
import rz.thesis.core.modules.http.handlers.WebVisHTTPD;
import rz.thesis.modules.experience.Experience;
import rz.thesis.modules.experience.ExperiencesModule;

public class ExperienceHandler extends MappingsProvider {
	public static class ExperiencesList extends CommandHandler {
		private static final int[] REQUIRED_PERMISSIONS = {};

		@Override
		protected int[] getRequiredPermissions() {
			return REQUIRED_PERMISSIONS;
		}

		@Override
		protected Response onPost(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Response onGet(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			Core core = uriResource.initParameter(0, Core.class);
			ExperiencesModule experiencesModule = core.getModule(ExperiencesModule.class);
			JsonResponse response = new JsonResponse();
			response.put("data", experiencesModule.getController().getExperiencesList(0));
			// TODO retrieve user id
			String serializedList = Serializer.serialize(response);
			return WebVisHTTPD.newFixedLengthResponse(Status.OK, "application/json", serializedList);
		}

		@Override
		public void addMappings(HttpServer server) {
			server.addMapping("/experiences/list", this.getClass());
		}

	}

	public static class ExperienceParametersRetrieve extends CommandHandler {
		private static final int[] REQUIRED_PERMISSIONS = {};

		@Override
		protected int[] getRequiredPermissions() {
			return REQUIRED_PERMISSIONS;
		}

		@Override
		protected Response onPost(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			return null;
		}

		@Override
		protected Response onGet(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
			Core core = uriResource.initParameter(0, Core.class);
			ExperiencesModule experiencesModule = core.getModule(ExperiencesModule.class);
			JsonResponse response = new JsonResponse();
			UUID experienceId = UUID.fromString(urlParams.get("id"));
			Experience exp = experiencesModule.getController().getExperience(0, experienceId);
			try {
				response.put("info", exp.getParameters());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String serializedList = Serializer.serialize(response);
			return WebVisHTTPD.newFixedLengthResponse(Status.OK, "application/json", serializedList);
		}

		@Override
		public void addMappings(HttpServer server) {
			server.addMapping("/experiences/parameters/:id", this.getClass());
		}

	}

	public static class ExperienceDataFileRetrieve extends CommandHandler {
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
			Core core = uriResource.initParameter(0, Core.class);
			ExperiencesModule experiencesModule = core.getModule(ExperiencesModule.class);
			UUID experienceId = UUID.fromString(urlParams.get("id"));
			Experience exp = experiencesModule.getController().getExperience(0, experienceId);
			File dataFile = new File(exp.getDataCompletePath());
			try {
				return WebVisHTTPD.newChunkedResponse(Status.OK, "application/json", new FileInputStream(dataFile));
			} catch (FileNotFoundException e) {
				return WebVisHTTPD.newFixedLengthResponse(Status.NOT_FOUND, "text/html",
						CommandHandler.NOT_FOUND_MESSAGE);
			}
		}

		@Override
		public void addMappings(HttpServer server) {
			server.addMapping("/experiences/data/:id", this.getClass());
		}
	}
}
