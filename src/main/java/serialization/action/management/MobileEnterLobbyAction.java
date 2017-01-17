package serialization.action.management;

import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;
import websocketecho.SubscriberType;

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
