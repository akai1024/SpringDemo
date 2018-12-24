package com.example.demo.cardgame.creature;

/**
 * 種族
 */
public enum Race {

	NONE(1, 1, 1),
	HUMAN(3, 3, 2),
	BEAST(4, 1, 3),
	GOBLIN(3, 2, 3),
	ELF(1, 4, 3)
	
	;

	/** 物理 */
	private int physical;
	/** 精神 */
	private int spirit;
	/** 活力 */
	private int vitality;

	private Race(int physical, int spirit, int vitality) {
		this.physical = physical;
		this.spirit = spirit;
		this.vitality = vitality;
	}

	public int getPhysical() {
		return physical;
	}

	public int getSpirit() {
		return spirit;
	}

	public int getVitality() {
		return vitality;
	}
	
}
