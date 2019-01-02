package com.example.demo.cardgame.creature;

import com.example.demo.cardgame.CardLaunch;

public class Wizzard extends Creature {

	public Wizzard() {
		super(Race.HUMAN);
	}

	@Override
	public void launchSkill(CardLaunch launch) {

	}

	@Override
	public String toString() {
		return "Wizzard";
	}

}
