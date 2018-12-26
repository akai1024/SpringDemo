package com.example.demo.poker;

/**
 * 撲克牌
 */
public class PokerCard {

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

}
