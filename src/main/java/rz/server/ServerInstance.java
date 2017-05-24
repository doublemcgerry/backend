package rz.server;

import java.util.ArrayList;
import java.util.List;

import rz.thesis.logger.interfaces.LobbyLoggerInterface;
import rz.thesis.serialization.action.Action;
import rz.thesis.serialization.action.lobby.ConnectedDeviceEvent;
import rz.thesis.serialization.action.lobby.DisconnectedDeviceEvent;

public class ServerInstance {

	private List<Subscriber> spectator = new ArrayList<Subscriber>();
	private Subscriber smartwatch;
	private Subscriber mobile;
	private LobbyLoggerInterface log;

	public ServerInstance(LobbyLoggerInterface log) {
		this.log = log;
	}

	public void addSmartwatch(Subscriber smartwatch) {
		this.smartwatch = smartwatch;
		this.broadcastAction(new ConnectedDeviceEvent(smartwatch.getUUID().toString(), smartwatch.getName(),
				SubscriberType.SMARTWATCH));
	}

	public boolean isSmartwatchPresent() {
		if (smartwatch == null) {
			return false;
		} else {
			return true;
		}
	}

	public void addMobile(Subscriber mobile) {
		this.mobile = mobile;
		this.broadcastAction(
				new ConnectedDeviceEvent(mobile.getUUID().toString(), mobile.getName(), SubscriberType.MOBILE));
		// TODO messaggi nella lobby
	}

	public boolean isMobilePresent() {
		if (mobile == null) {
			return false;
		} else {
			return true;
		}
	}

	public void addSpectator(Subscriber subscriber) {
		this.spectator.add(subscriber);
		this.broadcastAction(new ConnectedDeviceEvent(subscriber.getUUID().toString(), subscriber.getName(),
				SubscriberType.SPECTATOR));
	}

	public void removeSubscriber(Subscriber subscriber) {
		this.spectator.remove(subscriber);
		this.broadcastAction(new DisconnectedDeviceEvent(subscriber.getUUID().toString(), subscriber.getName(),
				SubscriberType.SPECTATOR));
	}

	public void broadcastAction(Action action) {
		for (Subscriber subscriber : spectator) {
			subscriber.sendAction(action);
		}
		if (smartwatch != null) {
			smartwatch.sendAction(action);
		}
		if (mobile != null) {
			mobile.sendAction(action);
		}
		log.logLobbyActivity(action.toString());
	}

}
