package serialization.action.game;

import serialization.action.Action;
import websocketecho.ServerController;
import websocketecho.Subscriber;

public abstract class GameAction extends Action{
	private static final long serialVersionUID = 7658291109761206269L;
	public abstract void execute(ServerController serverController, Subscriber subscriber);
}
