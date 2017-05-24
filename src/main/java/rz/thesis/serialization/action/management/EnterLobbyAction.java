package rz.thesis.serialization.action.management;

import java.util.UUID;

import rz.server.ConnectionsRouter;
import rz.server.Subscriber;

public abstract class EnterLobbyAction extends ManagementAction {

	private static final long serialVersionUID = 6722305545748319370L;
	private String lobby;
	private String sender;
	private String senderName;

	public EnterLobbyAction() {
	}

	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
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
