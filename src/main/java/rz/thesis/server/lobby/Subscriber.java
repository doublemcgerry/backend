package rz.thesis.server.lobby;

import java.util.UUID;

import rz.thesis.server.serialization.action.Action;


public interface Subscriber {
	void sendAction(Action action);
	boolean isAlive();
	void setCurrentServerInstance(ServerLobby serverInstance);
	void removeFromServerInstance();
	ServerLobby getCurrentServerInstance();
	UUID getUUID();
	void setUUID(UUID uuid);
	String getName();
	void setName(String name);
}
