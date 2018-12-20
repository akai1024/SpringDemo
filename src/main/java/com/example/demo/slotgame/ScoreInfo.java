package com.example.demo.slotgame;

import java.util.ArrayList;

/**
 * 得分資訊
 */
public class ScoreInfo {

	private ArrayList<Integer> screen;
	
	private int scoreToken;

	public ArrayList<Integer> getScreen() {
		return screen;
	}

	public void setScreen(ArrayList<Integer> screen) {
		this.screen = screen;
	}

	public int getScoreToken() {
		return scoreToken;
	}

	public void setScoreToken(int scoreToken) {
		this.scoreToken = scoreToken;
	}
	
}
