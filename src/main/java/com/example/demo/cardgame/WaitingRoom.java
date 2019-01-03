package com.example.demo.cardgame;

import java.util.ArrayList;
import java.util.Collections;

import com.example.demo.player.Player;

public class WaitingRoom {

	private int teamMembers;

	private ArrayList<Player> hosts;

	private ArrayList<Player> guests = new ArrayList<>();

	public WaitingRoom(ArrayList<Player> players) {
		this.teamMembers = players.size();
		this.hosts = players;
	}

	public void addGuest(Player player) {
		if (!guests.contains(player) && teamMembers > guests.size()) {
			guests.add(player);
		}
	}

	public boolean addGuests(ArrayList<Player> players) {
		if(guests.size() + players.size() > teamMembers) {
			return false;
		}
		
		for (Player player : players) {
			addGuest(player);
		}
		return true;
	}
	
	public boolean isFullMembers() {
		return hosts.size() == guests.size();
	}
	
	public ArrayList<CardTeam> getTeams(){
		ArrayList<CardTeam> teams = new ArrayList<>();
		
		CardTeam teamA = new CardTeam();
		for(Player player : hosts) {
			teamA.addPlayer(player);
		}
		teams.add(teamA);
		
		CardTeam teamB = new CardTeam();
		for(Player player : guests) {
			teamB.addPlayer(player);
		}
		teams.add(teamB);
		
		Collections.shuffle(teams);
		return teams;
	}

}
