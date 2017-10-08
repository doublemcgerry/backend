package rz.thesis.server.lobby;

import java.util.UUID;

import rz.thesis.server.serialization.action.Action;

public interface ActionSender {

	void sendAction(Action action);

	void sendAction(UUID source, UUID destination, Action action);
}
