package rz.thesis.server.serialization.action.reply;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.Action;

public abstract class ReplyAction extends Action{
	
	private static final long serialVersionUID = 2460209641896946010L;
	public abstract void execute(LobbiesManager router, Subscriber wrapper);
}
