package rz.thesis.server.lobby;

public interface LobbiesAuthenticationInterface {

	boolean isAuthenticated(Subscriber subscriber);

	AuthenticationInformation authenticate(Subscriber authenticator, String deviceKey);

	void addLobbyActorToWaitingRoom(String token, Subscriber actor);

	boolean containsTokenInWaitingRoom(String token);

	Subscriber retrieveFromWaitingRoom(String token);

	Subscriber removeFromWaitingRoom(String token);

	String generateNewToken();

}