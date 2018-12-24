package com.example.demo.cardgame;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.cardgame.creature.Creature;
import com.example.demo.cardgame.creature.Race;

/**
 * 各種運用牌的規則檢查
 */
public class CardRegulation {

	public static HashMap<Card, Integer> getCardSize(ArrayList<Card> cards) {
		HashMap<Card, Integer> cardMap = new HashMap<>();
		if (cards == null || cards.size() <= 0) {
			return cardMap;
		}

		for (Card card : cards) {
			int count = cardMap.containsKey(card) ? cardMap.get(card) : 0;
			count++;
			cardMap.put(card, count);
		}

		return cardMap;
	}

	public static CardLaunch launchCards(Creature creature, ArrayList<Card> cards) {
		CardLaunch launch = new CardLaunch();
		if (creature == null || cards == null || cards.size() <= 0) {
			return launch;
		}

		// 使用技能組
		if (creature.isSkillLaunchable(cards)) {
			launch.setSkill(true);
			creature.launchSkill(launch);
		} else {
			HashMap<Card, Integer> cardMap = getCardSize(cards);
			// 超過兩種卡，絕對非法
			if (cardMap.size() > 2) {
				return launch;
			}
			// 超過一種卡，檢查是不是使用star
			else if (cardMap.size() > 1) {
				if (!cardMap.containsKey(Card.STAR)) {
					return launch;
				}
			}

			// 計算一般用卡
			cardsLaunch(creature, launch, cardMap);
		}
		
		launch.setSuccess(true);
		return launch;
	}

	private static void cardsLaunch(Creature creature, CardLaunch launch, HashMap<Card, Integer> cardMap) {

		Race race = creature.getRace();
		
		if (cardMap.containsKey(Card.AD)) {
			int ads = cardMap.get(Card.AD);
			int damage = creature.getpPower() + ads * race.getPhysical();
			launch.setpDamage(damage);
		}

		if (cardMap.containsKey(Card.AP)) {
			int aps = cardMap.get(Card.AP);
			int damage = creature.getsPower() + aps * race.getSpirit();
			launch.setsDamage(damage);
		}

		if (cardMap.containsKey(Card.DEF)) {
			int defs = cardMap.get(Card.DEF);
			int pDefense = creature.getpArmor() + defs * race.getVitality();
			int sDefense = creature.getsArmor() + defs * race.getVitality();
			launch.setpDefense(pDefense);
			launch.setsDefense(sDefense);
		}

		if (cardMap.containsKey(Card.HEAL)) {
			int heal = cardMap.get(Card.HEAL) + race.getVitality();
			launch.setHeal(heal);
		}

		if (cardMap.containsKey(Card.STAR)) {
			int stars = cardMap.get(Card.STAR);
			launch.setExtraCards(stars);
		}

	}

}
