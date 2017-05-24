package rz.thesis.websocket;

import java.util.Map;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.thesis.core.websocket.RZWebSocket;
import rz.thesis.core.websocket.RZWebsocketsManager;
import rz.thesis.core.websocket.WebSocketAbstractFactory;
import rz.thesis.modules.ServerModule;

public class WebSocketFactory extends WebSocketAbstractFactory {

	private ServerModule serverModule;

	public WebSocketFactory(ServerModule serverModule) {
		this.serverModule = serverModule;
	}

	@Override
	public RZWebSocket createNewSocket(RZWebsocketsManager websocketsManager, UriResource uriResource,
			Map<String, String> urlParams, IHTTPSession session) {
		WebSocket socket = new WebSocket(websocketsManager, uriResource, urlParams, session, serverModule);
		return socket;
	}

}
