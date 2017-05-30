package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.SubscriberType;

public class SpectatorEnterLobbyAction extends EnterLobbyAction {
	
	private static final long serialVersionUID = -3018483622075602666L;
	
	public SpectatorEnterLobbyAction() {
	}
	
	@Override
	public void execute(LobbiesManager router, Subscriber wrapper) {
		super.execute(router,wrapper);
		router.changeLobby(wrapper,getLobby(), SubscriberType.SPECTATOR);
	}
	
	
	
}
