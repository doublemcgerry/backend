package rz.thesis.server.serialization.action.sensors;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;

public class StopWatchingSensorAction extends SensorsAction {

	private static final long serialVersionUID = -6781215837363877939L;
	private long timestamp;
	private String sender;

	@Override
	public String toString() {
		return sender + "has sent a new StopWatchingSensorAction at timestamp=" + timestamp;
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ServerLobby lobby, VirtualActor actor) {
		lobby.broadcastAction(this);
	}
}
