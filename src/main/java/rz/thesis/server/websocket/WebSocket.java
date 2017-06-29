package rz.thesis.server.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.websockets.CloseCode;
import org.nanohttpd.protocols.websockets.WebSocketFrame;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.core.modules.http.HttpSessionsManager;
import rz.thesis.core.websocket.RZWebSocket;
import rz.thesis.core.websocket.RZWebsocketsManager;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Tunnel;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.modules.ServerModule;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.utility.StringSerializer;

public class WebSocket extends RZWebSocket implements Tunnel {

	private static final Logger LOGGER = Logger.getLogger(WebSocket.class.getName());
	private UUID uuid;
	private LobbiesManagerInterface lobbyManager;
	private HttpSessionsManager sessionsManager;
	private HttpServerSession serverSession;
	private Map<UUID, VirtualActor> virtualActors;

	public WebSocket(RZWebsocketsManager manager, UriResource uriResource, Map<String, String> urlParams,
			IHTTPSession session, HttpServerSession serverSession, ServerModule serverModule) {
		super(manager, uriResource, urlParams, session);
		this.virtualActors = new HashMap<>();
		this.sessionsManager = uriResource.initParameter(1, HttpSessionsManager.class);
		this.lobbyManager = serverModule.getRouter();
		this.serverSession = serverSession;
	}

	@Override
	protected void onClose(CloseCode code, String reason, boolean initiatedByRemote) {
		super.onClose(code, reason, initiatedByRemote);
		for (Map.Entry<UUID, VirtualActor> virtualActor : virtualActors.entrySet()) {
			if (virtualActor.getValue().hasLobbyActor()) {
				virtualActor.getValue().getLobby().disconnectActor(virtualActor.getValue());
			} else {
				lobbyManager.getAuthenticator().removeFromWaitingRoom(virtualActor.getValue());
			}
		}
		this.virtualActors.clear();
	}

	@Override
	public void sendAction(Action action) {
		String serialized = StringSerializer.getSerializer().toJson(action, Action.class);
		try {
			super.send(serialized);
		} catch (IOException e) {
			LOGGER.debug(e.getMessage());
		}
	}

	@Override
	public boolean isAlive() {
		return this.isOpen();
	}

	@Override
	public UUID getUUID() {
		return this.uuid;
	}

	@Override
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	protected void onMessage(WebSocketFrame arg0) {
		Action action = StringSerializer.getSerializer().fromJson(arg0.getTextPayload(), Action.class);
		VirtualActor actor; // todo uniqueness of the source
		if (!containsActor(action.getSource())) {
			actor = new VirtualActor(action.getSource(), this);
			this.addActor(actor);
		} else {
			actor = getActorFromActorSession(action.getSource());
		}
		this.handleAction(actor, action);
	}

	@Override
	public void handleAction(VirtualActor actor, Action action) {
		lobbyManager.handleAction(actor, action);
	}

	@Override
	public HttpSessionsManager getSessionsManager() {
		return this.sessionsManager;
	}

	@Override
	public HttpServerSession getServerSession() {
		return this.serverSession;
	}

	@Override
	public void setLobbyManager(LobbiesManagerInterface lobbyManagerInterface) {
		this.lobbyManager = lobbyManagerInterface;
	}

	@Override
	public LobbiesManagerInterface getLobbyManager() {
		return this.lobbyManager;
	}

	@Override
	public void addActor(VirtualActor actor) {
		this.virtualActors.put(actor.getAddress(), actor);
	}

	@Override
	public void removeActor(VirtualActor actor) {
		this.removeActor(actor.getAddress());
	}

	@Override
	public void removeActor(UUID deviceSession) {
		this.virtualActors.remove(deviceSession);
	}

	@Override
	public Map<UUID, VirtualActor> getActors() {
		return this.virtualActors;
	}

	@Override
	public void sendAction(UUID source, UUID destination, Action action) {
		action.setSource(source);
		action.setDestination(destination);
		this.sendAction(action);
	}

	@Override
	public VirtualActor getActorFromActorSession(UUID actorSession) {
		for (Map.Entry<UUID, VirtualActor> lobbyActor : virtualActors.entrySet()) {
			if (actorSession.equals(lobbyActor.getValue().getAddress())) {
				return lobbyActor.getValue();
			}
		}
		throw new RuntimeException("no actor found for this session id");
	}

	@Override
	public boolean containsActor(UUID actorSession) {
		for (Map.Entry<UUID, VirtualActor> lobbyActor : virtualActors.entrySet()) {
			if (actorSession.equals(lobbyActor.getValue().getAddress())) {
				return true;
			}
		}
		return false;
	}

}
