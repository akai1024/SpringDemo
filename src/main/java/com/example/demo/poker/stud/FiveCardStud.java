package com.example.demo.poker.stud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import com.example.demo.player.Player;
import com.example.demo.poker.PokerCard;
import com.example.demo.poker.PokerCardStack;
import com.example.demo.poker.PokerJudgement;

/**
 * 梭哈
 */
public class FiveCardStud {

	private static final int STAGES = 3;
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 4;
	private static final int FIRST_STAGE_CARDS = 3;
	private static final int SECOND_STAGE_CARDS = 5;
	private static final int THIRD_STAGE_CARDS = 5;

	/** 牌庫 */
	private PokerCardStack stack = new PokerCardStack();
	ArrayList<Integer> rndPick = new ArrayList<>();

	private HashMap<Player, PokerCardStack> players = new HashMap<>();
	private HashMap<Player, ArrayList<ArrayList<PokerCard>>> arrangedCards = new HashMap<>();
	private HashMap<Player, Integer> scores = new HashMap<>();

	public FiveCardStud() {
		for (int i = 0; i < MAX_PLAYERS; i++) {
			rndPick.add(i);
		}
	}

	public void addPlayer(Player player) {
		if (player != null && players.size() < MAX_PLAYERS) {
			players.put(player, new PokerCardStack());
		}
	}

	/**
	 * 新的一局
	 */
	public void newRound() {
		stack.newPackageCards();
		stack.shuffle();

		arrangedCards.clear();
		scores.clear();

		// 發卡片
		deliverCards();
	}

	/**
	 * 配發卡片給所有玩家
	 */
	private void deliverCards() {
		ArrayList<ArrayList<PokerCard>> pCards = stack.separateToParts(MAX_PLAYERS);
		Collections.shuffle(rndPick);

		int idxOfPick = 0;
		for (PokerCardStack stack : players.values()) {
			stack.addCards(pCards.get(rndPick.get(idxOfPick)));
			idxOfPick++;
		}
	}

	/**
	 * 玩家安排自己的牌序
	 */
	public void arrangeCards(Player player, ArrayList<ArrayList<Integer>> indexes) {
		if (player == null || indexes == null || indexes.size() != STAGES) {
			return;
		}

		if (arrangedCards.containsKey(player) || !players.containsKey(player)) {
			return;
		}

		PokerCardStack stack = players.get(player);
		HashSet<Integer> arrangedIndex = new HashSet<>();
		ArrayList<ArrayList<PokerCard>> stageCards = new ArrayList<>();

		for (int stage = 0; stage < STAGES; stage++) {
			ArrayList<Integer> idxs = indexes.get(stage);
			int idxSize = idxs.size();
			if (stage == 0 && idxSize != FIRST_STAGE_CARDS) {
				return;
			} else if (stage == 1 && idxSize != SECOND_STAGE_CARDS) {
				return;
			} else if (stage == 2 && idxSize != THIRD_STAGE_CARDS) {
				return;
			} else if (stage >= indexes.size()) {
				return;
			}

			ArrayList<PokerCard> cards = new ArrayList<>();
			for (int idx : idxs) {
				if (arrangedIndex.contains(idx)) {
					return;
				} else {
					arrangedIndex.add(idx);
				}

				PokerCard card = stack.get(idx);
				if(card == null) {
					return;
				}
				cards.add(card);
			}
			stageCards.add(cards);
		}

		arrangedCards.put(player, stageCards);
	}
	
	/**
	 * 自動安排所有玩家的牌序
	 */
	public void autoArrangeAllPlayersCards() {
		for(Player player : players.keySet()) {
			autoArrangeCards(player);
		}
	}
	
	/**
	 * 自動安排玩家牌序
	 */
	private void autoArrangeCards(Player player) {
		if (player == null || arrangedCards.containsKey(player) || !players.containsKey(player)) {
			return;
		}
		
		PokerCardStack stack = players.get(player);
		ArrayList<ArrayList<PokerCard>> stageCards = new ArrayList<>();
		stageCards.add(stack.drawCards(FIRST_STAGE_CARDS));
		stageCards.add(stack.drawCards(SECOND_STAGE_CARDS));
		stageCards.add(stack.drawCards(THIRD_STAGE_CARDS));
		arrangedCards.put(player, stageCards);
	}

	/**
	 * 牌局開始
	 */
	public void start() {
		for(int stage = 0; stage < STAGES; stage++) {
			startStage(stage);
		}
	}
	
	/**
	 * 開始一個回合
	 */
	private void startStage(int stage) {
		if(stage >= STAGES || players.size() < MIN_PLAYERS) {
			return;
		}
		
		for(Player roundPlayer : arrangedCards.keySet()) {
			int score = 0;
			// 自己該回合的牌
			ArrayList<PokerCard> rPlayerCards = arrangedCards.get(roundPlayer).get(stage);
			PokerJudgement rJudge = new PokerJudgement(rPlayerCards);
			for(Player opponentPlayer : arrangedCards.keySet()) {
				if(opponentPlayer.equals(roundPlayer)) {
					continue;
				}
				// 對手該回合的牌
				ArrayList<PokerCard> oPlayerCards = arrangedCards.get(opponentPlayer).get(stage);
				PokerJudgement oJudge = new PokerJudgement(oPlayerCards);
				if(rJudge.compareTo(oJudge) > 0) {
					score++;
				}
			}
			
			// 加分
			addScore(roundPlayer, score);
		}
	}
	
	/**
	 * 增加玩家得分(可以為零分)
	 */
	private void addScore(Player player, int score) {
		if (player != null && players.containsKey(player)) {
			int totalScore = scores.containsKey(player) ? scores.get(player) : 0;
			totalScore += score;
			scores.put(player, totalScore);
		}
	}
	
	/**
	 * 印出結果
	 */
	public void printResult() {
		System.out.println(arrangedCards);
		System.out.println(scores);
		System.out.println(stack.getRestCards());
	}

}
