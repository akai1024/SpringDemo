package com.example.demo.slotgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.example.demo.slotgame.bingo.Line;
import com.example.demo.slotgame.bingo.Way;

/**
 * 排列帶庫
 */
public class RibbonBase {

	private int columns;

	private int rows;

	private ArrayList<Integer> bingoSymbols;

	private ArrayList<Integer> wildSymbols;
	
	private HashMap<Integer, Integer> minConnectSize;

	private ArrayList<ArrayList<Integer>> ribbons;

	private Random random;

	private ArrayList<ArrayList<Integer>> bingoLineIndexes;

	public RibbonBase(int columns, int rows, ArrayList<ArrayList<Integer>> ribbonsInput) {
		if (columns <= 0 || rows <= 0 || ribbonsInput == null) {
			throw new IllegalArgumentException();
		}

		if (columns != ribbonsInput.size()) {
			throw new IllegalArgumentException("colums must match ribbons length");
		}

		this.columns = columns;
		this.rows = rows;
		ribbons = new ArrayList<>();
		random = new Random();
		for (int colum = 0; colum < columns; colum++) {
			ArrayList<Integer> ribbon = ribbonsInput.get(colum);
			if (ribbon.size() < rows) {
				throw new IllegalArgumentException("ribbon size is smaller than rows, can't make it a normal screen");
			}
			ribbons.add(ribbon);
		}

	}

	/**
	 * 設定中獎symbol
	 */
	public void setBingoSymbols(ArrayList<Integer> bingoSymbols) {
		this.bingoSymbols = bingoSymbols;
	}

	/**
	 * 設定wild symbol
	 */
	public void setWildSymbols(ArrayList<Integer> wildSymbols) {
		this.wildSymbols = wildSymbols;
	}
	
	/**
	 * 設定中獎最小連線數
	 */
	public void setMinConnectSize(HashMap<Integer, Integer> minConnectSize) {
		this.minConnectSize = minConnectSize;
	}

	/**
	 * 設定中獎線位置
	 */
	public void setBingoLineIndexes(ArrayList<ArrayList<Integer>> bingoLineIndexes) {
		this.bingoLineIndexes = bingoLineIndexes;
	}

	/**
	 * 取得隨機盤面
	 */
	public ArrayList<Integer> getRandomScreen() {
		ArrayList<Integer> screen = new ArrayList<>();

		// 從第一輪(最左邊)開始隨機獲取
		for (int column = 0; column < columns; column++) {
			ArrayList<Integer> ribbon = ribbons.get(column);
			int ribbonSize = ribbon.size();
			int startIdx = random.nextInt(ribbonSize);
			int endIdx = startIdx + rows;
			int fillSize = 0;

			if (endIdx > ribbonSize) {
				fillSize = endIdx - ribbonSize;
				endIdx = ribbonSize;
			}

			screen.addAll(ribbon.subList(startIdx, endIdx));
			if (fillSize > 0) {
				screen.addAll(ribbon.subList(0, fillSize));
			}
		}

		return screen;
	}

	/**
	 * 取得中獎線
	 */
	public ArrayList<Line> getBingoLines(ArrayList<Integer> screen) {
		ArrayList<Line> lines = new ArrayList<>();
		if (bingoLineIndexes == null || bingoLineIndexes.size() <= 0) {
			return lines;
		}
		
		if(minConnectSize == null || minConnectSize.size() <= 0) {
			return lines;
		}

		for (int lineIdx = 0; lineIdx < bingoLineIndexes.size(); lineIdx++) {

			ArrayList<Integer> bingoLineIndex = bingoLineIndexes.get(lineIdx);
			int bingoSymbol = 0;
			int connect = 1;
			boolean isWildLead = false;
			for (int idx = 0; idx < bingoLineIndex.size(); idx++) {
				if (idx >= columns) {
					break;
				}

				int symbol = screen.get(bingoLineIndex.get(idx));
				boolean isWildSymbol = false;
				// 中獎symbol
				if (!bingoSymbols.contains(symbol)) {
					// 是wild
					if (wildSymbols != null && wildSymbols.contains(symbol)) {
						isWildSymbol = true;
					} else
						break;
				}

				// 線上第一個symbol
				if (idx == 0) {
					bingoSymbol = symbol;
					if (isWildSymbol) {
						isWildLead = true;
					}
				} else if (bingoSymbol == symbol) {
					connect++;
				} else if (isWildSymbol || isWildLead) {
					// 若此symbol非wild，替換領前的symbol
					if(isWildLead && !isWildSymbol) {
						bingoSymbol = symbol;
						isWildLead = false;
					}
					connect++;
				} else {
					break;
				}
			}

			// 有達到最小連線數
			if (connect >= minConnectSize.get(bingoSymbol)) {
				Line line = new Line();
				line.setSymbol(bingoSymbol);
				line.setConnect(connect);
				line.setBingoLineIndexs(new ArrayList<>(bingoLineIndex));
				lines.add(line);
			}
		}

		return lines;
	}

