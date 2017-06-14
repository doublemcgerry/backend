package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

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

	public void createLobby(Subscriber userActor) {
		if (userActor.getServerSession().isAuthenticated()) {
			String username = userActor.getServerSession().getUsername();
			LOGGER.debug(username + " has requested to create a Lobby with name " + username);

			ServerLobby gameinstance = new ServerLobby(username);
			synchronized (lobbyMap) {
				if (!lobbyMap.containsKey(username)) {
					lobbyMap.put(username, new ServerLobby(username));
				}
			}
		} else {
			throw new RuntimeException(
					"Rogue actor tried to screw us by creating a lobby without authentication first");
		}

	}

	public void addActorToLobby(ServerLobby lobby, LobbyActor actor) {
		lobby.addActor(actor);
	}

	public void addActorToLobby(String lobbyName, LobbyActor actor) {
		synchronized (lobbyMap) {
			if (!this.lobbyMap.containsKey(lobbyName)) {
				this.lobbyMap.put(lobbyName, new ServerLobby(lobbyName));
			}
			this.lobbyMap.get(lobbyName).addActor(actor);
		}
	}

}
