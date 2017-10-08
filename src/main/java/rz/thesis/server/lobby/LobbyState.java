package rz.thesis.server.lobby;

public enum LobbyState {
	NO_EXPERIENCE(0), EXPERIENCE_SELECTED(1), READY_TO_START(2), EXPERIENCE_STARTED(3), EXPERIENCE_ENDED(4);
	private int index;

	LobbyState(int index) {
		this.index = index;
	}

	/**
	 * returns true if the state is before the given one (exclusive)
	 * 
	 * @param state
	 * @return
	 */
	public boolean isBefore(LobbyState state) {
		return this.index < state.index;
	}

	/**
	 * returns true if the state is before the given one (inclusive)
	 * 
	 * @param state
	 * @return
	 */
	public boolean isBeforeI(LobbyState state) {
		return this.index <= state.index;
	}

	/**
	 * returns true if the state is after the given one (exclusive)
	 * 
	 * @param state
	 * @return
	 */
	public boolean isAfter(LobbyState state) {
		return this.index > state.index;
	}

	/**
	 * returns true if the state is after the given one (inclusive)
	 * 
	 * @param state
	 * @return
	 */
	public boolean isAfterI(LobbyState state) {
		return this.index >= state.index;
	}

	/**
	 * returns true if the state is between start and end (exclusive)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean isBetween(LobbyState start, LobbyState end) {
		return this.isAfter(start) && this.isBefore(end);
	}

	/**
	 * returns true if the state is between start and end (inclusive)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean isBetweenI(LobbyState start, LobbyState end) {
		return this.isAfterI(start) && this.isBeforeI(end);
	}

}