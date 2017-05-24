package rz.thesis.serialization.action.lobby;

import rz.server.SubscriberType;

public class DisconnectedDeviceEvent extends LobbyEvent {

	private static final long serialVersionUID = 3826952011751893194L;
	private SubscriberType type;
	private String sender;
	private String senderName;

	public DisconnectedDeviceEvent(String sender, String senderName, SubscriberType type) {
		super();
		this.senderName = senderName;
		this.type = type;
		this.sender = sender;
	}

	public SubscriberType getType() {
		return type;
	}

	public void setType(SubscriberType type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
