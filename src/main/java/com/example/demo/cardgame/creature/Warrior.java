package com.example.demo.cardgame.creature;

import com.example.demo.cardgame.CardLaunch;

public class Warrior extends Creature {

	public Warrior() {
		super(Race.HUMAN);
	}

	@Override
	public void launchSkill(CardLaunch launch) {
		
	}

	@Override
	public String toString() {
		return "Warrior";
	}

}
