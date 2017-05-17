package rz.thesis.serialization.action.sensors;

import rz.server.ServerInstance;
import rz.thesis.serialization.action.Action;

public abstract class SensorsAction extends Action {
	private static final long serialVersionUID = -2774926282070444319L;
	public abstract void execute(ServerInstance instance);
}
