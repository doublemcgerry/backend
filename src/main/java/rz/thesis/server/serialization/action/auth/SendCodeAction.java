package rz.thesis.server.serialization.action.auth;

import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.Subscriber;
import rz.thesis.server.serialization.action.management.ManagementAction;

public class SendCodeAction extends ManagementAction {
	private static final long serialVersionUID = 1353808040784186537L;

	private String code;

<<<<<<< HEAD:src/main/java/rz/thesis/server/serialization/action/auth/SendTokenAction.java
	public SendTokenAction(String token) {
		this.token = token;
=======
	public SendCodeAction(String code) {
		this.code=code;
>>>>>>> SecondSeason:src/main/java/rz/thesis/server/serialization/action/auth/SendCodeAction.java
	}

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, Subscriber wrapper) {

	}

	public String getCode() {
		return code;
	}
<<<<<<< HEAD:src/main/java/rz/thesis/server/serialization/action/auth/SendTokenAction.java

=======
>>>>>>> SecondSeason:src/main/java/rz/thesis/server/serialization/action/auth/SendCodeAction.java
}
