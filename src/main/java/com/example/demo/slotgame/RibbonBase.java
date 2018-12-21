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

	/**
	 * 建構，提供盤面矩形大小與排列帶
	 */
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

			// 到達排列帶尾端
			if (endIdx > ribbonSize) {
				// 從頭補symbol
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

		if (minConnectSize == null || minConnectSize.size() <= 0) {
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
					if (isWildLead && !isWildSymbol) {
						bingoSymbol = symbol;
						isWildLead = false;
					}
					connect++;
				} else {
					break;
				}
			}

			// 有達到最小連線數
			if (minConnectSize.containsKey(bingoSymbol) && connect >= minConnectSize.get(bingoSymbol)) {
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
	public ArrayList<Way> getBingoWays(ArrayList<Integer> screen, boolean isWildWay) {
		ArrayList<Way> ways = new ArrayList<>();
		findBingoWays(ways, screen, false);
		if(isWildWay) {
			findBingoWays(ways, screen, true);	
		}
		return ways;
	}

	/**
	 * 尋找中獎way
	 */
	private void findBingoWays(ArrayList<Way> ways, ArrayList<Integer> screen, boolean isWildWay) {
		ArrayList<Integer> checkSymbols = isWildWay ? wildSymbols : bingoSymbols;

		// 以symbol去尋找中獎機會
		for (int symbol : checkSymbols) {
			if (minConnectSize == null || !minConnectSize.containsKey(symbol)) {
				continue;
			}

			int minConnect = minConnectSize.get(symbol);
			ArrayList<ArrayList<Integer>> bingoIndexes = new ArrayList<>();
			ArrayList<Integer> colIndex = new ArrayList<>();
			HashMap<Integer, Integer> search = new HashMap<>();

			// 每一個idx尋找
			boolean isAllWild = true;
			for (int idx = 0; idx < screen.size(); idx++) {
				int col = idx / rows;
				int symbolOnScreen = screen.get(idx);
				// 與當前尋找的symbol相同
				if (symbolOnScreen == symbol) {
					colIndex.add(idx);
					isAllWild = false;
				}
				// 或是wild
				else if (!isWildWay && wildSymbols != null && wildSymbols.contains(symbolOnScreen)) {
					colIndex.add(idx);
				}

				// 換輪
				if ((idx + 1) % rows == 0) {
					// 本輪都沒中
					if (colIndex.size() == 0) {
						break;
					}
					// 有中
					else {
						bingoIndexes.add(new ArrayList<>(colIndex));
						colIndex.clear();
						search.put(col, 0);
					}
				}
			}

			// 超過最小連線數而且不能全部都是wild
			if (bingoIndexes.size() >= minConnect && !isAllWild) {
				ArrayList<ArrayList<Integer>> indexes = transferCombination(search, bingoIndexes);
				for (ArrayList<Integer> index : indexes) {
					Way way = new Way();
					way.setSymbol(symbol);
					way.setBingoIndexs(index);
					ways.add(way);
				}
			}
		}
	}

	/**
	 * 把中獎位置轉出所有組合
	 */
	private static ArrayList<ArrayList<Integer>> transferCombination(HashMap<Integer, Integer> search,
			ArrayList<ArrayList<Integer>> indexes) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<>();
		boolean isLastColCarry = false;
		while (!isLastColCarry) {
			ArrayList<Integer> idxs = new ArrayList<>();
			// 需要進位的輪
			int carryCol = -1;
			for (int col = 0; col < indexes.size(); col++) {

				int idxOfSearch = search.get(col);
				ArrayList<Integer> colIdxs = indexes.get(col);
				idxs.add(colIdxs.get(idxOfSearch));

				if (col == 0) {
					idxOfSearch++;
				} else if (col == carryCol) {
					idxOfSearch++;
				}

				// 超過該輪可提供的位置大小，進行進位
				if (idxOfSearch >= colIdxs.size()) {
					idxOfSearch = 0;
					if (col == indexes.size() - 1) {
						isLastColCarry = true;
					} else {
						carryCol = col + 1;
					}
				}
				// 更新指標
				search.put(col, idxOfSearch);
			}
			// 加入彙整過的位置
			result.add(idxs);
		}

		return result;
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
		for (int symbol : bingoSymbols) {
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

		for (int col = 0; col < 50; col++) {
			ArrayList<Integer> screen = ribbonBase.getRandomScreen();
//			ArrayList<Line> bingoLines = ribbonBase.getBingoLines(screen);
			ArrayList<Way> bingoWays = ribbonBase.getBingoWays(screen, true);
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

}
