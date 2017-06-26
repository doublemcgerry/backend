package rz.thesis.server.lobby;

import rz.thesis.server.lobby.actors.VirtualActor;

public class AuthenticationInformation {
	private String username;
	private String deviceKey;
	private VirtualActor actor;
	private Tunnel authenticator;

	public AuthenticationInformation(String username, String deviceKey, Tunnel authenticator,
			VirtualActor authenticatedActor) {
		super();
		this.username = username;
		this.deviceKey = deviceKey;
		this.actor = authenticatedActor;
		this.authenticator = authenticator;
	}

	public String getUsername() {
		return username;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public Tunnel getAuthenticator() {
		return authenticator;
	}

	public VirtualActor getactor() {
		return actor;
	}

}
