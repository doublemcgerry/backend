package serialization.action.sensors;

import websocketecho.ServerInstance;

public class StartWatchingSensorAction extends SensorsAction {

	private static final long serialVersionUID = 815596656975368143L;
	private long timestamp;

	@Override
	public void execute(ServerInstance instance) {
		instance.broadcastAction(this);
	}

}
