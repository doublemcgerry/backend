package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import rz.thesis.server.serialization.action.auth.SendCodeAction;

public class LobbiesContainer {

	private static final Logger LOGGER = Logger.getLogger(LobbiesContainer.class.getName());
	/***
	 * maps of the lobbies, the key is the username, the value is the specific
	 * server lobby
	 */
	private Map<String, ServerLobby> lobbyMap;

	/**
	 * map of the lobby actors that are currently waiting associations<br/>
	 * the key is the pairing token, the value is the actor that is associated
	 * to the device
	 */
	private Map<String, LobbyActor> waitingRoom;

	public LobbiesContainer() {
		this.lobbyMap = new HashMap<>();
		this.waitingRoom = new HashMap<>();
	}

	public void addLobbyActorToWaitingRoom(LobbyActor actor) {
		String token = getToken(4);
		synchronized (waitingRoom) {
			while (waitingRoom.containsKey(token)) {
				token = getToken(4);
			}
			waitingRoom.put(token, actor);
		}
		actor.sendAction(actor, new SendCodeAction(token));
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

	private static final Random random = new Random();
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";

	private static String getToken(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			token.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}
		return token.toString();
	}

	public Map<String, LobbyActor> getWaitingRoom() {
		return waitingRoom;
	}

}
