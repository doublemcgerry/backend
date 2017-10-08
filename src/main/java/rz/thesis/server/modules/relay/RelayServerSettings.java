package rz.thesis.server.modules.relay;

import rz.thesis.core.modules.CoreModuleSettings;

/**
 * this class contains the settings for the relay server
 * 
 * @author achelius
 *
 */
public class RelayServerSettings extends CoreModuleSettings {
	private static final long serialVersionUID = 1877494544746706603L;
	private int listenPort;
	private String remoteAddress;
	private String relayName;

	/**
	 * creates a new relay settings container
	 * 
	 * @param listenPort
	 *            port where the relay server listens to
	 * @param remoteAddress
	 *            address of the remote server
	 * @param relayName
	 *            name of this relay
	 */
	public RelayServerSettings(int listenPort, String remoteAddress, String relayName) {
		super();
		this.listenPort = listenPort;
		this.remoteAddress = remoteAddress;
		this.relayName = relayName;
	}

	/**
	 * returns the port on which the relay must listen to
	 * 
	 * @return
	 */
	public int getListenPort() {
		return listenPort;
	}

	/**
	 * returns the remote address of the main server or another relay server
	 * 
	 * @return
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * returns the name of this relay
	 * 
	 * @return
	 */
	public String getRelayName() {
		return relayName;
	}

}
