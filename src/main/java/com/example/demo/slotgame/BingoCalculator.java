package com.example.demo.slotgame;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.slotgame.bingo.Line;
import com.example.demo.slotgame.bingo.Way;

/**
 * 中獎計算機
 */
public class BingoCalculator {

	/** 矩形最大輪數 */
	private int columns;

	/** 矩形最大行數 */
	private int rows;

	/** 一般中獎symbol */
	private ArrayList<Integer> bingoSymbols;

	/** wild symbol */
	private ArrayList<Integer> wildSymbols;

	/** 最小連線數 */
	private HashMap<Integer, Integer> minConnectSize;

	/** 中獎線位置 */
	private ArrayList<ArrayList<Integer>> bingoLineIndexes;

	public BingoCalculator(int columns, int rows) {
		if (columns <= 0 || rows <= 0) {
			throw new IllegalArgumentException();
		}
		this.columns = columns;
		this.rows = rows;
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
		if (isWildWay) {
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

}
