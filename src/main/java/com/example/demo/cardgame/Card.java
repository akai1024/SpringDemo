package com.example.demo.cardgame;

import com.example.demo.WeightSelection;

public enum Card {

	/** 物理攻擊 */
	AD(10, 35),
	/** 法術攻擊 */
	AP(20, 35),
	/** 治療 */
	HEAL(30, 15),
	/** 守備 */
	DEF(40, 20),
	/** 星星 */
	STAR(50, 10)
	;
	
	private int id;
	private int weight;
	
	private static WeightSelection<Card> selection = new WeightSelection<>();
	
	static {
		for(Card card : Card.values()) {
			selection.add(card.getWeight(), card);
		}
	}
	
	private Card(int id,int weight) {
		this.id = id;
		this.weight = weight;
	}
	
	public int getId() {
		return id;
	}

	public int getWeight() {
		return weight;
	}
	
	public String toString() {
		return name() + "(" + id + ")";
	}

	/**
	 * 隨機一種卡片
	 */
	public static Card randomType() {
		return selection.random();
	}

}
