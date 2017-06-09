package rz.thesis.server.serialization.action;

import java.io.Serializable;

/**
 * this action contains the information of the final destination through a relay
 * and the corresponding action to send to it
 * 
 * @author achelius
 *
 */
public class RelayAction extends Action implements Serializable {
	private static final long serialVersionUID = 6791206058113891635L;
	private String id;
	private Action action;

	public RelayAction(String id, Action action) {
		super();
		this.id = id;
		this.action = action;
	}

	public String getId() {
		return id;
	}

	public Action getAction() {
		return action;
	}

}
