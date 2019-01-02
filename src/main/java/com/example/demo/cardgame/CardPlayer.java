package com.example.demo.cardgame;

import java.util.ArrayList;

import com.example.demo.player.Player;

public class CardPlayer {

	private String playerAcc;

	/**
	 * 手牌(無順序邏輯)
	 */
	private ArrayList<Card> handCards = new ArrayList<>();

	public CardPlayer(Player player) {
		if (player != null) {
			playerAcc = player.getAccount();
		}
	}

	public String toString() {
		return playerAcc + handCards.toString();
	}

	public String getPlayerAcc() {
		return playerAcc;
	}

	/**
	 * 使用卡片(卡片ID)
	 */
	public ArrayList<Card> useCards(ArrayList<Integer> ids) {
		ArrayList<Card> cards = new ArrayList<>();
		if (ids == null || ids.size() <= 0) {
			return cards;
		}

		for (int id : ids) {
			boolean isFound = false;
			for (int idx = 0; idx < handCards.size(); idx++) {
				Card card = handCards.get(idx);
				if (id == card.getId()) {
					isFound = true;
					cards.add(handCards.remove(idx));
					break;
				}
			}

			// 其中一張找不到了
			if (!isFound) {
				// 手牌加回去
				handCards.addAll(cards);
				// 回傳空陣列
				return new ArrayList<>();
			}
		}

		return cards;
	}

	/**
	 * 從牌庫抽牌
	 */
	public void drawCards(CardDeck deck, int size) {
		if (deck == null) {
			return;
		}
		handCards.addAll(deck.draw(size));
	}

}
