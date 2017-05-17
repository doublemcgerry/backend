package rz.thesis.serialization.action.management;

import rz.server.ConnectionsRouter;
import rz.server.Subscriber;
import rz.server.SubscriberType;

public class MobileEnterLobbyAction extends EnterLobbyAction {

	private static final long serialVersionUID = 2202061539670703277L;
	
	public MobileEnterLobbyAction() {
		
	}
	
	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		super.execute(router,wrapper);
		router.changeLobby(wrapper, getLobby(), SubscriberType.MOBILE);
	}

}
