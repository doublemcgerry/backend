package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import rz.thesis.core.modules.http.HttpServerSession;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.serialization.action.Action;

public class LobbiesAuthenticator implements LobbiesAuthenticationInterface {
	/**
	 * map of the lobby actors that are currently waiting associations<br/>
	 * the key is the pairing token, the value is the actor that is associated to
	 * the device
	 */
	private Map<String, VirtualActor> waitingRoom;

	public LobbiesAuthenticator() {
		this.waitingRoom = new HashMap<>();
	}

	@Override
	public AuthenticationInformation authenticate(HttpServerSession session, String deviceKey) {
		if (session.isAuthenticated()) {
			String username = session.getUsername();

			if (containsTokenInWaitingRoom(deviceKey)) {
				final VirtualActor sub = retrieveFromWaitingRoom(deviceKey);
				VirtualActor authenticatedActor = removeFromWaitingRoom(deviceKey);
				return new AuthenticationInformation(username, deviceKey, session, authenticatedActor);
			}

		} else {
			throw new RuntimeException("no authentication, no party");
		}
		return null;
	}

	@Override
	public void addActorToWaitingRoom(String token, VirtualActor actor) {
		synchronized (waitingRoom) {
			waitingRoom.put(token, actor);
		}
	}

	@Override
	public boolean containsTokenInWaitingRoom(String token) {
		synchronized (waitingRoom) {
			return waitingRoom.containsKey(token);
		}
	}

	@Override
	public VirtualActor retrieveFromWaitingRoom(String token) {
		synchronized (waitingRoom) {
			return waitingRoom.get(token);
		}
	}

	@Override
	public VirtualActor removeFromWaitingRoom(String token) {
		synchronized (waitingRoom) {
			return waitingRoom.remove(token);
		}
	}

	@Override
	public void broadcastToWaitingRoom(Action action) {
		for (Map.Entry<String, VirtualActor> vActorEntry : this.waitingRoom.entrySet()) {
			vActorEntry.getValue().sendActionToRemote(action);
		}
	}

	public Map<String, VirtualActor> getWaitingRoom() {
		return waitingRoom;
	}

	private static final Random random = new Random();
	private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

	private static String getToken(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			token.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}
		return token.toString();
	}

	@Override
	public String generateNewToken() {
		String token = getToken(4);
		synchronized (waitingRoom) {
			while (waitingRoom.containsKey(token)) {
				token = getToken(4);
			}
		}
		return token;
	}

	@Override
	public void removeFromWaitingRoom(VirtualActor value) {
		synchronized (waitingRoom) {
			for (Map.Entry<String, VirtualActor> vActorEntry : this.waitingRoom.entrySet()) {
				if (vActorEntry.getValue() == value) {
					this.waitingRoom.remove(vActorEntry.getKey());
					return;
				}
			}
		}
	}

}
