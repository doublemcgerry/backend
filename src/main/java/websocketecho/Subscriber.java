package websocketecho;

import java.util.UUID;

import serialization.action.Action;


public interface Subscriber {
	void sendActionToSubscriber(Action action);
	boolean isAlive();
	void setCurrentServerInstance(ServerInstance serverInstance);
	void removeFromServerInstance();
	ServerInstance getCurrentServerInstance();
	UUID getUUID();
	void setUUID(UUID uuid);
	String getName();
	void setName(String name);
}
