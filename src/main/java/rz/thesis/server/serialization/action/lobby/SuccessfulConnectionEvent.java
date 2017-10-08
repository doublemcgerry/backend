package rz.thesis.server.serialization.action.lobby;

public class SuccessfulConnectionEvent extends LobbyEvent {
	private static final long serialVersionUID = 8229417269965927669L;

	private String lobby;

	public SuccessfulConnectionEvent(String lobby) {
		this.lobby = lobby;
	}

	public String getLobby() {
		return lobby;
	}

}
