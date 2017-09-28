package rz.thesis.server.serialization.action.lobby;

import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.utility.Quaternion;

public class CameraUpdateAction extends LobbyAction {

	private Quaternion cameraRotation;
	private Quaternion deltaRotation;
	private int deltaTime;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
			VirtualActor actor) {
		// this.deltaRotation = Vector3.DivideByFloat(deltaRotation, this.deltaTime);
		lobby.broadcastAction(this);
	}

}
