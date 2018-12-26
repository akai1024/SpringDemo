package com.example.demo.poker;

/**
 * 撲克牌花色
 */
public enum PokerSuit {

	/** 黑桃 */
	SPADES(0, 0),
	/** 紅心 */
	HEARTS(1, 1),
	/** 方塊 */
	DIAMONDS(2, 1),
	/** 梅花 */
	CLUBS(3, 0)
	;

	private static final int COLOR_BLACK = 0;
	private static final int COLOR_RED = 1;

	private int order;

	private int color;

	private PokerSuit(int order, int color) {
		this.order = order;
		this.color = color;
	}
	
	/**
	 * 比較花色大小
	 */
	public int compare(PokerSuit pokerSuit) {
		if(pokerSuit == null) {
			return 0;
		}
		
		if(order < pokerSuit.order) {
			return 1;
		}
		else if(order > pokerSuit.order) {
			return -1;
		}
		
		return 0;
	}
	
	public boolean isBlack() {
		return color == COLOR_BLACK;
	}
	
	public boolean isRed() {
		return color == COLOR_RED;
	}

	public int getOrder() {
		return order;
	}

	public int getColor() {
		return color;
	}
	
}
