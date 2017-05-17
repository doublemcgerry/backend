package rz.thesis.serialization.action.reply;

import rz.server.ConnectionsRouter;
import rz.server.Subscriber;
import rz.thesis.serialization.action.Action;

public abstract class ReplyAction extends Action{
	
	private static final long serialVersionUID = 2460209641896946010L;
	public abstract void execute(ConnectionsRouter router, Subscriber wrapper);
}
