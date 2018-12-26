package com.example.demo.poker;

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

}
