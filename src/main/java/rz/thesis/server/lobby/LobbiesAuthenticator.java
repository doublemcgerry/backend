package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import rz.thesis.core.modules.http.HttpSessionsManager;

public class LobbiesAuthenticator implements LobbiesAuthenticationInterface {
	/**
	 * map of the lobby actors that are currently waiting associations<br/>
	 * the key is the pairing token, the value is the actor that is associated
	 * to the device
	 */
	private Map<String, Subscriber> waitingRoom;

	public LobbiesAuthenticator() {
		this.waitingRoom = new HashMap<>();
	}

	@Override
	public boolean isAuthenticated(Subscriber subscriber) {
		return subscriber.getServerSession().isAuthenticated();
	}

	@Override
	public AuthenticationInformation authenticate(final Subscriber authenticator, String deviceKey) {

		if (authenticator.getServerSession().isAuthenticated()) {
			String username = authenticator.getServerSession().getUsername();

			if (containsTokenInWaitingRoom(deviceKey)) {
				final Subscriber sub = retrieveFromWaitingRoom(deviceKey);
				HttpSessionsManager.authenticateSession(sub.getServerSession(), username);
				Subscriber authenticatedSubscriber = removeFromWaitingRoom(deviceKey);

				return new AuthenticationInformation(username, deviceKey, authenticatedSubscriber, authenticator,
						authenticatedSubscriber.getServerSession());
			}

		} else {
			throw new RuntimeException("no authentication, no party");
		}
		return null;

	}

	@Override
	public String addLobbyActorToWaitingRoom(Subscriber actor) {
		String token = getToken(4);
		synchronized (waitingRoom) {
			while (waitingRoom.containsKey(token)) {
				token = getToken(4);
			}
			waitingRoom.put(token, actor);
		}
		return token;
	}

	@Override
	public boolean containsTokenInWaitingRoom(String token) {
		synchronized (waitingRoom) {
			return waitingRoom.containsKey(token);
		}
	}

	@Override
	public Subscriber retrieveFromWaitingRoom(String token) {
		synchronized (waitingRoom) {
			return waitingRoom.get(token);
		}
	}

	@Override
	public Subscriber removeFromWaitingRoom(String token) {
		synchronized (waitingRoom) {
			return waitingRoom.remove(waitingRoom);
		}
	}

	public Map<String, Subscriber> getWaitingRoom() {
		return waitingRoom;
	}

	private static final Random random = new Random();
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";

	private static String getToken(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			token.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}
		return token.toString();
	}
}
