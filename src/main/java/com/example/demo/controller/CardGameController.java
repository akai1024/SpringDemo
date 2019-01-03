package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CommonUtil;
import com.example.demo.cardgame.Card;
import com.example.demo.cardgame.creature.Race;
import com.example.demo.datasource.model.CreatureModel;
import com.example.demo.datasource.model.PlayerModel;
import com.example.demo.datasource.repo.CreatureRepo;
import com.example.demo.datasource.repo.PlayerRepo;

@RestController
@RequestMapping("/cardGame")
public class CardGameController {

	private static Logger logger = LoggerFactory.getLogger(CardGameController.class);

	@Autowired
	private PlayerRepo playerRepo;
	
	@Autowired
	private CreatureRepo creatureRepo;

	@RequestMapping("/createPlayer")
	public PlayerModel createPlayer(@RequestParam("acc") String acc, @RequestParam("name") String name) {
		if (CommonUtil.isEmptyString(acc) || CommonUtil.isEmptyString(name)) {
			return null;
		}

		// 如果帳號已經存在不能創建
		if (playerRepo.findById(acc).isPresent()) {
			if (logger.isErrorEnabled()) {
				logger.error("acc exist, acc=" + acc);
			}
			return null;
		}

		// 如果名稱已經存在不能創建
		if (playerRepo.findByName(name).isPresent()) {
			if (logger.isErrorEnabled()) {
				logger.error("name exist, name=" + name);
			}
			return null;
		}

		PlayerModel model = new PlayerModel();
		model.setAccount(acc);
		model.setName(name);
		model.setLevel(1);
		model.setExperience(0);
		playerRepo.save(model);
		return model;
	}
	
	@RequestMapping("/createRandomCreature")
	public CreatureModel createCreature() {
		
		Random random = new Random();
		
		CreatureModel model = new CreatureModel();
		
		Race[] races = Race.values();
		model.setRace(races[random.nextInt(races.length)]);
		
		model.setDefaultMaxHP(random.nextInt(50));
		model.setHp(model.getDefaultMaxHP());
		
		model.setpPower(random.nextInt(10));
		model.setsPower(random.nextInt(10));
		
		model.setpArmor(random.nextInt(5));
		model.setsArmor(random.nextInt(5));
		
		int cardSize = random.nextInt(5) + 1;
		ArrayList<Card> sCards = new ArrayList<>();
		Card[] cards = Card.values();
		for(int i = 0; i < cardSize; i++) {
			sCards.add(cards[random.nextInt(cards.length)]);
		}
		model.setSkillCards(sCards);
		
		creatureRepo.save(model);
		return model;
	}
	
	@RequestMapping("findAllCreature")
	public List<CreatureModel> findAllCreature(){
		return creatureRepo.findAll();
	}

}
