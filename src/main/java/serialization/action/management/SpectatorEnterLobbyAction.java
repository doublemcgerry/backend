package serialization.action.management;

import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;
import websocketecho.SubscriberType;

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
