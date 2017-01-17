package websocketecho;

import java.util.List;
import java.util.UUID;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;

import serialization.StringSerializer;
import serialization.action.Action;



public class ConnectionWrapper extends WebSocketImpl implements Subscriber{
	private UUID uuid;
	private String name;
	private ServerInstance serverInstance;
	
	public ConnectionWrapper(WebSocketListener listener, Draft draft, ConnectionsRouter connectionsRouter) {
		super(listener,draft);
		this.uuid=connectionsRouter.generateUUID();
	}
	
	public ConnectionWrapper(WebSocketListener listener, List<Draft> drafts, ConnectionsRouter connectionsRouter) {
		super(listener,drafts);
		this.uuid=connectionsRouter.generateUUID();
	}
	
	@Override
	public void sendActionToSubscriber(Action action) {
		try{
			this.send(StringSerializer.getSerializer().toJson(action,Action.class));
		}catch(Exception ex){
			
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
		ServerInstance currentgameinstance= this.serverInstance;
		removeFromServerInstance();
		this.uuid=uuid;
		setCurrentServerInstance(currentgameinstance);
	}
	
	@Override
	public ServerInstance getCurrentServerInstance() {
		return serverInstance;
	}
	
	@Override
	public void setCurrentServerInstance(ServerInstance gameInstance) {
		removeFromServerInstance();
		if (gameInstance!=null) {
			this.serverInstance=gameInstance;
		}
	}
	
	@Override
	public void removeFromServerInstance() {
		if (this.serverInstance!=null){
			this.serverInstance.removeSubscriber(this);
		}
		this.serverInstance=null;
	}
	
	@Override
	public String toString(){
		return this.uuid.toString();
		
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

}
