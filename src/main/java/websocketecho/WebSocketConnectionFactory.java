package websocketecho;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.server.WebSocketServer.WebSocketServerFactory;

public class WebSocketConnectionFactory implements WebSocketServerFactory {

	private ConnectionsRouter _connectionsRouter;
	public WebSocketConnectionFactory(ConnectionsRouter connectionsRouter) {
		this._connectionsRouter=connectionsRouter;
	}

	@Override
	public WebSocketImpl createWebSocket(WebSocketAdapter a, Draft d, Socket s) {
		return new ConnectionWrapper(new ConnectionWrapperListener(_connectionsRouter,a),d,_connectionsRouter);
	}

	@Override
	public WebSocketImpl createWebSocket(WebSocketAdapter a, List<Draft> drafts, Socket s) {
		return new ConnectionWrapper(new ConnectionWrapperListener(_connectionsRouter,a),drafts,_connectionsRouter);
	}

	@Override
	public ByteChannel wrapChannel(SocketChannel channel, SelectionKey key) throws IOException {
		if( channel == null )
			return channel;
		return channel;
	}

}
