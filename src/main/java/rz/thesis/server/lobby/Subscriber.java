package rz.thesis.server.lobby;

import java.util.UUID;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.core.modules.http.HttpSessionsManager;
import rz.thesis.server.serialization.action.Action;

public interface Subscriber {
	void sendAction(Subscriber subscriber, Action action);

	boolean isAlive();

	void setCurrentServerInstance(ServerLobby serverInstance);

	void removeFromServerInstance();

	ServerLobby getCurrentServerInstance();

	UUID getUUID();

	void setUUID(UUID uuid);

	HttpSessionsManager getSessionsManager();

	HttpServerSession getServerSession();

	void handleAction(Subscriber subscriber, Action action);

	void setLobbyManager(LobbiesManagerInterface lobbyManagerInterface);

	LobbiesManagerInterface getLobbyManager();
}