	/**
	 * 取得中獎way
	 */
	public ArrayList<Way> getBingoWays(ArrayList<Integer> screen, int minConnect) {
		ArrayList<Way> ways = new ArrayList<>();

//		for (int rowAtFirstCol = 0; rowAtFirstCol < rows; rowAtFirstCol++) {
//			int symbol = screen.get(rowAtFirstCol);
//
//			int connect = 1;
//			int waySize = 1;
//			for (int col = 1; col < columns; col++) {
//				int symbolCount = 0;
//				int fromIndex = col * rows;
//				int toIndex = fromIndex + rows;
//				
//				for (int idx = fromIndex; idx < toIndex; idx++) {
//					int symbolInCol = screen.get(idx);
//					if (symbolInCol == symbol) {
//						symbolCount++;
//					}
//				}
//
//				// 該輪獲得的symbol數量
//				if (symbolCount > 0) {
//					waySize *= symbolCount;
//					connect++;
//				} else {
//					break;
//				}
//			}
//			
//			if(connect >= minConnect) {
//				
//			}
//
//		}

		return ways;
	}

	public static void main(String[] args) {
		Random random = new Random();
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
		for(int symbol : bingoSymbols) {
			minConnectSize.put(symbol, 3);
		}
		minConnectSize.put(7, 2);
		minConnectSize.put(8, 3);
		
		ArrayList<ArrayList<Integer>> ribbons = new ArrayList<>();
		for (int col = 0; col < columns; col++) {
			int ribbonSize = random.nextInt(20) + rows;
			ArrayList<Integer> ribbon = new ArrayList<>();
			for (int i = 0; i < ribbonSize; i++) {
				ribbon.add(random.nextInt(symbolSize + 1) + 1);
			}
			ribbons.add(ribbon);
		}

		RibbonBase ribbonBase = new RibbonBase(columns, rows, ribbons);
		ribbonBase.setBingoSymbols(bingoSymbols);
		ribbonBase.setBingoLineIndexes(bingoLineIndexes);
		ribbonBase.setWildSymbols(wildSymbols);
		ribbonBase.setMinConnectSize(minConnectSize);

//		for (int col = 0; col < 50; col++) {
//			ArrayList<Integer> screen = ribbonBase.getRandomScreen();
//			ArrayList<Line> bingoLines = ribbonBase.getBingoLines(screen, minConnect);
//			if (bingoLines.size() > 0) {
//				System.out.println(screen);
//				System.out.println(bingoLines);
//			}
//
//		}
		
		ArrayList<Integer> screen = new ArrayList<>();
		screen.add(8);//0
		screen.add(1);//1
		screen.add(1);//2
		screen.add(7);//3
		screen.add(1);//4
		screen.add(1);//5
		screen.add(8);//6
		screen.add(1);//7
		screen.add(1);//8
		screen.add(7);//9
		screen.add(1);//10
		screen.add(1);//11
		screen.add(1);//12
		screen.add(1);//13
		screen.add(1);//14
		ArrayList<Line> bingoLines = ribbonBase.getBingoLines(screen);
		if (bingoLines.size() > 0) {
			System.out.println(screen);
			System.out.println(bingoLines);
		}
		
	}

}

