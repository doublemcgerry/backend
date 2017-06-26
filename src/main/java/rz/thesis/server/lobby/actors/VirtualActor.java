package rz.thesis.server.lobby.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.Tunnel;
import rz.thesis.server.serialization.action.Action;

public class VirtualActor {
	private UUID actorSession;
	private String userName;
	private Map<String, Object> sessionInfo;
	private Tunnel tunnel;
	private LobbyActor actor;

	public VirtualActor(UUID actorSession, Tunnel tunnel) {
		this.actorSession = actorSession;
		this.tunnel = tunnel;
		this.sessionInfo = new HashMap<>();
	}

	public UUID getActorSession() {
		return actorSession;
	}

	public void sendActionToRemote(Action action) {
		action.setDeviceSession(actorSession);
		tunnel.sendAction(action);
	}

	public boolean hasLobbyActor() {
		return actor != null;
	}

	public LobbyActor getLobbyActor() {
		return actor;
	}

	public void setLobbyActor(LobbyActor actor) {
		this.actor = actor;
	}

	public boolean isAuthenticated() {
		return userName != null;
	}

	public void authenticate(String userName) {
		this.userName = userName;
	}

	public Object getSessionInfo(String key) {
		return this.sessionInfo.get(key);
	}

	public String getUserName() {
		return userName;
	}

	public Tunnel getTunnel() {
		return tunnel;
	}

}
