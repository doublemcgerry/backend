package rz.thesis.server.lobby;

import java.util.Map;
import java.util.UUID;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.core.modules.http.HttpSessionsManager;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.Action;

public interface Tunnel extends ActionSender {

	boolean isAlive();

	UUID getUUID();

	void setUUID(UUID uuid);

	HttpSessionsManager getSessionsManager();

	HttpServerSession getServerSession();

	void setLobbyManager(LobbiesManagerInterface lobbyManagerInterface);

	LobbiesManagerInterface getLobbyManager();

	void addActor(VirtualActor actor);

	void removeActor(VirtualActor actor);

	void removeActor(UUID actorSession);

	Map<UUID, VirtualActor> getActors();

	VirtualActor getActorFromActorSession(UUID actorSession);

	boolean containsActor(UUID actorSession);

	void handleAction(VirtualActor actor, Action action);
}
