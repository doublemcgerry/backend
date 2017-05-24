package rz.thesis.server.serialization.action.management;

import rz.thesis.server.lobby.LobbiesManager;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.lobby.SubscriberType;

public class MobileEnterLobbyAction extends EnterLobbyAction {

	private static final long serialVersionUID = 2202061539670703277L;
	
	public MobileEnterLobbyAction() {
		
	}
	
	@Override
	public void execute(LobbiesManager router, Subscriber wrapper) {
		super.execute(router,wrapper);
		router.changeLobby(wrapper, getLobby(), SubscriberType.USER);
	}

}
