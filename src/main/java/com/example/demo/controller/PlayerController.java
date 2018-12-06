package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.player.Player;
import com.example.demo.serialize.SerializeManager;

@RestController
@RequestMapping("/player")
public class PlayerController {

	private static final String SESSION_ACC_PARAM = "acc";

	private static final String PLAYERS_SAVE = "players";
	private HashMap<String, Player> players;
	private HashMap<String, Player> playersIndexOfName = new HashMap<>();
	private HashMap<String, HttpSession> loginSessions = new HashMap<>();

	public PlayerController() {
		HashMap<String, Player> playersSave = SerializeManager.read(PLAYERS_SAVE);
		players = (playersSave == null) ? new HashMap<>() : playersSave;

		players.forEach((key, value) -> {
			playersIndexOfName.put(value.getName(), value);
		});
	}

	@Scheduled(fixedDelay = 10000)
	public void save() {
		SerializeManager.save(PLAYERS_SAVE, players);
	}

	@RequestMapping("/list")
	public ArrayList<Player> getPlayerList() {
		return new ArrayList<>(players.values());
	}

	@RequestMapping("/create")
	public Player createPlayer(@RequestParam("acc") String acc, @RequestParam("name") String name,
			HttpServletRequest request) {
		if (acc == null || acc.isEmpty() || players.containsKey(acc)) {
			return null;
		}

		if (name == null || name.isEmpty() || playersIndexOfName.containsKey(name)) {
			return null;
		}

		Player player = new Player();
		player.setAccount(acc);
		player.setName(name);
		player.setLevel(1);

		addPlayer(player);
		login(acc, request);
		return player;
	}

	private void addPlayer(Player player) {
		if (player == null) {
			return;
		}
		players.put(player.getAccount(), player);
		playersIndexOfName.put(player.getName(), player);
	}

	@RequestMapping("/login")
	public Player login(@RequestParam("acc") String acc, HttpServletRequest request) {
		if (acc == null || acc.isEmpty() || request == null) {
			return null;
		}

		Player player = players.get(acc);
		if (player != null) {
			HttpSession session = request.getSession();
			Player playerInSession = (Player) session.getAttribute(SESSION_ACC_PARAM);
			if (playerInSession == null || !player.equals(playerInSession)) {
				session.setAttribute(SESSION_ACC_PARAM, player);
				
				// 如果已經登入過
				if(loginSessions.containsKey(acc)) {
					loginSessions.get(acc).removeAttribute(SESSION_ACC_PARAM);
				}
				loginSessions.put(acc, session);
			} else {
				return null;
			}
		}
		return player;
	}
	
	public static boolean isLogin(HttpSession session) {
		return session.getAttribute(SESSION_ACC_PARAM) != null;
	}
	
	public static Player getPlayer(HttpSession session) {
		return (Player) session.getAttribute(SESSION_ACC_PARAM);
	}

	@RequestMapping("/createRobot")
	public ArrayList<Player> createRobot() {
		String acc = "robot";
		String name = "robotPlayer";
		for(int i = 0; i < 1000; i++) {
			createPlayer(acc + i, name + i, null);
		}
		return getPlayerList();
	}
	
}
