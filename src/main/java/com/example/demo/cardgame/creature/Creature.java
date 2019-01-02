package com.example.demo.cardgame.creature;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.cardgame.Card;
import com.example.demo.cardgame.CardLaunch;
import com.example.demo.cardgame.CardRegulation;

/**
 * 生物
 */
public abstract class Creature {

	/** 種族 */
	protected Race race = Race.NONE;

	/** 血量 */
	protected int hp;
	
	/** 預設最大血量 */
	protected int defaultMaxHP;

	/** 物攻 */
	protected int pPower;

	/** 法攻 */
	protected int sPower;

	/** 物防 */
	protected int pArmor;

	/** 法防 */
	protected int sArmor;

	/** 可使用技能的牌型 */
	protected ArrayList<Card> skillCards = new ArrayList<>();

	public Creature(Race race) {
		this.race = race;
	}
	
	/**
	 * 是否完全符合技能用卡
	 */
	public boolean isSkillLaunchable(ArrayList<Card> cards) {
		if (cards.size() <= 0) {
			return false;
		}

		if (cards.size() != skillCards.size()) {
			return false;
		}

		HashMap<Card, Integer> skillNeeds = CardRegulation.getCardSize(skillCards);
		HashMap<Card, Integer> costNeeds = CardRegulation.getCardSize(cards);
		for (Card card : skillNeeds.keySet()) {
			if (!costNeeds.containsKey(card)) {
				return false;
			}
			if (skillNeeds.get(card) != costNeeds.get(card)) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * 使用技能
	 */
	public abstract void launchSkill(CardLaunch launch);
	
	public abstract String toString();

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDefaultMaxHP() {
		return defaultMaxHP;
	}

	public void setDefaultMaxHP(int defaultMaxHP) {
		this.defaultMaxHP = defaultMaxHP;
	}

	public int getpPower() {
		return pPower;
	}

	public void setpPower(int pPower) {
		this.pPower = pPower;
	}

	public int getsPower() {
		return sPower;
	}

	public void setsPower(int sPower) {
		this.sPower = sPower;
	}

	public int getpArmor() {
		return pArmor;
	}

	public void setpArmor(int pArmor) {
		this.pArmor = pArmor;
	}

	public int getsArmor() {
		return sArmor;
	}

	public void setsArmor(int sArmor) {
		this.sArmor = sArmor;
	}

	public ArrayList<Card> getSkillCards() {
		return skillCards;
	}

	public void setSkillCards(ArrayList<Card> skillCards) {
		this.skillCards = skillCards;
	}

}
