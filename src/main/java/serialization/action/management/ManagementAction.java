package serialization.action.management;

import serialization.action.Action;
import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;

public abstract class ManagementAction extends Action{
	private static final long serialVersionUID = 3281620540341135676L;
	public abstract void execute(ConnectionsRouter router, Subscriber wrapper);
}
