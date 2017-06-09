package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.RelayDemux;
import rz.thesis.server.lobby.Subscriber;

public class RelayAnnounceAction extends ManagementAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1676622184417215167L;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, Subscriber wrapper) {
		lobbyManager.addRelay(new RelayDemux(lobbyManager, wrapper));
	}

}
