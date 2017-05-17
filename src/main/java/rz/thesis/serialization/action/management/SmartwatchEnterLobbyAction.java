package rz.thesis.serialization.action.management;

import rz.server.ConnectionsRouter;
import rz.server.Subscriber;
import rz.server.SubscriberType;

public class SmartwatchEnterLobbyAction extends EnterLobbyAction {
	
	private static final long serialVersionUID = -3018483622075602666L;
	
	public SmartwatchEnterLobbyAction() {
	}
	
	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		super.execute(router,wrapper);
		router.changeLobby(wrapper,getLobby(), SubscriberType.SMARTWATCH);
	}
	
	
	
}
