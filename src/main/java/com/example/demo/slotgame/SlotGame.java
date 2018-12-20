package com.example.demo.slotgame;

import java.util.ArrayList;

/**
 * SLOT機台遊戲
 */
public abstract class SlotGame {

	protected RibbonBase ribbonBase;
	
	public SlotGame(int columns, int rows, ArrayList<ArrayList<Integer>> ribbonsInput) {
		ribbonBase = new RibbonBase(columns, rows, ribbonsInput);
		
		// 設定中獎線
		
		// 設定中獎symbol
		
		// 設定wild symbol
		
		// 設定連線數
		
	}
	
	/**
	 * 轉一把
	 */
	public ScoreInfo spin(BetInfo betInfo) {
		ScoreInfo scoreInfo = new ScoreInfo();
		ArrayList<Integer> screen = ribbonBase.getRandomScreen();
		scoreInfo.setScreen(screen);
		scoreInfo.setScoreToken(calculateTokens(betInfo, screen));
		return scoreInfo;
	}

	/**
	 * 得分計算方式
	 */
	protected abstract int calculateTokens(BetInfo betInfo, ArrayList<Integer> screen);
	
}
