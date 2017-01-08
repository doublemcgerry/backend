package utility;

import interfaces.LobbyLoggerInterface;
import interfaces.LobbyManagerInterface;
import interfaces.MainServerLoggerInterface;

public class FakeWriter implements MainServerLoggerInterface, LobbyManagerInterface, LobbyLoggerInterface {

	@Override
	public LobbyLoggerInterface createNewLobby(String lobbyToAdd) {
		System.out.println(lobbyToAdd);
		return this;
	}

	@Override
	public void logMainServerActivity(String text) {
		System.out.println(text);
	}

	@Override
	public void logDiscoveryServerActivity(String text) {
		System.out.println(text);
	}

	@Override
	public void logLobbyActivity(String textToLog) {
		System.out.println(textToLog);
	}

}
