package serialization.action.sensors;

import websocketecho.ServerInstance;

public class StopWatchingSensorAction extends SensorsAction{

	private static final long serialVersionUID = -6781215837363877939L;
	private long timestamp;
	private String sender;
	
	@Override
	public String toString() {
		return sender +"has sent a new StopWatchingSensorAction at timestamp=" + timestamp;
	}

	@Override
	public void execute(ServerInstance instance) {
		instance.broadcastAction(this);
	}
}
