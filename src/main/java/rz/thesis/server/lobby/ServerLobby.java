package rz.thesis.server.lobby;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import rz.thesis.modules.experience.Experience;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.sensors.SensorType;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.lobby.ConnectedDeviceEvent;
import rz.thesis.server.serialization.action.lobby.DeviceDefinition;
import rz.thesis.server.serialization.action.lobby.DisconnectedDeviceEvent;
import rz.thesis.server.serialization.action.lobby.LobbyAction;
import rz.thesis.server.serialization.action.lobby.LobbyEvent;
import rz.thesis.server.serialization.action.lobby.LobbyStateChanged;
import rz.thesis.server.serialization.action.lobby.LobbyStatusCommunication;
import rz.thesis.server.serialization.action.lobby.experience.BindSlotConfirmationEvent;
import rz.thesis.server.serialization.action.lobby.experience.ExperienceEndedEvent;
import rz.thesis.server.serialization.action.lobby.experience.ExperienceStartedEvent;
import rz.thesis.server.serialization.action.lobby.experience.ExperienceStatusChangeEvent;
import rz.thesis.server.serialization.action.lobby.experience.SelectedExperienceEvent;

public class ServerLobby {
	public enum LobbyState {
		NO_EXPERIENCE(0), EXPERIENCE_SELECTED(1), READY_TO_START(2), EXPERIENCE_STARTED(3), EXPERIENCE_ENDED(4);
		private int index;

		LobbyState(int index) {
			this.index = index;
		}

		/**
		 * returns true if the state is before the given one (exclusive)
		 * 
		 * @param state
		 * @return
		 */
		public boolean isBefore(LobbyState state) {
			return this.index < state.index;
		}

		/**
		 * returns true if the state is before the given one (inclusive)
		 * 
		 * @param state
		 * @return
		 */
		public boolean isBeforeI(LobbyState state) {
			return this.index <= state.index;
		}

		/**
		 * returns true if the state is after the given one (exclusive)
		 * 
		 * @param state
		 * @return
		 */
		public boolean isAfter(LobbyState state) {
			return this.index > state.index;
		}

		/**
		 * returns true if the state is after the given one (inclusive)
		 * 
		 * @param state
		 * @return
		 */
		public boolean isAfterI(LobbyState state) {
			return this.index >= state.index;
		}

		/**
		 * returns true if the state is between start and end (exclusive)
		 * 
		 * @param start
		 * @param end
		 * @return
		 */
		public boolean isBetween(LobbyState start, LobbyState end) {
			return this.isAfter(start) && this.isBefore(end);
		}

		/**
		 * returns true if the state is between start and end (inclusive)
		 * 
		 * @param start
		 * @param end
		 * @return
		 */
		public boolean isBetweenI(LobbyState start, LobbyState end) {
			return this.isAfterI(start) && this.isBeforeI(end);
		}

	}

	private static final Logger LOGGER = Logger.getLogger(ServerLobby.class.getName());
	private Map<UUID, VirtualActor> actors = new HashMap<>();
	private String userName;
	private Experience currentExperience;
	private ExperienceDevicesStatus devicesStatus;
	private LobbyState lobbyState;

	public ServerLobby(String userName) {
		this.userName = userName;
		this.lobbyState = LobbyState.NO_EXPERIENCE;
	}

