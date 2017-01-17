package serialization.action.management;

import serialization.action.reply.AvailableLobbiesReply;
import serialization.action.reply.ReplyAction;
import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;

public class GetAvailableLobbiesAction extends ManagementAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4262165002016879306L;

	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		ReplyAction action = new AvailableLobbiesReply();
		action.execute(router, wrapper);
	}

}
