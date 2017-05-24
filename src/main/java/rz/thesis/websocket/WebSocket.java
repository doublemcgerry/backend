package rz.thesis.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.websockets.WebSocketFrame;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.server.ConnectionsRouter;
import rz.server.ServerInstance;
import rz.server.Subscriber;
import rz.thesis.core.websocket.RZWebSocket;
import rz.thesis.core.websocket.RZWebsocketsManager;
import rz.thesis.modules.ServerModule;
import rz.thesis.serialization.action.Action;
import rz.thesis.utility.StringSerializer;

public class WebSocket extends RZWebSocket implements Subscriber {

	private ServerInstance instance;
	private static final Logger LOGGER = Logger.getLogger(WebSocket.class.getName());
	private UUID uuid;
	private String name;
	private ConnectionsRouter router;

	public WebSocket(RZWebsocketsManager manager, UriResource uriResource, Map<String, String> urlParams,
			IHTTPSession session, ServerModule serverModule) {
		super(manager, uriResource, urlParams, session);
		uuid = UUID.randomUUID();
		router = serverModule.getRouter();
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
	public void setCurrentServerInstance(ServerInstance serverInstance) {
		this.instance = serverInstance;
	}

	@Override
	public void removeFromServerInstance() {
		if (instance != null) {
			this.instance.removeSubscriber(this);
		} else {
			throw new RuntimeException("The instance is null");
		}
	}

	@Override
	public ServerInstance getCurrentServerInstance() {
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
	public String getName() {
		return "".equals(name) ? name : "Para Culo";
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void onMessage(WebSocketFrame arg0) {
		Action action = StringSerializer.getSerializer().fromJson(arg0.getTextPayload(), Action.class);
		router.handleAction(this, action);
	}

}
