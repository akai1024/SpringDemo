package com.example.demo.cardgame;

public enum GamePhase {

	MATCHING,

	INITIALIZING,

	GAMBLING,

	FIGHTING,

	CLEANING,

	;

	/**
	 * 不會回到matching
	 */
	public GamePhase getNext() {
		switch (this) {
		case MATCHING:
			return INITIALIZING;
		case INITIALIZING:
			return GAMBLING;
		case GAMBLING:
			return FIGHTING;
		case FIGHTING:
			return CLEANING;
		case CLEANING:
			return INITIALIZING;
		default:
			return null;
		}
	}

}
