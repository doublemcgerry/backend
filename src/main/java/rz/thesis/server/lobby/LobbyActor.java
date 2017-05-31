package rz.thesis.server.lobby;

import java.util.UUID;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.core.modules.http.HttpSessionsManager;
import rz.thesis.server.serialization.action.Action;

public abstract class LobbyActor implements Subscriber {
	protected UUID id;
	protected String name;
	private Subscriber subscriber;

	public LobbyActor(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void sendAction(Action action) {
		this.subscriber.sendAction(action);
	}

	@Override
	public boolean isAlive() {
		return this.subscriber.isAlive();
	}

	@Override
	public void setCurrentServerInstance(ServerLobby serverInstance) {
		this.subscriber.setCurrentServerInstance(serverInstance);
	}

	@Override
	public void removeFromServerInstance() {
		this.removeFromServerInstance();
	}

	@Override
	public ServerLobby getCurrentServerInstance() {
		return this.getCurrentServerInstance();
	}

	@Override
	public UUID getUUID() {
		return this.subscriber.getUUID();
	}

	@Override
	public void setUUID(UUID uuid) {
		this.subscriber.setUUID(uuid);
	}
	/***
	 * Returns whether or not this actor refers to the provided wrapper
	 * @param wrapper
	 * @return
	 */
	public boolean isWrapper(Subscriber wrapper){
		return this.subscriber.equals(wrapper);
	}

	@Override
	public HttpSessionsManager getSessionsManager() {
		return this.subscriber.getSessionsManager();
	}

	@Override
	public HttpServerSession getServerSession() {
		return this.subscriber.getServerSession();
	}
	
	

}
