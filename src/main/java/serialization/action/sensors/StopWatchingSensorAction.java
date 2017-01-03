package serialization.action.sensors;

import websocketecho.ServerInstance;

public class StopWatchingSensorAction extends SensorsAction{

	private static final long serialVersionUID = -6781215837363877939L;
	private long timestamp;

	@Override
	public void execute(ServerInstance instance) {
		instance.broadcastAction(this);
	}
}
