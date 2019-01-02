package com.example.demo.cardgame;

import java.util.ArrayList;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.cardgame.creature.CreatureMap;
import com.example.demo.player.Player;

/**
 * 對戰
 */
public class Match {

	private static Logger logger = LoggerFactory.getLogger(Match.class);

	private static final int INIT_HAND_CARDS = 5;
	private static final int CREATURE_SIZE = 3;

	private GamePhase phase = GamePhase.MATCHING;

	private CardTeam teamA;

	private CardTeam teamB;

	private Gambling gambling;

	private CardDeck deck = new CardDeck();

	/**
	 * 隊伍回合(0=teamA, 1= teamB)
	 */
	private int teamTurn = 0;

	public Match(WaitingRoom waitingRoom) {
		for (CardTeam team : waitingRoom.getTeams()) {
			addTeam(team);
		}
	}

	private void addTeam(CardTeam team) {
		if (!phase.equals(GamePhase.MATCHING)) {
			return;
		}

		if (teamA == null) {
			teamA = team;
			return;
		}

		if (teamB == null) {
			teamB = team;
			switchPhaseToNext();
		}
	}

	private void switchPhaseToNext() {
		// end of phase
		switch (phase) {
		case MATCHING:
			endOfMatching();
			break;
		case INITIALIZING:
			endOfInitializing();
			break;
		case GAMBLING:
			endOfGambling();
			break;
		case FIGHTING:
			endOfFighting();
			break;
		case CLEANING:
			endOfCleaning();
			break;
		default:
			break;
		}

		// start of phase
		phase = phase.getNext();
		switch (phase) {
		case MATCHING:
			// 因為初始就是在matching中，所以不會進入這個phase
			break;
		case INITIALIZING:
			startOfInitializing();
			break;
		case GAMBLING:
			startOfGambling();
			break;
		case FIGHTING:
			startOfFighting();
			break;
		case CLEANING:
			startOfCleaning();
			break;
		default:
			break;
		}

	}

	private void endOfMatching() {
		println("===== End of Matching =====");
	}

	private void endOfInitializing() {
		println("===== End of Initializing =====");
		println("TeamA>>>" + teamA.toString());
		println("TeamB>>>" + teamB.toString());
	}

	private void endOfGambling() {
		println("===== End of Gambling =====");
		println(gambling.toString());
	}

	private void endOfFighting() {
		println("===== End of Fighting =====");
	}

	private void endOfCleaning() {
		println("===== End of Cleaning =====");
	}

	private void startOfInitializing() {
		println("===== Start of Initializing =====");

		// 初始化牌庫
		deck.initStack();

		// 發牌給玩家
		for (CardPlayer cPlayer : teamA.getPlayers()) {
			cPlayer.drawCards(deck, INIT_HAND_CARDS);
		}
		for (CardPlayer cPlayer : teamB.getPlayers()) {
			cPlayer.drawCards(deck, INIT_HAND_CARDS);
		}

	}

	private void startOfGambling() {
		println("===== Start of Gambling =====");

		int totalPlayers = teamA.getPlayers().size() + teamB.getPlayers().size();
		if (gambling == null) {
			gambling = new Gambling(totalPlayers);
		} else {
			gambling.clearCards(totalPlayers);
		}
	}

	private void startOfFighting() {
		println("===== Start of Fighting =====");
	}

	private void startOfCleaning() {
		println("===== Start of Cleaning =====");
	}

	public void chooseCreature(Player player, ArrayList<Integer> creatureIDs) {
		if (creatureIDs == null || creatureIDs.size() != CREATURE_SIZE) {
			return;
		}

		if (!phase.equals(GamePhase.INITIALIZING)) {
			return;
		}

		println(player + " choose:" + creatureIDs.toString());

		HashSet<Integer> idCheck = new HashSet<>();

		if (teamA.isContainsPlayer(player) && !teamA.isReadyForFight(CREATURE_SIZE)) {
			for (int id : creatureIDs) {
				if (idCheck.contains(id)) {
					teamA.clearCreatures();
					return;
				} else {
					idCheck.add(id);
				}
				teamA.addCreature(CreatureMap.getCreatureInstance(id));
			}
		}

		if (teamB.isContainsPlayer(player) && !teamB.isReadyForFight(CREATURE_SIZE)) {
			for (int id : creatureIDs) {
				if (idCheck.contains(id)) {
					teamB.clearCreatures();
					return;
				} else {
					idCheck.add(id);
				}
				teamB.addCreature(CreatureMap.getCreatureInstance(id));
			}
		}

		if (teamA.isReadyForFight(CREATURE_SIZE) && teamB.isReadyForFight(CREATURE_SIZE)) {
			switchPhaseToNext();
		}
	}

	public void gambling(Player player, ArrayList<Integer> cardIDs) {

		boolean isTeamA = teamA.isContainsPlayer(player);
		boolean isTeamB = teamB.isContainsPlayer(player);

		CardTeam team;
		if (isTeamA && !isTeamB) {
			team = teamA;
		} else if (!isTeamA && isTeamB) {
			team = teamB;
		} else {
			return;
		}

		CardPlayer cPlayer = team.getPlayer(player);
		boolean isSuccess = gambling.addGambling(isTeamA, player.getAccount(), cPlayer.useCards(cardIDs));
		
		if(isSuccess) {
			// 抽牌補齊
			cPlayer.drawCards(deck, cardIDs.size());
			
			// 看看開始的隊伍
			int startTeam = gambling.getStartTeam();
			if(startTeam >= 0) {
				teamTurn = startTeam;
				switchPhaseToNext();
			}			
		}
	}

	private void fightingTurnOver() {
		teamTurn = teamTurn == 0 ? 1 : 0;
	}

	// ==========================================================
	private void println(Object obj) {
		if (logger.isInfoEnabled()) {
			logger.info("[" + phase.name() + "]" + obj);
		}
	}
}
