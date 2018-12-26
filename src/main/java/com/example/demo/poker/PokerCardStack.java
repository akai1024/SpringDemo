package com.example.demo.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 撲克牌堆
 */
public class PokerCardStack {

	private LinkedList<PokerCard> cards = new LinkedList<>();

	public PokerCardStack() {

	}

	public PokerCardStack(List<PokerCard> pokerCards) {
		if (pokerCards != null) {
			cards.addAll(pokerCards);
		}
	}

	@Override
	public String toString() {
		return cards.toString();
	}

	public void newPackageCards() {
		cards.clear();
		for (PokerSuit suit : PokerSuit.values()) {
			for (PokerRank rank : PokerRank.values()) {
				cards.add(new PokerCard(suit, rank));
			}
		}
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public int size() {
		return cards.size();
	}

	public ArrayList<ArrayList<PokerCard>> separateToParts(int parts) {
		if (parts <= 0) {
			return null;
		}

		ArrayList<ArrayList<PokerCard>> partCards = new ArrayList<>();
		for (int pt = 0; pt < parts; pt++) {
			partCards.add(new ArrayList<>());
		}

		int index = 0;
		while (cards.size() > 0) {
			ArrayList<PokerCard> singlePart = partCards.get(index);
			singlePart.add(cards.removeFirst());
			index++;
			if (index >= parts) {
				index = 0;
			}
		}

		return partCards;
	}

	public ArrayList<PokerCard> drawCards(int size) {
		ArrayList<PokerCard> drawCards = new ArrayList<>();
		if (size > cards.size() || size <= 0) {
			return drawCards;
		}

		for (int i = 0; i < size; i++) {
			drawCards.add(cards.removeFirst());
		}
		return drawCards;
	}

}
