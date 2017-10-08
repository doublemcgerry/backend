package rz.thesis.server.lobby;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.Action;

public interface LobbiesAuthenticationInterface {

	AuthenticationInformation authenticate(HttpServerSession session, String deviceKey);

	void addActorToWaitingRoom(String token, VirtualActor actor);

	boolean containsTokenInWaitingRoom(String token);

	VirtualActor retrieveFromWaitingRoom(String token);

	VirtualActor removeFromWaitingRoom(String token);

	void broadcastToWaitingRoom(Action action);

	String generateNewToken();

	void removeFromWaitingRoom(VirtualActor value);

}