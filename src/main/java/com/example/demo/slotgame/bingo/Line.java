package com.example.demo.slotgame.bingo;

import java.util.ArrayList;

/**
 * 中獎線
 */
public class Line {

	/** 圖標 */
	private int symbol;

	/** 連線數 */
	private int connect;

	/** 中獎線位置 */
	private ArrayList<Integer> bingoLineIndexs;

	@Override
	public String toString() {
		return symbol + "X" + connect + bingoLineIndexs.toString();
	}

	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	public int getConnect() {
		return connect;
	}

	public void setConnect(int connect) {
		this.connect = connect;
	}

	public ArrayList<Integer> getBingoLineIndexs() {
		return bingoLineIndexs;
	}

	public void setBingoLineIndexs(ArrayList<Integer> bingoLineIndexs) {
		this.bingoLineIndexs = bingoLineIndexs;
	}

}
