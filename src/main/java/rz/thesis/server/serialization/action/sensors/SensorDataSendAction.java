package rz.thesis.server.serialization.action.sensors;

import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.utility.SensorData;

public class SensorDataSendAction extends SensorsAction {

	private static final long serialVersionUID = 6442731397402415499L;
	private SensorData data;
	private String sender;

	@Override
	public void execute(ServerLobby instance) {
		instance.broadcastAction(this);
	}

	@Override
	public String toString() {
		return sender + " has sent a new SensorDataSendAction [data=" + data + "]";
	}

}
