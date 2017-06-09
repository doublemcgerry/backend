package rz.thesis.server.lobby;

public class AuthenticationInformation {
	private String username;
	private String deviceKey;
	private LobbyActor device;
	private Subscriber authenticator;

	public AuthenticationInformation(String username, String deviceKey, LobbyActor device, Subscriber authenticator) {
		super();
		this.username = username;
		this.deviceKey = deviceKey;
		this.device = device;
		this.authenticator = authenticator;
	}

	public String getUsername() {
		return username;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public LobbyActor getDevice() {
		return device;
	}

	public Subscriber getAuthenticator() {
		return authenticator;
	}

}
