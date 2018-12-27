package com.example.demo.poker;

import java.util.Comparator;

/**
 * 撲克牌
 */
public class PokerCard implements Comparable<PokerCard> {

	private PokerSuit suit;

	private PokerRank rank;

	@Override
	public String toString() {
		return "(" + suit + ")" + rank;
	}

	public PokerCard(PokerSuit suit, PokerRank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public PokerSuit getSuit() {
		return suit;
	}

	public void setSuit(PokerSuit suit) {
		this.suit = suit;
	}

	public PokerRank getRank() {
		return rank;
	}

	public void setRank(PokerRank rank) {
		this.rank = rank;
	}

	/**
	 * 預設的排序方式
	 */
	@Override
	public int compareTo(PokerCard o) {
		if (suit != null && rank != null) {
			// 先比數字
			int cRank = rank.compareOrder(o.rank);
			if (cRank == 0) {
				// 數字相同，比較花色
				return suit.compare(o.suit);
			} else {
				return cRank;
			}
		}
		return 0;
	}

	/**
	 * 以數字為順序的排序方式
	 */
	public static Comparator<PokerCard> getStraightOrder(){
		return new Comparator<PokerCard>() {
			@Override
			public int compare(PokerCard o1, PokerCard o2) {
				if(o1 != null && o2 != null) {
					// 先比數字
					int cRank = o1.rank.compareNumber(o2.rank);
					if (cRank == 0) {
						// 數字相同，比較花色
						return o1.suit.compare(o2.suit);
					} else {
						return cRank;
					}
				}
				return 0;
			}
		};
	}
}
