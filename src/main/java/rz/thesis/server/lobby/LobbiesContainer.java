package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import rz.thesis.server.lobby.actors.VirtualActor;

public class LobbiesContainer {

	private static final Logger LOGGER = Logger.getLogger(LobbiesContainer.class.getName());
	/***
	 * maps of the lobbies, the key is the username, the value is the specific
	 * server lobby
	 */
	private Map<String, ServerLobby> lobbyMap;

	public LobbiesContainer() {
		this.lobbyMap = new HashMap<>();
	}

	/**
	 * create the lobby with the specified name
	 * 
	 * @param lobbyName
	 *            name of the lobby to create
	 */
	public void createLobby(String lobbyName) {
		LOGGER.debug(lobbyName + " has requested to create a Lobby with name " + lobbyName);
		ServerLobby gameinstance = new ServerLobby(lobbyName);
		synchronized (lobbyMap) {
			if (!lobbyMap.containsKey(lobbyName)) {
				lobbyMap.put(lobbyName, gameinstance);
			}
		}
	}

	/**
	 * adds the actor to the lobby, eventually creating the lobby if not present
	 * 
	 * @param lobbyName
	 *            name of the lobby to insert the actor into
	 * @param actor
	 *            actor to insert into the lobby
	 */
	public boolean addActorToLobby(String lobbyName, VirtualActor actor) {
		synchronized (lobbyMap) {
			if (!this.lobbyMap.containsKey(lobbyName)) {
				this.createLobby(lobbyName);
			}
			return this.lobbyMap.get(lobbyName).addActor(actor);
		}
	}

	/**
	 * removes the actor from the specified lobby
	 * 
	 * @param lobbyName
	 *            name of the lobby where to remove the actor
	 * @param actor
	 *            actor to remove from the lobby
	 */
	public void removeActor(String lobbyName, VirtualActor actor) {
		synchronized (lobbyMap) {
			if (this.lobbyMap.containsKey(lobbyName)) {
				this.lobbyMap.get(lobbyName).removeActor(actor);
			}
		}
	}

	/**
	 * searches the list of lobbies for the provided lobby name
	 * 
	 * @param lobby
	 *            name of the lobby to search
	 * @return true if a lobby with the name provided is present, false
	 *         otherwise
	 */
	public boolean containsLobby(String lobby) {
		synchronized (lobbyMap) {
			return this.lobbyMap.containsKey(lobby);
		}
	}

	public String getLobbyStatus() {
		StringBuilder builder = new StringBuilder();
		builder.append("lobbies: (count:" + this.lobbyMap.size() + "):\n");
		synchronized (lobbyMap) {
			for (Map.Entry<String, ServerLobby> lobbyEntry : this.lobbyMap.entrySet()) {
				builder.append(lobbyEntry.getValue().toString());
				builder.append("\n");
			}
		}
		return builder.toString();
	}

}
