package websocketecho;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

import serialization.StringSerializer;
import serialization.action.Action;
import serialization.action.management.ManagementAction;
import serialization.action.sensors.SensorsAction;



public class ConnectionWrapperListener implements WebSocketListener {

	private ConnectionsRouter _connectionsRouter;
	private WebSocketAdapter callback;

	public ConnectionWrapperListener( ConnectionsRouter connectionsRouter,
			WebSocketAdapter callback) {
		this._connectionsRouter = connectionsRouter;
		this.callback=callback;

	}

	@Override
	public void onWebsocketMessage(WebSocket conn, String message) {
		Action action = StringSerializer.getSerializer().fromJson(message, Action.class);
		ConnectionWrapper wrapper = (ConnectionWrapper) conn;
		if (action instanceof SensorsAction) {
			((SensorsAction) action).execute(wrapper.getCurrentServerInstance());
		} else if (action instanceof ManagementAction) {
			_connectionsRouter.handleManagementAction((ConnectionWrapper) conn, (ManagementAction) action);
		}
		callback.onWebsocketMessage(conn, message);
	}

	@Override
	public void onWebsocketMessage(WebSocket conn, ByteBuffer blob) {
		callback.onWebsocketMessage(conn, blob);
	}

	@Override
	public void onWebsocketOpen(WebSocket conn, Handshakedata d) {
		 callback.onWebsocketOpen(conn, d);
	}

	@Override
	public void onWebsocketClose(WebSocket ws, int code, String reason, boolean remote) {
		callback.onWebsocketClose(ws, code, reason, remote);
	}

	@Override
	public void onWebsocketClosing(WebSocket ws, int code, String reason, boolean remote) {
		ConnectionWrapper wrapper = (ConnectionWrapper) ws;
		wrapper.removeFromServerInstance();
		callback.onWebsocketClosing(ws, code, reason, remote);
	}

	@Override
	public void onWebsocketCloseInitiated(WebSocket ws, int code, String reason) {

		 callback.onWebsocketCloseInitiated(ws, code, reason);
	}

	@Override
	public void onWebsocketError(WebSocket conn, Exception ex) {
		callback.onWebsocketError(conn, ex);
	}

	@Override
	public void onWriteDemand(WebSocket conn) {
		callback.onWriteDemand(conn);
	}

	@Override
	public InetSocketAddress getLocalSocketAddress(WebSocket conn) {
		return conn.getLocalSocketAddress();
	}

	@Override
	public InetSocketAddress getRemoteSocketAddress(WebSocket conn) {
		return conn.getRemoteSocketAddress();
	}


	@Override
	public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft,
			ClientHandshake request) throws InvalidDataException {
		return callback.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
	}

	@Override
	public void onWebsocketHandshakeReceivedAsClient(WebSocket conn, ClientHandshake request, ServerHandshake response)
			throws InvalidDataException {
		callback.onWebsocketHandshakeReceivedAsClient(conn, request, response);
	}

	@Override
	public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) throws InvalidDataException {
		callback.onWebsocketHandshakeSentAsClient(conn, request);
	}

	@Override
	public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
		callback.onWebsocketMessageFragment(conn, frame);
	}

	@Override
	public void onWebsocketPing(WebSocket conn, Framedata f) {
		callback.onWebsocketPing(conn, f);
	}

	@Override
	public void onWebsocketPong(WebSocket conn, Framedata f) {
		callback.onWebsocketPong(conn, f);
	}

	@Override
	public String getFlashPolicy(WebSocket conn) {
		return callback.getFlashPolicy(conn);
	}

}
