package rz.thesis.server.serialization.action.management;

/**
 * this class defines the base structure for an actorAnnounceAction, which
 * contains basically the device independent attributes
 * 
 * @author achelius
 *
 */
public abstract class ActorAnnounceAction extends ManagementAction {

	private static final long serialVersionUID = 6722305545748319370L;

	private String name;

	public ActorAnnounceAction() {
	}

	public ActorAnnounceAction(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
