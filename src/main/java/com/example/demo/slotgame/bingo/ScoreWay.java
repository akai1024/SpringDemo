package com.example.demo.slotgame.bingo;

public class ScoreWay extends Way {

	private int scoreToken;

	public ScoreWay() {

	}

	public ScoreWay(Way way) {
		setSymbol(way.getSymbol());
		setBingoIndexs(way.getBingoIndexs());
	}

	@Override
	public String toString() {
		return "(" + scoreToken + ")" + super.toString();
	}

	public int getScoreToken() {
		return scoreToken;
	}

	public void setScoreToken(int scoreToken) {
		this.scoreToken = scoreToken;
	}

}
