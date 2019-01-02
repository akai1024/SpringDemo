package com.example.demo.cardgame;

import java.util.ArrayList;

import com.example.demo.cardgame.creature.Creature;
import com.example.demo.player.Player;

public class CardTeam {

	private int currentPlayer = 0;

	private int currentCreature = 0;

	private ArrayList<CardPlayer> players = new ArrayList<>();

	private ArrayList<Creature> creatures = new ArrayList<>();

	public String toString() {
		return "Player:" + players.toString() + "/ Creatures:" + creatures.toString();
	}

	public void addPlayer(Player player) {
		players.add(new CardPlayer(player));
	}

	public void addCreature(Creature creature) {
		creatures.add(creature);
	}

	public void clearCreatures() {
		creatures.clear();
	}

	public ArrayList<CardPlayer> getPlayers() {
		return players;
	}

	public CardPlayer getPlayer(Player player) {
		for (CardPlayer cPlayer : players) {
			if (cPlayer.getPlayerAcc().equals(player.getAccount())) {
				return cPlayer;
			}
		}
		return null;
	}

	public boolean isContainsPlayer(Player player) {
		if (player == null) {
			return false;
		}

		return getPlayer(player) != null;
	}

	public boolean isReadyForFight(int creatureSize) {
		return creatures.size() == creatureSize;
	}

	public CardPlayer getNextPlayer() {
		if (players.size() <= 0) {
			return null;
		}

		currentPlayer++;
		if (currentPlayer >= players.size()) {
			currentPlayer = 0;
		}
		return players.get(currentPlayer);
	}

	public Creature getNextCreature() {
		int creatureSize = creatures.size();
		if (creatureSize <= 0) {
			return null;
		}

		int size = 0;
		currentCreature++;
		if (currentCreature >= creatureSize) {
			currentCreature = 0;
		}

		while (creatures.get(currentCreature).getHp() <= 0 && size <= creatureSize) {
			currentCreature++;
			size++;
		}

		if (size >= creatureSize) {
			return null;
		}
		return creatures.get(currentCreature);
	}

}
