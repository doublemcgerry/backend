package websocketecho;

import java.util.List;
import java.util.UUID;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;

import serialization.StringSerializer;
import serialization.action.Action;



public class ConnectionWrapper extends WebSocketImpl implements Subscriber{
	private UUID _uuid;
	private ServerInstance _gameInstance;
	
	public ConnectionWrapper(WebSocketListener listener, Draft draft, ConnectionsRouter connectionsRouter) {
		super(listener,draft);
		this._uuid=connectionsRouter.generateUUID();
	}
	
	public ConnectionWrapper(WebSocketListener listener, List<Draft> drafts, ConnectionsRouter connectionsRouter) {
		super(listener,drafts);
		this._uuid=connectionsRouter.generateUUID();
	}
	
	@Override
	public void sendActionToSubscriber(Action action) {
		this.send(StringSerializer.getSerializer().toJson(action,Action.class));
	}

	@Override
	public boolean isAlive() {
		return this.isOpen();
	}

	@Override
	public UUID getUUID() {
		return this._uuid;
	}
	
	@Override
	public void setUUID(UUID uuid) {
		ServerInstance currentgameinstance= this._gameInstance;
		removeFromGameInstance();
		this._uuid=uuid;
		setCurrentGameInstance(currentgameinstance);
	}
	
	@Override
	public ServerInstance getCurrentGameInstance() {
		return _gameInstance;
	}
	
	@Override
	public void setCurrentGameInstance(ServerInstance gameInstance) {
		removeFromGameInstance();
		if (gameInstance!=null) {
			this._gameInstance=gameInstance;
		}
	}
	
	@Override
	public void removeFromGameInstance() {
		if (this._gameInstance!=null){
			this._gameInstance.removeSubscriber(this);
		}
		this._gameInstance=null;
	}
	
	@Override
	public String toString(){
		return this._uuid.toString();
		
	}

}
