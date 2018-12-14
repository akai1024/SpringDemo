package com.example.demo.controller;

import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.player.Player;
import com.example.demo.shipgame.Combat;

@RestController
@RequestMapping("shipGame")
public class WarshipGameController {

	private static final String CMD_DEPLOY = "D";
	private static final String CMD_FIRE = "F";

	private LinkedList<Combat> waitingRoom = new LinkedList<>();

	private HashMap<Player, Combat> combatMatching = new HashMap<>();

	@RequestMapping("/matching")
	public String matching(HttpSession session) {
		if (!PlayerController.isLogin(session)) {
			return "please login first";
		}

		Player player = PlayerController.getPlayer(session);
		if (combatMatching.containsKey(player)) {
			return "waiting for enemy...";
		}

		// 開一個新局等候挑戰者
		if (waitingRoom.size() <= 0) {
			Combat combat = new Combat();
			combat.addCommander(player);
			waitingRoom.addLast(combat);
			combatMatching.put(player, combat);
			return "waiting for enemy...";
		}

		Combat combat = waitingRoom.getFirst();
		// 如果滿員則將所有指揮官指到同一場
		if (combat.addCommander(player)) {
			for (Player commander : combat.getCommanders()) {
				combatMatching.put(commander, combat);
			}
			// 這裡要測試combatMatching的指標會不會遺失
			waitingRoom.removeFirst();
		}
		return "combat start!";
	}

	@RequestMapping("/command")
	public String command(
			HttpSession session,
			@RequestParam("type") String cmdType,
			@RequestParam("param") String content) {
		if (!PlayerController.isLogin(session)) {
			return "please login first";
		}

		Player player = PlayerController.getPlayer(session);
		Combat combat = combatMatching.get(player);
		if(combat == null) {
			return "please match enemy first";
		}
		
		boolean cmdResult = false;
		switch (cmdType) {
		case CMD_DEPLOY: {
			cmdResult = combat.deploy(player, content);
			break;
		}
		case CMD_FIRE: {
			cmdResult = combat.fire(player, content);
			break;
		}

		default:
			break;
		}
		
		return cmdResult ? "ok" : "fail";
	}

}
