package websocketecho;

import java.util.UUID;

import serialization.action.Action;


public interface Subscriber {
	void sendActionToSubscriber(Action action);
	boolean isAlive();
	UUID getUUID();
	void setCurrentGameInstance(ServerInstance gameInstance);
	void removeFromGameInstance();
	ServerInstance getCurrentGameInstance();
	void setUUID(UUID uuid);
}
