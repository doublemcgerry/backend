package rz.thesis.serialization.action.sensors;

import rz.server.ServerInstance;

public class StartWatchingSensorAction extends SensorsAction {

	private static final long serialVersionUID = 815596656975368143L;
	private long timestamp;
	private String sender;

	@Override
	public String toString() {
		return sender +"has sent a new StartWatchingSensorAction at timestamp=" + timestamp;
	}

	@Override
	public void execute(ServerInstance instance) {
		instance.broadcastAction(this);
	}

}
