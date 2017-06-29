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
	public boolean addActor(VirtualActor actor) {
		if (containsAddress(actor.getAddress())) {
			LOGGER.debug("trying to reconnect actor :" + actor.getAddress().toString() + " to lobby:" + userName);
			return reconnectActor(actor);
		} else {
			LOGGER.debug("Added actor :" + actor.getAddress().toString() + " to lobby:" + userName);
			this.actors.put(actor.getAddress(), actor);
			actor.setLobby(this);
			return true;
		}
	}

	private boolean containsAddress(UUID address) {
		return this.actors.containsKey(address);
	}

	/**
	 * reconnects the actor to the lobby
	 * 
	 * @param newactor
	 */
	public boolean reconnectActor(VirtualActor newactor) {
		if (this.actors.containsKey(newactor.getAddress())) {
			VirtualActor oldActor = this.actors.get(newactor.getAddress());
			if (oldActor.isDisconnected()) {
				oldActor.addInfoToNewActor(newactor);
				this.actors.put(newactor.getAddress(), newactor);
				LOGGER.debug("reconnected actor:" + newactor.getAddress() + " to lobby: " + userName);
				return true;
			} else {
				LOGGER.debug("actor reconnection not possible for :" + newactor.getAddress()
				        + " because old actor was not disconnected");
			}

		} else {
			LOGGER.debug("actor reconnection not possible for :" + newactor.getAddress()
			        + " no actor with that address is present");
		}
		return false;
	}

	/**
	 * removes this actor from the lobby
	 * 
	 * @param actor
	 *            actor to remove from the lobby
	 */
	public void removeActor(VirtualActor actor) {
		this.actors.remove(actor.getAddress());
		actor.setLobby(null);
	}

	/**
	 * sets the actor as disconnected
	 * 
	 * @param actor
	 *            actor to set as disconnected
	 */
	public void disconnectActor(VirtualActor actor) {
		this.actors.get(actor.getAddress()).disconnect();
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lobby: " + userName + ":\n");
		synchronized (actors) {
			for (Map.Entry<UUID, VirtualActor> vActorEntry : actors.entrySet()) {
				builder.append(vActorEntry.getValue().toString());
				builder.append("\n");
			}
		}
		return builder.toString();
	}

}
