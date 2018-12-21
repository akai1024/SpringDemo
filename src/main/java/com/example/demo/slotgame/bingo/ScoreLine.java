package com.example.demo.slotgame.bingo;

public class ScoreLine extends Line {

	private int scoreToken;

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
