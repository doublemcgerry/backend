package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.RelayAction;
import rz.thesis.server.serialization.action.management.ManagementAction;

/**
 * This class provides the demux functionality for the relay, enables the
 * creation of the various devices and enables the tunneling
 * 
 * @author achelius
 *
 */
public class RelayDemux extends LobbyActor {

	private final LobbiesManagerInterface lobbiesManager;
	private final LobbyInterceptor interceptor;

	private Map<UUID, Subscriber> subscribers;

	public RelayDemux(LobbiesManagerInterface lobbyManager, Subscriber subscriber) {
		super(subscriber);
		this.subscribers = new HashMap<>();
		this.lobbiesManager = lobbyManager;
		this.interceptor = new LobbyInterceptor(this);
		subscriber.setLobbyManager(interceptor);
	}

	@Override
	public void removeFromServerInstance() {
		synchronized (subscribers) {
			for (Map.Entry<UUID, Subscriber> entry : subscribers.entrySet()) {
				entry.getValue().removeFromServerInstance();
			}
		}
		super.removeFromServerInstance();
	}

	private class DummySubscriber extends LobbyActor {

		private UUID id;

		public DummySubscriber(UUID id, Subscriber subscriber) {
			super(subscriber);
		}

		@Override
		public UUID getUUID() {
			return id;
		}

		@Override
		public void setUUID(UUID uuid) {
			this.id = uuid;
		}

		@Override
		public void handleAction(Subscriber subscriber, Action action) {
			this.subscriber.handleAction(subscriber, action);
		}

		@Override
		public void sendAction(Subscriber subscriber, Action action) {
			this.subscriber.sendAction(subscriber, new RelayAction(getUUID().toString(), action));
		}

	}

	private class LobbyInterceptor implements LobbiesManagerInterface {

		private RelayDemux demux;

		public LobbyInterceptor(RelayDemux demux) {
			super();
			this.demux = demux;
		}

		@Override
		public void handleAction(Subscriber wrapper, Action action) {
			if (action instanceof RelayAction) {
				RelayAction relayAction = (RelayAction) action;
				UUID source = UUID.fromString(relayAction.getId());
				synchronized (subscribers) {
					if (subscribers.containsKey(source)) {
						subscribers.get(source).handleAction(subscribers.get(source), relayAction.getAction());
					} else {
						DummySubscriber dsub = new DummySubscriber(source, wrapper);
						subscribers.put(source, dsub);
						lobbiesManager.handleAction(dsub, relayAction.getAction());
					}
				}
			}
		}

		@Override
		public void handleManagementAction(Subscriber wrapper, ManagementAction action) {
			action.execute(this, wrapper);
		}

		@Override
		public void broadcastToWaitingRoom(Action action) {
			lobbiesManager.broadcastToWaitingRoom(action);
		}

		@Override
		public void addRelay(RelayDemux relayDemux) {
			lobbiesManager.addRelay(relayDemux);
		}

		@Override
		public void removeRelay(RelayDemux relayDemux) {
			lobbiesManager.removeRelay(relayDemux);
		}

		@Override
		public void addActorToLobby(String lobbyName, LobbyActor actor) {
			lobbiesManager.addActorToLobby(lobbyName, actor);
		}

		@Override
		public AuthenticationInformation authenticate(Subscriber authenticator, String deviceKey) {
			return lobbiesManager.authenticate(authenticator, deviceKey);
		}

		@Override
		public String addLobbyActorToWaitingRoom(LobbyActor actor) {
			return lobbiesManager.addLobbyActorToWaitingRoom(actor);
		}

	}

}
