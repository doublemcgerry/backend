package rz.thesis.server.lobby;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rz.thesis.server.lobby.actors.LobbyDevice;
import rz.thesis.server.lobby.actors.LobbySpectator;
import rz.thesis.server.lobby.actors.UserActor;
import rz.thesis.server.serialization.action.Action;

public class ServerLobby {
	private static final Logger LOGGER = Logger.getLogger(ServerLobby.class.getName());
	private List<LobbySpectator> spectators = new ArrayList<LobbySpectator>();
	private List<LobbyDevice> sensors = new ArrayList<LobbyDevice>();
	private List<UserActor> users = new ArrayList<UserActor>();
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
	public void addActor(LobbyActor actor) {
		if (actor instanceof LobbyDevice) {
			LobbyDevice conv = (LobbyDevice) actor;
			actor.setCurrentServerInstance(this);
			addSensor(conv);
		} else if (actor instanceof UserActor) {
			UserActor conv = (UserActor) actor;
			actor.setCurrentServerInstance(this);
			addUser(conv);
		} else if (actor instanceof LobbySpectator) {
			LobbySpectator conv = (LobbySpectator) actor;
			actor.setCurrentServerInstance(this);
			addSpectator(conv);
		}
	}

	private void addSensor(LobbyDevice sensor) {
		synchronized (sensors) {
			sensors.add(sensor);
		}
	}

	private void addUser(UserActor actor) {
		synchronized (users) {
			users.add(actor);
		}

	}

	private void addSpectator(LobbySpectator actor) {
		synchronized (spectators) {
			spectators.add(actor);
		}
	}

	/**
	 * Remove subscriber from the lobby (every list of actors will be parsed)
	 * 
	 * @param wrapper
	 *            subscriber linked to the actor that must be eliminated
	 */
	public void removeSubscriber(Subscriber wrapper) {
		synchronized (spectators) {
			for (int i = spectators.size() - 1; i >= 0; i--) {
				if (spectators.get(i).isWrapper(wrapper)) {
					spectators.remove(i);
				}
			}
		}
		synchronized (users) {
			for (int i = users.size() - 1; i >= 0; i--) {
				if (users.get(i).isWrapper(wrapper)) {
					users.remove(i);
				}
			}
		}
		synchronized (sensors) {
			for (int i = sensors.size() - 1; i >= 0; i--) {
				if (sensors.get(i).isWrapper(wrapper)) {
					sensors.remove(i);
				}
			}
		}

	}

	/**
	 * broadcasts the action to every actor in the lobby
	 * 
	 * @param action
	 */
	public void broadcastAction(Action action) {
		synchronized (sensors) {
			for (int i = sensors.size() - 1; i >= 0; i--) {
				try {
					sensors.get(i).sendAction(action);
				} catch (Exception e) {
					LOGGER.error(e);
				}

			}
		}
		synchronized (users) {
			for (int i = users.size() - 1; i >= 0; i--) {
				try {
					users.get(i).sendAction(action);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		synchronized (spectators) {
			for (int i = spectators.size() - 1; i >= 0; i--) {
				try {
					spectators.get(i).sendAction(action);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		LOGGER.debug(action.toString());
	}

}
