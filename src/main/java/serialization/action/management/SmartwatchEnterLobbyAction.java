package serialization.action.management;

import websocketecho.ConnectionsRouter;
import websocketecho.Subscriber;
import websocketecho.SubscriberType;

public class SmartwatchEnterLobbyAction extends ManagementAction {
	
	private static final long serialVersionUID = -3018483622075602666L;
	private String zone;
	
	public SmartwatchEnterLobbyAction(String zone) {
		this.zone=zone;
	}
	
	@Override
	public void execute(ConnectionsRouter router, Subscriber wrapper) {
		System.out.println("Smartwatch " + wrapper.toString() + " asked to change zone to " + this.zone);
		router.changeLobby(wrapper, zone, SubscriberType.SMARTWATCH);
	}
}
