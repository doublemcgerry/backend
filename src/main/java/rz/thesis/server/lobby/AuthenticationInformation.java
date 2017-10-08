package rz.thesis.server.lobby;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.server.lobby.actors.VirtualActor;

public class AuthenticationInformation {
	private String username;
	private String deviceKey;
	private VirtualActor actor;
	private HttpServerSession session;

	public AuthenticationInformation(String username, String deviceKey, HttpServerSession session,
			VirtualActor authenticatedActor) {
		super();
		this.username = username;
		this.deviceKey = deviceKey;
		this.actor = authenticatedActor;
		this.session = session;
	}

	public String getUsername() {
		return username;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public HttpServerSession getServerSession() {
		return session;
	}

	public VirtualActor getactor() {
		return actor;
	}

}
