package com.example.demo.slotgame;

import java.util.ArrayList;

import com.example.demo.slotgame.bingo.ScoreLine;
import com.example.demo.slotgame.bingo.ScoreWay;

/**
 * 得分資訊
 */
public class ScoreInfo {

	private ArrayList<Integer> screen;

	private int scoreToken;

	private ArrayList<ScoreLine> scoreLines;

	private ArrayList<ScoreWay> scoreWays;

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

	public ArrayList<ScoreLine> getScoreLines() {
		return scoreLines;
	}

	public void setScoreLines(ArrayList<ScoreLine> scoreLines) {
		this.scoreLines = scoreLines;
	}

	public ArrayList<ScoreWay> getScoreWays() {
		return scoreWays;
	}

	public void setScoreWays(ArrayList<ScoreWay> scoreWays) {
		this.scoreWays = scoreWays;
	}

}
