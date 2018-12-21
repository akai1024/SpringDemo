package com.example.demo.slotgame;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.slotgame.bingo.ScoreLine;
import com.example.demo.slotgame.bingo.ScoreWay;
import com.example.demo.slotgame.bingo.Way;

public class SlotGameA extends SlotGame {

	public static final int COLUMNS = 5;
	public static final int ROWS = 3;
	private static final int MIN_CONNECT_SIZE = 3;
	private static final ArrayList<Integer> symbolList;

	/**
	 * 中獎線賠率
	 */
	@SuppressWarnings("unused")
	private HashMap<String, Integer> lineOdds;
	
	/**
	 * 中獎way賠率
	 */
	private HashMap<String, Integer> wayOdds;

	static {
		symbolList = new ArrayList<>();

		// 測試用隨便加
		for (int symbol = 1; symbol <= 5; symbol++) {
			symbolList.add(symbol);
		}
	}

	public SlotGameA() {
		super(COLUMNS, ROWS, randomRibbons(COLUMNS, ROWS, symbolList));

		lineOdds = new HashMap<>();
		wayOdds = new HashMap<>();
		
		// 測試用隨便加
		wayOdds.put("1_5", 100);
		wayOdds.put("1_4", 10);
		wayOdds.put("1_3", 2);
		wayOdds.put("2_5", 50);
		wayOdds.put("2_4", 5);
		wayOdds.put("2_3", 2);
		wayOdds.put("3_5", 30);
		wayOdds.put("3_4", 3);
		wayOdds.put("3_3", 2);
		wayOdds.put("4_5", 10);
		wayOdds.put("4_4", 3);
		wayOdds.put("4_3", 1);
		wayOdds.put("5_5", 5);
		wayOdds.put("5_4", 3);
		wayOdds.put("5_3", 1);
		
		HashMap<Integer, Integer> minConnectSize = new HashMap<>();
		for(int symbol : symbolList) {
			minConnectSize.put(symbol, MIN_CONNECT_SIZE);
		}
		
		setCalculator(COLUMNS, ROWS, symbolList, null, minConnectSize, null);
	}

	@Override
	protected ArrayList<ScoreLine> calculateLineTokens(BetInfo betInfo, ArrayList<Integer> screen) {
		return new ArrayList<>();
	}

	@Override
	protected ArrayList<ScoreWay> calculateWayTokens(BetInfo betInfo, ArrayList<Integer> screen) {
		ArrayList<Way> ways = calculator.getBingoWays(screen, false);
		ArrayList<ScoreWay> bingoWays = new ArrayList<>();
		for (Way way : ways) {
			ScoreWay sWay = new ScoreWay(way);
			String bingoKey = sWay.getBingoKey();
			if (wayOdds.containsKey(bingoKey)) {
				int odds = wayOdds.get(bingoKey);
				sWay.setScoreToken(betInfo.getTotalToken() * odds);
				bingoWays.add(sWay);
			}
		}
		return bingoWays;
	}

	@Override
	protected int calculateTotalTokens(ScoreInfo scoreInfo) {
		int totalScoreTokens = 0;
		for (ScoreWay sWay : scoreInfo.getScoreWays()) {
			totalScoreTokens += sWay.getScoreToken();
		}
		return totalScoreTokens;
	}

}
