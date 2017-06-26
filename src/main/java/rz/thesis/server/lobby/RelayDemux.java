package rz.thesis.server.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class provides the demux functionality for the relay, enables the
 * creation of the various devices and enables the tunneling
 * 
 * @author achelius
 *
 */
public class RelayDemux {

	private final LobbiesManagerInterface lobbiesManager;

	private Map<UUID, Tunnel> subscribers;

	public RelayDemux(LobbiesManagerInterface lobbyManager, Tunnel subscriber) {
		this.subscribers = new HashMap<>();
		this.lobbiesManager = lobbyManager;
	}

}
