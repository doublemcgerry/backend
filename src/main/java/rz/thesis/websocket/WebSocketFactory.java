package rz.thesis.websocket;

import java.util.Map;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.router.RouterNanoHTTPD.UriResource;

import rz.thesis.core.websocket.RZWebSocket;
import rz.thesis.core.websocket.RZWebsocketsManager;
import rz.thesis.core.websocket.WebSocketAbstractFactory;

public class WebSocketFactory extends WebSocketAbstractFactory {

	@Override
	public RZWebSocket createNewSocket(RZWebsocketsManager websocketsManager, UriResource uriResource,
			Map<String, String> urlParams, IHTTPSession session) {

		return null;
	}

}
