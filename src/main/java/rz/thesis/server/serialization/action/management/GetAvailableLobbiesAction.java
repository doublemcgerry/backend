package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.reply.AvailableLobbiesReply;
import rz.thesis.server.serialization.action.reply.ReplyAction;

public class GetAvailableLobbiesAction extends ManagementAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4262165002016879306L;

	@Override
	public void execute(LobbiesManager router, Subscriber wrapper) {
		//logger.logMainServerActivity(wrapper.getUUID() + " has requested the list of available lobbies");
		ReplyAction action = new AvailableLobbiesReply(router.getAvailableLobby());		
		wrapper.sendAction(action);
	}

}
