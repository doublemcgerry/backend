package serialization.action.sensors;

import utility.SensorData;
import websocketecho.ServerInstance;

public class SensorDataSendAction extends SensorsAction {

	private static final long serialVersionUID = 6442731397402415499L;
	private SensorData data;

	@Override
	public void execute(ServerInstance instance) {
		instance.broadcastAction(this);
	}

}
