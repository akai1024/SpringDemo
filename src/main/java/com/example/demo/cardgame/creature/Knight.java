package com.example.demo.cardgame.creature;

import com.example.demo.cardgame.CardLaunch;

public class Knight extends Creature {

	public Knight() {
		super(Race.HUMAN);
	}

	@Override
	public void launchSkill(CardLaunch launch) {
		
	}

	@Override
	public String toString() {
		return "Knight";
	}

}
