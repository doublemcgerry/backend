package rz.thesis.server.websocket;

import java.io.IOException;
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
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.modules.ServerModule;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.utility.StringSerializer;

public class WebSocket extends RZWebSocket implements Subscriber {

	private ServerLobby instance;
	private static final Logger LOGGER = Logger.getLogger(WebSocket.class.getName());
	private UUID uuid;
	private LobbiesManagerInterface lobbyManager;
	private HttpSessionsManager sessionsManager;
	private HttpServerSession serverSession;

	public WebSocket(RZWebsocketsManager manager, UriResource uriResource, Map<String, String> urlParams,
			IHTTPSession session, HttpServerSession serverSession, ServerModule serverModule) {
		super(manager, uriResource, urlParams, session);
		this.sessionsManager = uriResource.initParameter(1, HttpSessionsManager.class);
		this.lobbyManager = serverModule.getRouter();
		this.serverSession = serverSession;
	}

	@Override
	protected void onOpen() {
		super.onOpen();
		this.lobbyManager.onSubscriberCreated(this);
	}

	@Override
	protected void onClose(CloseCode code, String reason, boolean initiatedByRemote) {
		super.onClose(code, reason, initiatedByRemote);
		removeFromServerInstance();
		this.lobbyManager.onSubscriberClosed(this);
	}

	@Override
	public void sendAction(Subscriber subscriber, Action action) {
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
	public void setCurrentServerInstance(ServerLobby serverInstance) {
		this.instance = serverInstance;
	}

	@Override
	public void removeFromServerInstance() {
		if (instance != null) {
			this.instance.removeSubscriber(this);
		}
	}

	@Override
	public ServerLobby getCurrentServerInstance() {
		return this.instance;
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
		this.handleAction(this, action);
	}

	@Override
	public void handleAction(Subscriber subscriber, Action action) {
		lobbyManager.handleAction(this, action);
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

}
