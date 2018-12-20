package com.example.demo.slotgame.bingo;

import java.util.ArrayList;

/**
 * 中獎Way
 */
public class Way {

	/** 圖標 */
	private int symbol;
	
	/** 中獎位置 */
	private ArrayList<Integer> bingoIndexs;

	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	public ArrayList<Integer> getBingoIndexs() {
		return bingoIndexs;
	}

	public void setBingoIndexs(ArrayList<Integer> bingoIndexs) {
		this.bingoIndexs = bingoIndexs;
	}
	
}
