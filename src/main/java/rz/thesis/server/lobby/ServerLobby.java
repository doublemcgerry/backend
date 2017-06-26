package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.lobby.LobbyAction;

public class ServerLobby {
	private static final Logger LOGGER = Logger.getLogger(ServerLobby.class.getName());
	private Map<UUID, VirtualActor> actors = new HashMap<>();
	private String userName;

	public ServerLobby(String userName) {
		this.userName = userName;
	}

	/**
	 * adds the actor to the specific list depending on the base class
	 * 
	 * @param actor
	 *            actor to add to the lobby
	 */
	public void addActor(VirtualActor actor) {
		this.actors.put(actor.getActorSession(), actor);
	}

	/**
	 * removes this actor from the lobby
	 * 
	 * @param actor
	 *            actor to remove from the lobby
	 */
	public void removeActor(VirtualActor actor) {
		this.actors.remove(actor.getActorSession());
	}

	/**
	 * sends the action to the specific client
	 * 
	 * @param destination
	 *            client id to send the action to
	 * @param action
	 *            action to send to the client
	 */
	public void sendActionToSpecificClient(UUID destination, LobbyAction action) {
		if (this.actors.containsKey(destination)) {
			VirtualActor destinationActor = this.actors.get(destination);
			destinationActor.sendActionToRemote(action);
		}
	}

	/**
	 * broadcasts the action to every actor in the lobby
	 * 
	 * @param action
	 */
	public void broadcastAction(LobbyAction lobbyAction) {
		synchronized (actors) {
			for (Map.Entry<UUID, VirtualActor> vActorEntry : actors.entrySet()) {
				vActorEntry.getValue().sendActionToRemote(lobbyAction);
			}
		}
		LOGGER.debug(lobbyAction.toString());
	}

}
