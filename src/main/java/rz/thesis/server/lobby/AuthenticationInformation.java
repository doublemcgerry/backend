package rz.thesis.server.lobby;

import rz.thesis.core.modules.http.HttpServerSession;

public class AuthenticationInformation {
	private String username;
	private String deviceKey;
	private HttpServerSession serverSession;
	private Subscriber device;
	private Subscriber authenticator;

	public AuthenticationInformation(String username, String deviceKey, Subscriber authenticatedSubscriber,
			Subscriber authenticator, HttpServerSession httpServerSession) {
		super();
		this.username = username;
		this.deviceKey = deviceKey;
		this.device = authenticatedSubscriber;
		this.authenticator = authenticator;
		this.serverSession = httpServerSession;
	}

	public String getUsername() {
		return username;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public Subscriber getDeviceSubscriber() {
		return device;
	}

	public Subscriber getAuthenticator() {
		return authenticator;
	}

	public HttpServerSession getServerSession() {
		return serverSession;
	}

}
