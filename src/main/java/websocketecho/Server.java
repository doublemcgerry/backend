package websocketecho;

import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import interfaces.AdderInterface;
import interfaces.WriterInterface;


public class Server extends WebSocketServer {
	private ConnectionsRouter _connectionsRouter;
	WriterInterface writer;

	public Server(InetSocketAddress address, WriterInterface writer, AdderInterface lobbyAdder) {
		super(address);
		this.writer = writer;
		this._connectionsRouter= new ConnectionsRouter(writer,lobbyAdder);
		this.setWebSocketFactory(new WebSocketConnectionFactory(this._connectionsRouter));
	}
	
	public ConnectionsRouter getConnectionsTracker() {
		return _connectionsRouter;
	}

	@Override
	public void run() {
		this.writer.addMainServerText("Server is Ready to Recieve");
		super.run();
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
