package com.example.demo.poker;

/**
 * 牌型
 */
public enum PokerSeries {

	/** 同花順 */
	STRAIGHT_FLUSH(0),

	/** 鐵支 */
	FOUR_OF_A_KIND(1),

	/** 葫蘆 */
	FULL_HOUSE(2),

	/** 同花 */
	FLUSH(3),

	/** 順子 */
	STRAIGHT(4),

	/** 三條 */
	THREE_OF_A_KIND(5),

	/** 兩對 */
	TWO_PAIRS(6),

	/** 一對 */
	ONE_PAIR(7),

	/** 散牌 */
	HIGH_CARD(8);

	private int order;

	private PokerSeries(int order) {
		this.order = order;
	}

	public int compare(PokerSeries series) {
		if (series != null) {
			return -1 * Integer.compare(order, series.order);
		}
		return 0;
	}

}
