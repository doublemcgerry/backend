package rz.thesis.server.serialization.action.management;

public abstract class ActorAnnounceAction extends ManagementAction {

	private static final long serialVersionUID = 6722305545748319370L;

	private String name;

	public ActorAnnounceAction() {
	}

	public ActorAnnounceAction(String name) {
		this.name = name;
	}

}
