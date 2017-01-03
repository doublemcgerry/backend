package serialization.action.reply;

import java.util.ArrayList;

import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;

public class AvailableLobbiesReply extends ReplyAction{
	private static final long serialVersionUID = -1017610169916616951L;
	public ArrayList<String> availableLobbies;

	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		this.availableLobbies = router.getAvailableLobby();
		wrapper.sendActionToSubscriber(this);
	}
	
}
