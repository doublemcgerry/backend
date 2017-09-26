package rz.thesis.server.lobby.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import rz.thesis.server.lobby.LobbyActor;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.Tunnel;
import rz.thesis.server.serialization.action.Action;

public class VirtualActor {

	private static final Logger LOGGER = Logger.getLogger(VirtualActor.class.getName());
	private UUID address;
	private String userName;
	private Map<String, Object> sessionInfo;
	private Tunnel tunnel;
	private LobbyActor actor;
	private ServerLobby lobby;

	public VirtualActor(UUID actorSession, Tunnel tunnel) {
		this.address = actorSession;
		this.tunnel = tunnel;
		this.sessionInfo = new HashMap<>();
	}

	public UUID getAddress() {
		return address;
	}

	public void sendActionToRemote(Action action) {
		action.setDestination(address);
		if (tunnel != null) {
			tunnel.sendAction(action);
			LOGGER.debug("<--- sent action:" + action.toString() + " to actor " + address.toString());
		} else {
			LOGGER.debug(
					"ERROR : sending action to closed tunnel:" + action.toString() + " to actor " + address.toString());
		}

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

	public ServerLobby getLobby() {
		return lobby;
	}

	public void setLobby(ServerLobby lobby) {
		this.lobby = lobby;
	}

	public void disconnect() {
		this.tunnel = null;
	}

	public boolean isDisconnected() {
		return this.tunnel == null;
	}

	/**
	 * sets the info from this actor to the provided new actor
	 * 
	 * @param newActor
	 */
	public void addInfoToNewActor(VirtualActor newActor) {
		newActor.userName = userName;
		newActor.sessionInfo = sessionInfo;
		newActor.actor = actor;
		newActor.lobby = lobby;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " address:" + this.address.toString() + " connected:"
				+ !this.isDisconnected() + (actor == null ? "" : (" type:" + actor.toString()));
	}

	public boolean canStartExperience() {
		if (actor == null) {
			return false;
		} else {
			return actor.canStartExperience();
		}

	}

	public boolean canStopExperience() {
		if (actor == null) {
			return false;
		} else {
			return actor.canStopExperience();
		}
	}

}