	/**
	 * adds the actor to the specific list depending on the base class (during any
	 * state after {@link LobbyState#READY_TO_START}, no actor can be added)
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
			synchronized (actors) {
				this.actors.put(actor.getAddress(), actor);
			}
			actor.setLobby(this);
			LobbyActor lobbyActor = actor.getLobbyActor();
			// send only to the newly connected, the list of members in the lobby
			actor.sendActionToRemote(
					new LobbyStatusCommunication(this.lobbyState, getDevicesDefinitionsList(), this.userName));
			if (!(this.currentExperience == null)) {
				actor.sendActionToRemote(new SelectedExperienceEvent(currentExperience, devicesStatus));
			}
			// broadcast the connected event to the lobby
			this.broadcastEvent(new ConnectedDeviceEvent(actor.getUserName(), lobbyActor));
			return true;
		}
	}

	/**
	 * generates a list of device definitions from the current list of actors in the
	 * lobby
	 * 
	 * @return
	 */
	private List<DeviceDefinition> getDevicesDefinitionsList() {
		List<DeviceDefinition> definitions = new ArrayList<>();
		synchronized (this.actors) {
			for (Map.Entry<UUID, VirtualActor> actorsEntry : this.actors.entrySet()) {
				LobbyActor actor = actorsEntry.getValue().getLobbyActor();
				definitions.add(new DeviceDefinition(actor.getName(), actor.getAddress(), actor.getActorType(),
						actor.getSupportedSensors()));
			}
		}
		return definitions;

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
		if (this.lobbyState.isAfterI(LobbyState.READY_TO_START)) {
			return false;
		}
		if (this.actors.containsKey(newactor.getAddress())) {
			VirtualActor oldActor = this.actors.get(newactor.getAddress());
			if (oldActor.isDisconnected()) {
				oldActor.addInfoToNewActor(newactor);
				synchronized (actors) {
					this.actors.put(newactor.getAddress(), newactor);
				}
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
		synchronized (actors) {
			this.actors.remove(actor.getAddress());
		}
		this.broadcastEvent(new DisconnectedDeviceEvent(this.userName, actor.getLobbyActor()));
		actor.setLobby(null);
	}

	/**
	 * sets the actor as disconnected
	 * 
	 * @param actor
	 *            actor to set as disconnected
	 */
	public void disconnectActor(VirtualActor actor) {
		this.removeActor(actor);
		if (this.lobbyState.isAfterI(LobbyState.EXPERIENCE_SELECTED)) {
			LobbyActor lobbyActor = actor.getLobbyActor();
			if (lobbyActor != null) {
				this.devicesStatus.removeDevice(new DeviceDefinition(lobbyActor.getName(), lobbyActor.getAddress(),
						lobbyActor.getActorType(), lobbyActor.getSupportedSensors()));
				this.broadcastEvent(new ExperienceStatusChangeEvent(devicesStatus));
			}
		}

	}

	/**
	 * sends the action to the specific client
	 * 
	 * @param destination
	 *            client id to send the action to
	 * @param action
	 *            action to send to the client
	 */
	public void sendActionToSpecificClient(final UUID destination, final Action action) {
		synchronized (actors) {
			if (this.actors.containsKey(destination)) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						VirtualActor destinationActor = actors.get(destination);
						destinationActor.sendActionToRemote(action);
					}
				}).start();

			}
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
		if (lobbyAction.isDebuggable()) {
			LOGGER.debug(lobbyAction.toString());
		}
	}

	/**
	 * broadcasts the action to every actor in the lobby
	 * 
	 * @param action
	 */
	public void broadcastEvent(LobbyEvent lobbyEvent) {
		synchronized (actors) {
			for (Map.Entry<UUID, VirtualActor> vActorEntry : actors.entrySet()) {
				vActorEntry.getValue().sendActionToRemote(lobbyEvent);
			}
		}
		LOGGER.debug(lobbyEvent.toString());
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
		builder.append("\n");
		if (this.devicesStatus != null) {
			builder.append("experience status\n");
			builder.append(this.devicesStatus.toString());
		}
		return builder.toString();
	}

	public ExperienceDevicesStatus initializeExperience(Experience experience) {

		this.currentExperience = experience;
		try {
			this.devicesStatus = new ExperienceDevicesStatus(experience.getParameters());
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
		}
		this.setLobbyState(LobbyState.EXPERIENCE_SELECTED);
		return this.devicesStatus;
	}

	/**
	 * returns true if the experience is selected but the sensors slots are not
	 * completely full
	 * 
	 * @return
	 */
	public boolean isExperienceInitiating() {
		return this.lobbyState == LobbyState.EXPERIENCE_SELECTED;
	}

	/**
	 * returns true if the lobby has an experience currently associated
	 * 
	 * @return
	 */
	public boolean isExperienceSelected() {
		return this.lobbyState == LobbyState.EXPERIENCE_SELECTED;
	}

	/**
	 * returns true is the experience is currently running
	 * 
	 * @return
	 */
	public boolean isExperienceRunning() {
		return this.lobbyState == LobbyState.EXPERIENCE_STARTED;
	}

	/**
	 * returns true is the experience is currently running
	 * 
	 * @return
	 */
	public boolean isExperienceReadyToStart() {
		return this.lobbyState == LobbyState.READY_TO_START;
	}

	public ExperienceDevicesStatus getDeviceStatus() {
		return this.devicesStatus;
	}

	public void setLobbyState(LobbyState state) {
		if (this.lobbyState != state) {
			this.lobbyState = state;
			this.broadcastEvent(new LobbyStateChanged(this.lobbyState));
		}

	}

	/**
	 * if the lobby is in the {@link LobbyState#READY_TO_START}, communicates to
	 * everyone to start the experience and move the state into
	 * {@link LobbyState#EXPERIENCE_STARTED}
	 */
	public void startExperience() {
		if (this.lobbyState.isBefore(LobbyState.READY_TO_START)) {
			LOGGER.error("Lobby:" + this.userName + " has been asked to start before entering "
					+ LobbyState.READY_TO_START.toString());
		} else {
			LOGGER.debug(
					"Lobby:" + this.userName + " is starting the experience " + currentExperience.getId().toString());
			this.setLobbyState(LobbyState.EXPERIENCE_STARTED);
			this.broadcastEvent(new ExperienceStartedEvent(currentExperience, this.devicesStatus));
		}
	}

	/**
	 * Forcefully stops the experience moving into the
	 * {@link LobbyState#EXPERIENCE_ENDED}
	 */
	public void interruptExperience() {
		if (this.lobbyState.isBefore(LobbyState.EXPERIENCE_STARTED)) {
			LOGGER.error("Lobby:" + this.userName + " has been asked to stop before entering "
					+ LobbyState.EXPERIENCE_STARTED.toString());
		} else {
			LOGGER.debug(
					"Lobby:" + this.userName + " INTERRUPTED the experience " + currentExperience.getId().toString());
			this.setLobbyState(LobbyState.EXPERIENCE_ENDED);
			this.setLobbyState(LobbyState.NO_EXPERIENCE);
			this.currentExperience = null;
			this.devicesStatus = null;
			this.broadcastEvent(new ExperienceEndedEvent());
		}
	}

	/**
	 * Gracefully stops the experience moving into the
	 * {@link LobbyState#EXPERIENCE_ENDED}
	 */
	public void finishExperience() {
		if (this.lobbyState.isBefore(LobbyState.EXPERIENCE_STARTED)) {
			LOGGER.error("Lobby:" + this.userName + " has been asked to finish before entering "
					+ LobbyState.EXPERIENCE_STARTED.toString());
		} else {
			LOGGER.debug("Lobby:" + this.userName + " finished the experience " + currentExperience.getId().toString());
			this.setLobbyState(LobbyState.EXPERIENCE_ENDED);
			this.setLobbyState(LobbyState.NO_EXPERIENCE);
			this.currentExperience = null;
			this.devicesStatus = null;
			this.broadcastEvent(new ExperienceEndedEvent());
		}
	}

	/**
	 * Checks if the sensors of that type are needed
	 * 
	 * @param type
	 * @return
	 */
	public boolean canBindExperienceSensor(SensorType type) {
		return this.devicesStatus.canAddSensor(type);
	}

	/**
	 * adds the address of the device as sensor for the given type and broadcasts an
	 * {@link ExperienceStatusChangeEvent} event
	 * 
	 * @param type
	 *            type of sensor to bind the device to
	 * @param address
	 *            address of the sensor
	 */
	public void bindExperienceSensor(SensorType type, UUID address) {
		synchronized (actors) {
			if (!this.actors.containsKey(address)) {
				LOGGER.error("Lobby:" + this.userName + " Trying to bind sensor slot to inexistent address ");
				return;
			}
		}
		this.devicesStatus.addSensor(type, address);
		ExperienceStatusChangeEvent changeEvent = new ExperienceStatusChangeEvent(this.devicesStatus);
		broadcastEvent(changeEvent);
		LobbyActor actor = this.actors.get(address).getLobbyActor();
		DeviceDefinition def = new DeviceDefinition(actor.getName(), actor.getAddress(), actor.getActorType(),
				actor.getSupportedSensors());
		broadcastEvent(new BindSlotConfirmationEvent(type, def, this.currentExperience));
		if (this.devicesStatus.calculateReadysness()) {
			this.setLobbyState(LobbyState.READY_TO_START);
		}
	}

}
