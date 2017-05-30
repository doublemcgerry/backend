package rz.thesis.server.serialization.action.management;

import java.util.UUID;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;

public abstract class EnterLobbyAction extends ManagementAction {

	private static final long serialVersionUID = 6722305545748319370L;
	private String lobby;
	private String sender;
	private String senderName;

	public EnterLobbyAction() {
	}

	@Override
	public void execute(LobbiesManager router, Subscriber wrapper) {
		wrapper.setName(senderName);
		wrapper.setUUID(UUID.fromString(sender));
	}

	public String getLobby() {
		return lobby;
	}

	public String getSender() {
		return sender;
	}

}
