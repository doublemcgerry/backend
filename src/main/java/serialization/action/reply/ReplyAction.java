package serialization.action.reply;

import serialization.action.Action;
import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;

public abstract class ReplyAction extends Action{
	
	private static final long serialVersionUID = 2460209641896946010L;
	public abstract void execute(ConnectionsRouter router, Subscriber wrapper);
}
