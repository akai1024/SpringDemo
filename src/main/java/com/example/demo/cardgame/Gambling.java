package com.example.demo.cardgame;

import java.util.ArrayList;
import java.util.Random;

public class Gambling {

	private int totalPlayers;

	private int startTeam = -1;

	private ArrayList<String> gamblingPlayers = new ArrayList<>();

	private ArrayList<Card> teamA = new ArrayList<>();

	private ArrayList<Card> teamB = new ArrayList<>();

	public Gambling(int totalPlayers) {
		this.totalPlayers = totalPlayers;
	}

	@Override
	public String toString() {
		return "startTeam=" + startTeam + ", teamA=" + teamA.toString() + ", teamB=" + teamB.toString();
	}

	public void clearCards(int totalPlayers) {
		this.totalPlayers = totalPlayers;
		this.startTeam = -1;

		gamblingPlayers.clear();
		teamA.clear();
		teamB.clear();
	}

	public boolean addGambling(boolean isTeamA, String accID, ArrayList<Card> cards) {
		if (gamblingPlayers.contains(accID)) {
			return false;
		}

		if (isTeamA) {
			teamA.addAll(cards);
		} else {
			teamB.addAll(cards);
		}

		gamblingPlayers.add(accID);
		return true;
	}

	/**
	 * 取得優先行動的隊伍<br>
	 * 目前暫時由賭牌張數決定，還不用比較牌面
	 */
	public int getStartTeam() {
		if (gamblingPlayers.size() == totalPlayers) {
			if (teamA.size() > teamB.size()) {
				startTeam = 0;
			} else if (teamA.size() < teamB.size()) {
				startTeam = 1;
			}
			// 出牌數相等
			else {
				startTeam = new Random().nextInt(2);
			}
		}
		return startTeam;
	}
}
