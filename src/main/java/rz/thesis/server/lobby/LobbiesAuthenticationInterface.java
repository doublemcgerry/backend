package rz.thesis.server.lobby;

public interface LobbiesAuthenticationInterface {

	boolean isAuthenticated(Subscriber subscriber);

	AuthenticationInformation authenticate(Subscriber authenticator, String deviceKey);

	String addLobbyActorToWaitingRoom(Subscriber actor);

	boolean containsTokenInWaitingRoom(String token);

	Subscriber retrieveFromWaitingRoom(String token);

	Subscriber removeFromWaitingRoom(String token);

	String generateNewToken();

}