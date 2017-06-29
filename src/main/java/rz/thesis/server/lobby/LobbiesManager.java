package rz.thesis.server.lobby;

import org.apache.log4j.Logger;

import rz.thesis.core.Core;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.lobby.LobbyAction;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class LobbiesManager implements LobbiesManagerInterface {

	private static final Logger LOGGER = Logger.getLogger(LobbiesManager.class.getName());

	private Core core;
	private ExperiencesModule experiencesModule;

	private LobbiesContainer container;

	private LobbiesAuthenticator authenticator;

	public LobbiesManager(Core core) {
		this.container = new LobbiesContainer();
		this.core = core;
		this.experiencesModule = this.core.getModule(ExperiencesModule.class);
		this.core = core;
		this.authenticator = new LobbiesAuthenticator();
	}

	@Override
	public void handleAction(VirtualActor actor, Action action) {
		LOGGER.debug("--->received action:" + action.toString() + " from actor " + actor.getAddress().toString());
		if (action instanceof ManagementAction) {
			ManagementAction manag_action = (ManagementAction) action;
			handleManagementAction(actor, manag_action);
		}
		if (action instanceof LobbyAction) {
			LobbyAction lobbyAction = (LobbyAction) action;
			if (actor.hasLobbyActor()) {
				lobbyAction.execute(this, actor.getLobby(), actor);
			}
		}
	}

	protected LobbiesContainer getContainer() {
		return this.container;
	}

	@Override
	public LobbiesAuthenticationInterface getAuthenticator() {
		return this.authenticator;
	}

	@Override
	public void handleManagementAction(VirtualActor actor, ManagementAction action) {
		action.execute(this, actor);
	}

	@Override
	public void broadcastToWaitingRoom(Action action) {
		this.authenticator.broadcastToWaitingRoom(action);
	}

	@Override
	public void addActorToLobby(String lobbyName, VirtualActor actor) {
		this.container.addActorToLobby(lobbyName, actor);
	}

	@Override
	public boolean reconnectToLobby(String lobby, VirtualActor actor) {
		if (this.container.containsLobby(lobby)) {
			this.container.addActorToLobby(lobby, actor);
			return true;
		}
		return false;
	}

	@Override
	public String getLobbiesStatus() {
		return this.container.getLobbyStatus();
	}
}
