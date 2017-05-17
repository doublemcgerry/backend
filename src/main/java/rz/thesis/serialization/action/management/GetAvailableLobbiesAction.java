package rz.thesis.serialization.action.management;

import rz.server.ConnectionsRouter;
import rz.server.Subscriber;
import rz.thesis.serialization.action.reply.AvailableLobbiesReply;
import rz.thesis.serialization.action.reply.ReplyAction;

public class GetAvailableLobbiesAction extends ManagementAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4262165002016879306L;

	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		//logger.logMainServerActivity(wrapper.getUUID() + " has requested the list of available lobbies");
		ReplyAction action = new AvailableLobbiesReply(router.getAvailableLobby());		
		wrapper.sendActionToSubscriber(action);
	}

}
