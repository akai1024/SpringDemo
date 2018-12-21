package com.example.demo.controller.slotgame;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.PlayerController;
import com.example.demo.player.LevelCalculator;
import com.example.demo.player.Player;
import com.example.demo.slotgame.BetInfo;
import com.example.demo.slotgame.ScoreInfo;
import com.example.demo.slotgame.SlotGameA;

@RestController
@RequestMapping("/slot/gameA")
public class SlotAController {

	private static final int MAX_BET_COUNT = 20;
	private static final int SINGLE_BET_TOKEN = 10;

	private SlotGameA slotGameA = new SlotGameA();

	@RequestMapping("/spin")
	public ScoreInfo playerSpin(HttpSession session, @RequestParam("betCount") int betCount) {
		if (betCount <= 0 || betCount > MAX_BET_COUNT) {
			return null;
		}
		
		Player player = PlayerController.getPlayer(session);
		if(player == null) {
			return null;
		}
		
		BetInfo betInfo = new BetInfo();
		betInfo.setBetCount(betCount);
		betInfo.setSingleBetToken(SINGLE_BET_TOKEN);
		
		// 加經驗
		LevelCalculator.addPlayerExp(player, betInfo.getTotalToken());
		
		return slotGameA.spin(betInfo);
	}

}
