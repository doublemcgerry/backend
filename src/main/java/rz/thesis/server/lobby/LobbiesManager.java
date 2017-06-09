package rz.thesis.server.lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rz.thesis.core.Core;
import rz.thesis.core.modules.http.HttpSessionsManager;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class LobbiesManager implements LobbiesManagerInterface {

	private static final Logger LOGGER = Logger.getLogger(LobbiesManager.class.getName());

	private Core core;
	private ExperiencesModule experiencesModule;

	private List<RelayDemux> relays = new ArrayList<>();

	private LobbiesContainer container;

	public LobbiesManager(Core core) {
		this.container = new LobbiesContainer();
		this.core = core;
		this.experiencesModule = this.core.getModule(ExperiencesModule.class);
		this.core = core;
	}

	@Override
	public void handleAction(Subscriber wrapper, Action action) {
		if (action instanceof ManagementAction) {
			ManagementAction manag_action = (ManagementAction) action;
			wrapper.getLobbyManager().handleManagementAction(wrapper, manag_action);
		}
	}

	@Override
	public void handleManagementAction(Subscriber wrapper, ManagementAction action) {
		action.execute(wrapper.getLobbyManager(), wrapper);
	}

	@Override
	public AuthenticationInformation authenticate(final Subscriber authenticator, String deviceKey) {
		if (authenticator.getServerSession().isAuthenticated()) {
			String username = authenticator.getServerSession().getUsername();
			synchronized (container.getWaitingRoom()) {
				if (container.getWaitingRoom().containsKey(deviceKey)) {
					final LobbyActor actor = container.getWaitingRoom().get(deviceKey);
					HttpSessionsManager.authenticateSession(actor.getServerSession(), username);
					LobbyActor authenticatedActor = container.getWaitingRoom().remove(deviceKey);

					return new AuthenticationInformation(username, deviceKey, authenticatedActor, authenticator);
				}
			}

		} else {
			throw new RuntimeException("no authentication, no party");
		}
		return null;

	}

	@Override
	public void broadcastToWaitingRoom(Action action) {
		for (Map.Entry<String, LobbyActor> subscriber : container.getWaitingRoom().entrySet()) {
			try {
				subscriber.getValue().sendAction(subscriber.getValue(), action);
			} catch (Exception e) {
				LOGGER.debug("Error while sending to subscriber:" + e.getMessage());
			}
		}
	}

	@Override
	public void addRelay(RelayDemux relayDemux) {
		synchronized (relays) {
			relays.add(relayDemux);
		}
	}

	@Override
	public void removeRelay(RelayDemux relayDemux) {
		synchronized (relays) {
			relays.remove(relayDemux);
		}
	}

	protected LobbiesContainer getContainer() {
		return this.container;
	}

	@Override
	public void addActorToLobby(String lobbyName, LobbyActor actor) {
		this.container.addActorToLobby(lobbyName, actor);
	}

	@Override
	public String addLobbyActorToWaitingRoom(LobbyActor actor) {
		return this.container.addLobbyActorToWaitingRoom(actor);
	}

}
