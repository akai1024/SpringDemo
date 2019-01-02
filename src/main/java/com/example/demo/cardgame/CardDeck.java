package com.example.demo.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class CardDeck {

	private static final int GEN_CARDS = 50;

	/** 牌庫 */
	private LinkedList<Card> stack = new LinkedList<>();

	public String toString() {
		return stack.toString();
	}
	
	/**
	 * 初始化牌庫
	 */
	public void initStack() {
		stack.clear();
		checkAndGenCards(0);
	}

	/**
	 * 產生卡片
	 */
	private void checkAndGenCards(int needs) {
		if (stack.size() > needs || needs < 0) {
			return;
		}

		int fillCards = GEN_CARDS + needs;
		while (fillCards > 0) {
			stack.add(Card.randomType());
			fillCards--;
		}
	}

	/**
	 * 抽卡
	 */
	public ArrayList<Card> draw(int size) {
		ArrayList<Card> cards = new ArrayList<>();
		if (size <= 0) {
			return cards;
		}

		checkAndGenCards(size);

		for (int i = 0; i < size; i++) {
			cards.add(stack.removeFirst());
		}

		return cards;
	}

	/**
	 * 打亂牌庫
	 */
	public void shuffle() {
		Collections.shuffle(stack);
	}

}
