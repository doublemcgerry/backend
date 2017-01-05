package utility;

import interfaces.AdderInterface;
import interfaces.WriterInterface;

public class FakeWriter implements WriterInterface, AdderInterface {

	@Override
	public void addNewLobby(String lobbyToAdd) {
		System.out.println(lobbyToAdd);
	}

	@Override
	public void addMainServerText(String text) {
		System.out.println(text);
	}

	@Override
	public void addUDPServerText(String text) {
		System.out.println(text);
	}

}
