package com.example.demo.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 撲克牌判斷邏輯
 */
public class PokerJudgement {

	private PokerSeries series;

	private ArrayList<PokerCard> orderCards;

	@Override
	public String toString() {
		return series + orderCards.toString();
	}

	public PokerJudgement(ArrayList<PokerCard> cards) {
		series = PokerSeries.HIGH_CARD;
		orderCards = new ArrayList<>();

		ArrayList<PokerCard> copyCards = new ArrayList<>(cards);

		// 先排序
		Collections.sort(copyCards);

		if (checkStraightFlush(copyCards)) {
			series = PokerSeries.STRAIGHT_FLUSH;
			return;
		}

		if (checkSizeOfOneKind(copyCards, 4)) {
			series = PokerSeries.FOUR_OF_A_KIND;
			return;
		}

		if (checkSizeOfTwoKind(copyCards, 3, 2)) {
			series = PokerSeries.FULL_HOUSE;
			return;
		}

		if (checkFlush(copyCards)) {
			series = PokerSeries.FLUSH;
			return;
		}

		if (checkStraight(copyCards)) {
			series = PokerSeries.STRAIGHT;
			return;
		}

		if (checkSizeOfOneKind(copyCards, 3)) {
			series = PokerSeries.THREE_OF_A_KIND;
			return;
		}

		if (checkSizeOfTwoKind(copyCards, 2, 2)) {
			series = PokerSeries.TWO_PAIRS;
			return;
		}

		if (checkSizeOfOneKind(copyCards, 2)) {
			series = PokerSeries.ONE_PAIR;
			return;
		}

		// 散牌，反序加入大小牌
		for (int idx = copyCards.size() - 1; idx >= 0; idx--) {
			orderCards.add(copyCards.get(idx));
		}
	}

	private boolean checkStraightFlush(ArrayList<PokerCard> cards) {
		if (cards.size() < 5) {
			return false;
		}

		PokerRank curRank = null;
		PokerSuit firstSuit = null;
		for (int idx = 0; idx < cards.size(); idx++) {
			PokerCard poker = cards.get(idx);
			PokerRank pRank = poker.getRank();
			PokerSuit pSuit = poker.getSuit();
			// 第一張
			if (idx == 0) {
				curRank = pRank;
				firstSuit = pSuit;
			} else {
				// 花色不同
				if (!firstSuit.equals(pSuit)) {
					return false;
				}

				// 是否是下一張
				if (!pRank.equals(curRank.getNexRank())) {
					return false;
				} else {
					curRank = pRank;
				}
			}
		}

		// 反序加入大小牌
		for (int idx = cards.size() - 1; idx >= 0; idx--) {
			orderCards.add(cards.get(idx));
		}

		return true;
	}

	private boolean checkSizeOfOneKind(ArrayList<PokerCard> cards, int sizeOf) {
		if (cards.size() < sizeOf) {
			return false;
		}

		HashMap<PokerRank, Integer> counter = getRankCounter(cards);

		PokerRank sizeRank = null;
		for (Entry<PokerRank, Integer> entry : counter.entrySet()) {
			if (entry.getValue() >= sizeOf) {
				sizeRank = entry.getKey();
				break;
			}
		}

		if (sizeRank != null) {
			// 反序加入大小牌
			ArrayList<PokerCard> otherCards = new ArrayList<>();
			for (int idx = cards.size() - 1; idx >= 0; idx--) {
				PokerCard card = cards.get(idx);
				if (card.getRank().equals(sizeRank)) {
					orderCards.add(card);
				} else {
					otherCards.add(card);
				}
			}

			orderCards.addAll(otherCards);
			return true;
		}

		return false;
	}

	private boolean checkSizeOfTwoKind(ArrayList<PokerCard> cards, int size1, int size2) {
		HashMap<PokerRank, Integer> counter = getRankCounter(cards);

		PokerRank sizeRank1 = null;
		PokerRank sizeRank2 = null;
		for (Entry<PokerRank, Integer> entry : counter.entrySet()) {
			if (sizeRank1 == null && entry.getValue() >= size1) {
				sizeRank1 = entry.getKey();
				continue;
			}
			if (sizeRank2 == null && entry.getValue() >= size2) {
				sizeRank2 = entry.getKey();
				continue;
			}
		}

		if (sizeRank1 != null && sizeRank2 != null) {
			boolean isSize1Bigger = false;
			if (size1 > size2) {
				isSize1Bigger = true;
			} else if (size1 == size2) {
				isSize1Bigger = sizeRank1.compareOrder(sizeRank2) > 0;
			}

			ArrayList<PokerCard> biggerSizeCards = new ArrayList<>();
			ArrayList<PokerCard> smallerSizeCards = new ArrayList<>();
			ArrayList<PokerCard> otherCards = new ArrayList<>();
			// 反序加入大小牌
			for (int idx = cards.size() - 1; idx >= 0; idx--) {
				PokerCard card = cards.get(idx);
				if (card.getRank().equals(sizeRank1)) {
					if (isSize1Bigger) {
						biggerSizeCards.add(card);
					} else {
						smallerSizeCards.add(card);
					}
				} else if (card.getRank().equals(sizeRank2)) {
					if (isSize1Bigger) {
						smallerSizeCards.add(card);
					} else {
						biggerSizeCards.add(card);
					}
				} else {
					otherCards.add(card);
				}
			}

			orderCards.addAll(biggerSizeCards);
			orderCards.addAll(smallerSizeCards);
			orderCards.addAll(otherCards);
			return true;
		}

		return false;
	}

	private static HashMap<PokerRank, Integer> getRankCounter(ArrayList<PokerCard> cards) {
		HashMap<PokerRank, Integer> counter = new HashMap<>();
		for (PokerCard card : cards) {
			PokerRank rank = card.getRank();
			int count = counter.containsKey(rank) ? counter.get(rank) : 0;
			count++;
			counter.put(rank, count);
		}
		return counter;
	}

	private boolean checkFlush(ArrayList<PokerCard> cards) {
		PokerSuit firstSuit = null;
		for (int idx = 0; idx < cards.size(); idx++) {
			PokerSuit pSuit = cards.get(idx).getSuit();
			if (idx == 0) {
				firstSuit = pSuit;
			} else if (!firstSuit.equals(pSuit)) {
				return false;
			}
		}

		// 反序加入大小牌
		for (int idx = cards.size() - 1; idx >= 0; idx--) {
			orderCards.add(cards.get(idx));
		}
		return true;
	}

	private boolean checkStraight(ArrayList<PokerCard> cards) {
		PokerRank curRank = null;
		for (int idx = 0; idx < cards.size(); idx++) {
			PokerCard poker = cards.get(idx);
			PokerRank pRank = poker.getRank();
			// 第一張
			if (idx == 0) {
				curRank = pRank;
			} else {
				// 是否是下一張
				if (!pRank.equals(curRank.getNexRank())) {
					return false;
				} else {
					curRank = pRank;
				}
			}
		}

		// 反序加入大小牌
		for (int idx = cards.size() - 1; idx >= 0; idx--) {
			orderCards.add(cards.get(idx));
		}
		return true;
	}

}
