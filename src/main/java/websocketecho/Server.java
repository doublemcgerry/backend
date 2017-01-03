package websocketecho;

import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class Server extends WebSocketServer {
	private ConnectionsRouter _connectionsRouter;

	public Server(InetSocketAddress address) {
		super(address);
		this._connectionsRouter= new ConnectionsRouter();
		this.setWebSocketFactory(new WebSocketConnectionFactory(this._connectionsRouter));
		
	}
	
	public ConnectionsRouter getConnectionsTracker() {
		return _connectionsRouter;
	}
	

	@Override
	public void onClose(WebSocket client, int arg1, String arg2, boolean arg3) {
	}

	@Override
	public void onError(WebSocket client, Exception exception) {
	}

	@Override
	public void onMessage(WebSocket client, String message) {
	}

	@Override
	public void onOpen(WebSocket client, ClientHandshake arg1) {
	}

}
