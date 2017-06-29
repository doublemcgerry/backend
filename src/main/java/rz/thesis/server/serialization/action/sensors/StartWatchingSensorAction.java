package rz.thesis.server.serialization.action.sensors;

import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;

public class StartWatchingSensorAction extends SensorsAction {

	private static final long serialVersionUID = 815596656975368143L;
	private long timestamp;
	private String sender;

	@Override
	public String toString() {
		return sender + "has sent a new StartWatchingSensorAction at timestamp=" + timestamp;
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule expModule, ServerLobby lobby,
	        VirtualActor actor) {
		lobby.broadcastAction(this);
	}

}
