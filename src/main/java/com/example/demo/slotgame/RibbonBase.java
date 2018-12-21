package com.example.demo.slotgame;

import java.util.ArrayList;
import java.util.Random;

/**
 * 排列帶庫
 */
public class RibbonBase {

	/** 矩形最大輪數 */
	private int columns;

	/** 矩形最大行數 */
	private int rows;

	/** 排列帶 */
	private ArrayList<ArrayList<Integer>> ribbons;

	/** 隨機 */
	private Random random;

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

}
