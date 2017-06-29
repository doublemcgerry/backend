package rz.thesis.server.lobby;

import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.Action;
import rz.thesis.server.serialization.action.management.ManagementAction;

public interface LobbiesManagerInterface {

	void handleAction(VirtualActor actor, Action action);

	void handleManagementAction(VirtualActor actor, ManagementAction action);

	void broadcastToWaitingRoom(Action action);

	void addActorToLobby(String lobbyName, VirtualActor actor);

	LobbiesAuthenticationInterface getAuthenticator();

	boolean reconnectToLobby(String lobby, VirtualActor actor);

	public String getLobbiesStatus();

}