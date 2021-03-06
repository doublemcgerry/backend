package rz.thesis.server.serialization.action;

import java.io.Serializable;
import java.util.UUID;

public abstract class Action implements Serializable {
	private static final long serialVersionUID = 8603051382268444140L;

	public static final UUID BROADCAST_DESTINATION = UUID.fromString("635ef93e-dc73-4276-aee7-4960347c1081");
	public static final UUID LOCAL_LOBBY = UUID.fromString("c8329626-b1b0-443b-8ea6-480a6b1b4025");

	protected UUID source = LOCAL_LOBBY;
	protected UUID destination = LOCAL_LOBBY;
	protected boolean debuggable = true;

	protected Action() {

	}

	public UUID getSource() {
		return source;
	}

	public void setSource(UUID sourceId) {
		this.source = sourceId;
	}

	public UUID getDestination() {
		return destination;
	}

	public void setDestination(UUID destinationId) {
		this.destination = destinationId;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " source:" + (source == null ? "nosource" : source.toString())
				+ " destination:" + (destination == null ? "nodestination" : destination.toString());
	}

	public boolean isDebuggable() {
		return debuggable;
	}

}
