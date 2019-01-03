package com.example.demo.datasource.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.cardgame.Card;
import com.example.demo.cardgame.creature.Race;

@Entity
@Table(name = "creatures")
public class CreatureModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4150517098395664721L;
	
	/** db id */
	private int id;

	/** 種族 */
	private Race race = Race.NONE;

	/** 血量 */
	private int hp;

	/** 預設最大血量 */
	private int defaultMaxHP;

	/** 物攻 */
	private int pPower;

	/** 法攻 */
	private int sPower;

	/** 物防 */
	private int pArmor;

	/** 法防 */
	private int sArmor;

	/** 可使用技能的牌型 */
	private ArrayList<Card> skillCards = new ArrayList<>();

	
	@Id
	@Column(columnDefinition = "int(11) not null auto_increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDefaultMaxHP() {
		return defaultMaxHP;
	}

	public void setDefaultMaxHP(int defaultMaxHP) {
		this.defaultMaxHP = defaultMaxHP;
	}

	public int getpPower() {
		return pPower;
	}

	public void setpPower(int pPower) {
		this.pPower = pPower;
	}

	public int getsPower() {
		return sPower;
	}

	public void setsPower(int sPower) {
		this.sPower = sPower;
	}

	public int getpArmor() {
		return pArmor;
	}

	public void setpArmor(int pArmor) {
		this.pArmor = pArmor;
	}

	public int getsArmor() {
		return sArmor;
	}

	public void setsArmor(int sArmor) {
		this.sArmor = sArmor;
	}

	@Column(columnDefinition = "json NOT NULL")
	public ArrayList<Card> getSkillCards() {
		return skillCards;
	}

	public void setSkillCards(ArrayList<Card> skillCards) {
		this.skillCards = skillCards;
	}

}
