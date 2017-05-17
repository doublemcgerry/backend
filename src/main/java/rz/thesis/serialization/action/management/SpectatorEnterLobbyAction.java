package rz.thesis.serialization.action.management;

import rz.server.ConnectionsRouter;
import rz.server.Subscriber;
import rz.server.SubscriberType;

public class SpectatorEnterLobbyAction extends EnterLobbyAction {
	
	private static final long serialVersionUID = -3018483622075602666L;
	
	public SpectatorEnterLobbyAction() {
	}
	
	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		super.execute(router,wrapper);
		router.changeLobby(wrapper,getLobby(), SubscriberType.SPECTATOR);
	}
	
	
	
}
