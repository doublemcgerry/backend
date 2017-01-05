package serialization.action.management;

import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;
import websocketecho.SubscriberType;

public class EnterLobbyAction extends ManagementAction {

	private static final long serialVersionUID = 6722305545748319370L;
	private String lobby;
	
	public EnterLobbyAction(String lobby) {
		this.lobby=lobby;
	}
	
	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		router.changeLobby(wrapper, lobby, SubscriberType.SPECTATOR);
	}
}
