package rz.thesis.server.lobby;

import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.management.ManagementAction;

public interface LobbiesManagerInterface {

	void handleAction(Subscriber wrapper, Action action);

	void handleManagementAction(Subscriber wrapper, ManagementAction action);

	AuthenticationInformation authenticate(Subscriber authenticator, String deviceKey);

	void broadcastToWaitingRoom(Action action);

	void addRelay(RelayDemux relayDemux);

	void removeRelay(RelayDemux relayDemux);

	void addActorToLobby(String lobbyName, LobbyActor actor);

	void addLobbyActorToWaitingRoom(LobbyActor actor);

}