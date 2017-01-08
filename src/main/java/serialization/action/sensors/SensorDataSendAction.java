package serialization.action.sensors;

import utility.SensorData;
import websocketecho.ServerInstance;

public class SensorDataSendAction extends SensorsAction {

	private static final long serialVersionUID = 6442731397402415499L;
	private SensorData data;
	private String sender;

	@Override
	public void execute(ServerInstance instance) {
		instance.broadcastAction(this);
	}

	@Override
	public String toString() {
		return sender + " has sent a new SensorDataSendAction [data=" + data + "]";
	}

}
