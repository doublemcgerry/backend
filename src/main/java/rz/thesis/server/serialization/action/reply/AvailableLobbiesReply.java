package rz.thesis.server.serialization.action.reply;

import java.util.ArrayList;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;

public class AvailableLobbiesReply extends ReplyAction{
	private static final long serialVersionUID = -1017610169916616951L;
	public ArrayList<String> availableLobbies;

	public AvailableLobbiesReply(ArrayList<String> availableLobbies) {
		this.availableLobbies=availableLobbies;
	}

	@Override
	public void execute(LobbiesManager router, Subscriber wrapper) {
	}
	
}
