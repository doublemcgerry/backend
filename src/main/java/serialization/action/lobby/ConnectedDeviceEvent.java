package serialization.action.lobby;

import websocketecho.ServerInstance;
import websocketecho.Subscriber;
import websocketecho.SubscriberType;

public class ConnectedDeviceEvent extends LobbyEvent {

	private static final long serialVersionUID = 3826952011751893194L;
	private SubscriberType type;
	private String sender;

	public ConnectedDeviceEvent(String sender, SubscriberType type) {
		super();
		this.type = type;
		this.sender = sender;
	}

}
