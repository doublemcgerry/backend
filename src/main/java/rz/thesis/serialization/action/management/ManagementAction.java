package rz.thesis.serialization.action.management;

import rz.server.ConnectionsRouter;
import rz.server.Subscriber;
import rz.thesis.serialization.action.Action;

public abstract class ManagementAction extends Action{
	private static final long serialVersionUID = 3281620540341135676L;
	public abstract void execute(ConnectionsRouter router, Subscriber wrapper);
}
