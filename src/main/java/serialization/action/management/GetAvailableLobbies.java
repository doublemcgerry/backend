package serialization.action.management;

import serialization.action.reply.AvailableLobbieReply;
import serialization.action.reply.ReplyAction;
import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;

public class GetAvailableLobbies extends ManagementAction {

	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		ReplyAction action = new AvailableLobbieReply();
		action.execute(router, wrapper);
	}

}
