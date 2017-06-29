package rz.thesis.server.serialization.action.lobby;

import java.io.FileNotFoundException;
import java.util.UUID;

import rz.thesis.modules.experience.Experience;
import rz.thesis.modules.experience.ExperienceDefinitionParameters;
import rz.thesis.modules.experience.ExperiencesModule;
import rz.thesis.server.lobby.LobbiesManagerInterface;
import rz.thesis.server.lobby.ServerLobby;
import rz.thesis.server.lobby.actors.VirtualActor;
import rz.thesis.server.lobby.actors.concrete.MobileScreenActor;

public class SelectExperienceAction extends LobbyAction {
	private static final long serialVersionUID = 2096847257069776706L;
	private UUID experienceId;

	@Override
	public void execute(LobbiesManagerInterface lobbyManager, ExperiencesModule experiencesModule, ServerLobby lobby,
	        VirtualActor actor) {
		if (actor.hasLobbyActor() && actor.getLobbyActor() instanceof MobileScreenActor) {
			Experience exp = experiencesModule.getController().getExperience(0, experienceId);
			lobby.initiateExperience(exp);
			ExperienceDefinitionParameters params = null;
			try {
				params = exp.getParameters();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lobby.broadcastEvent(new SelectedExperienceEvent(params));
		}
	}

}
