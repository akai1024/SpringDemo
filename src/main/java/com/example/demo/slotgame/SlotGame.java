package com.example.demo.slotgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.example.demo.slotgame.bingo.ScoreLine;
import com.example.demo.slotgame.bingo.ScoreWay;
import com.example.demo.slotgame.bingo.Way;

/**
 * SLOT機台遊戲
 */
public abstract class SlotGame {

	protected RibbonBase ribbonBase;
	
	protected BingoCalculator calculator;
	
	/**
	 * 建構
	 */
	public SlotGame(int columns, int rows, ArrayList<ArrayList<Integer>> ribbonsInput) {
		ribbonBase = new RibbonBase(columns, rows, ribbonsInput);
	}

	/**
	 * 設定中獎計算機
	 */
	protected void setCalculator(
			int columns,
			int rows,
			ArrayList<Integer> bingoSymbols,
			ArrayList<Integer> wildSymbols,
			HashMap<Integer, Integer> minConnectSize,
			ArrayList<ArrayList<Integer>> bingoLineIndexes) {
		calculator = new BingoCalculator(columns, rows);
		calculator.setBingoSymbols(bingoSymbols);
		calculator.setWildSymbols(wildSymbols);
		calculator.setMinConnectSize(minConnectSize);
		calculator.setBingoLineIndexes(bingoLineIndexes);
	}
	
	/**
	 * 轉一把
	 */
	public ScoreInfo spin(BetInfo betInfo) {
		ScoreInfo scoreInfo = new ScoreInfo();
		ArrayList<Integer> screen = ribbonBase.getRandomScreen();
		scoreInfo.setScreen(screen);
		scoreInfo.setScoreLines(calculateLineTokens(betInfo, screen));
		scoreInfo.setScoreWays(calculateWayTokens(betInfo, screen));
		scoreInfo.setScoreToken(calculateTotalTokens(scoreInfo));
		return scoreInfo;
	}

	/**
	 * 得分線計算方式
	 */
	protected abstract ArrayList<ScoreLine> calculateLineTokens(BetInfo betInfo, ArrayList<Integer> screen);	
	
	/**
	 * 得分way計算方式
	 */
	protected abstract ArrayList<ScoreWay> calculateWayTokens(BetInfo betInfo, ArrayList<Integer> screen);

	/**
	 * 總分
	 */
	protected abstract int calculateTotalTokens(ScoreInfo scoreInfo);
	
	
	
	
	public static void main(String[] args) {
		int symbolSize = 6;
		int columns = 5;
		int rows = 3;

		ArrayList<Integer> bingoSymbols = new ArrayList<>();
		for (int symbol = 1; symbol <= symbolSize; symbol++) {
			bingoSymbols.add(symbol);
		}

		ArrayList<Integer> wildSymbols = new ArrayList<>();
		wildSymbols.add(7);
		wildSymbols.add(8);

		ArrayList<ArrayList<Integer>> bingoLineIndexes = new ArrayList<>();
		ArrayList<Integer> line1 = new ArrayList<>();
		line1.add(0);
		line1.add(3);
		line1.add(6);
		line1.add(9);
		line1.add(12);
		bingoLineIndexes.add(line1);

		HashMap<Integer, Integer> minConnectSize = new HashMap<>();
		for (int symbol : bingoSymbols) {
			minConnectSize.put(symbol, 3);
		}
		minConnectSize.put(7, 2);
		minConnectSize.put(8, 3);

		
		ArrayList<Integer> symbolList = new ArrayList<>();
		symbolList.addAll(bingoSymbols);
		symbolList.addAll(wildSymbols);
		RibbonBase ribbonBase = new RibbonBase(columns, rows, randomRibbons(columns, rows, symbolList));
		BingoCalculator calculator = new BingoCalculator(columns, rows);
		calculator.setBingoSymbols(bingoSymbols);
		calculator.setBingoLineIndexes(bingoLineIndexes);
		calculator.setWildSymbols(wildSymbols);
		calculator.setMinConnectSize(minConnectSize);

		for (int col = 0; col < 50; col++) {
			ArrayList<Integer> screen = ribbonBase.getRandomScreen();
//			ArrayList<Line> bingoLines = ribbonBase.getBingoLines(screen);
			ArrayList<Way> bingoWays = calculator.getBingoWays(screen, true);
			if (bingoWays.size() > 0) {
				System.out.println(screen);
//				System.out.println(bingoLines);
				System.out.println(bingoWays);
			}

		}

//		ArrayList<Integer> screen = new ArrayList<>();
//		screen.add(8);// 0
//		screen.add(1);// 1
//		screen.add(1);// 2
//		screen.add(7);// 3
//		screen.add(1);// 4
//		screen.add(1);// 5
//		screen.add(8);// 6
//		screen.add(1);// 7
//		screen.add(1);// 8
//		screen.add(7);// 9
//		screen.add(7);// 10
//		screen.add(7);// 11
//		screen.add(1);// 12
//		screen.add(1);// 13
//		screen.add(1);// 14
//		ArrayList<Line> bingoLines = ribbonBase.getBingoLines(screen);
//		ArrayList<Way> bingoWays = ribbonBase.getBingoWays(screen);
//		if (bingoLines.size() > 0) {
//			System.out.println(screen);
//			System.out.println(bingoLines);
//			System.out.println(bingoWays);
//		}

	}
	
	public static ArrayList<ArrayList<Integer>> randomRibbons(int columns, int rows, ArrayList<Integer> symbolList) {
		Random random = new Random();
		ArrayList<ArrayList<Integer>> ribbons = new ArrayList<>();
		for (int col = 0; col < columns; col++) {
			int ribbonSize = random.nextInt(20) + rows;
			ArrayList<Integer> ribbon = new ArrayList<>();
			for (int i = 0; i < ribbonSize; i++) {
				int idxInSymbol = random.nextInt(symbolList.size());
				ribbon.add(symbolList.get(idxInSymbol));
			}
			ribbons.add(ribbon);
		}
		return ribbons;
	}
	
}
