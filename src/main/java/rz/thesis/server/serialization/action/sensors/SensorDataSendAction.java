package rz.thesis.server.serialization.action.sensors;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.utility.SensorData;

public class SensorDataSendAction extends SensorsAction {

	private static final long serialVersionUID = 6442731397402415499L;
	private SensorData data;
	private String sender;

	@Override
	public String toString() {
		return sender + " has sent a new SensorDataSendAction [data=" + data + "]";
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ServerLobby lobby, VirtualActor actor) {
		lobby.broadcastAction(this);
	}

}
