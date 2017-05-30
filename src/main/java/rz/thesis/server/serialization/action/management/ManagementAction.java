package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.Action;

public abstract class ManagementAction extends Action{
	private static final long serialVersionUID = 3281620540341135676L;
	public abstract void execute(LobbiesManager router, Subscriber wrapper);
}