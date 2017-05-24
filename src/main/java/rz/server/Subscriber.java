package rz.server;

import java.util.UUID;

import rz.thesis.serialization.action.Action;


public interface Subscriber {
	void sendAction(Action action);
	boolean isAlive();
	void setCurrentServerInstance(ServerInstance serverInstance);
	void removeFromServerInstance();
	ServerInstance getCurrentServerInstance();
	UUID getUUID();
	void setUUID(UUID uuid);
	String getName();
	void setName(String name);
}
