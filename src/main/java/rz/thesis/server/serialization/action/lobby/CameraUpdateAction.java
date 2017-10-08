package rz.thesis.server.serialization.action.lobby;

import java.util.List;

import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.utility.Quaternion;

public class CameraUpdateAction extends LobbyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6296841742192781098L;
	private Quaternion cameraRotation;
	private Quaternion deltaRotation;
	private int deltaTime;
	private List<Quaternion> lastValues;
	private List<Integer> lastDeltas;

	public CameraUpdateAction() {
		this.debuggable = false;
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
			VirtualActor actor) {
		// this.deltaRotation = Vector3.DivideByFloat(deltaRotation, this.deltaTime);
		lobby.broadcastAction(this);
	}

	public Quaternion getCameraRotation() {
		return cameraRotation;
	}

	public Quaternion getDeltaRotation() {
		return deltaRotation;
	}

	public int getDeltaTime() {
		return deltaTime;
	}

	public List<Quaternion> getLastValues() {
		return lastValues;
	}

	public List<Integer> getLastDeltas() {
		return lastDeltas;
	}

}
