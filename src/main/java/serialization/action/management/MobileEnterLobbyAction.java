package serialization.action.management;

import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;
import websocketecho.SubscriberType;

public class MobileEnterLobbyAction extends ManagementAction {

	private static final long serialVersionUID = 2202061539670703277L;
	private String zone;
	
	public MobileEnterLobbyAction(String zone) {
		this.zone=zone;
	}
	
	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		System.out.println("Mobile Application" + wrapper.toString() + " asked to change zone to " + this.zone);
		router.changeLobby(wrapper, zone, SubscriberType.MOBILE);
	}

}
