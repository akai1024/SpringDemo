package com.example.demo.cardgame;

import com.example.demo.WeightSelection;

public enum Card {

	/** 物理攻擊 */
	AD(35),
	/** 法術攻擊 */
	AP(35),
	/** 治療 */
	HEAL(15),
	/** 守備 */
	DEF(20),
	/** 星星 */
	STAR(10)
	;
	
	private int weight;
	
	private static WeightSelection<Card> selection = new WeightSelection<>();
	
	static {
		for(Card card : Card.values()) {
			selection.add(card.getWeight(), card);
		}
	}
	
	private Card(int weight) {
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}

	/**
	 * 隨機一種卡片
	 */
	public static Card randomType() {
		return selection.random();
	}

}
