package com.example.demo.cardgame;

/**
 * 使用卡片的結果
 */
public class CardLaunch {

	private boolean isSuccess = false;

	private boolean isSkill = false;

	private int pDamage;

	private int sDamage;

	private int pDefense;

	private int sDefense;

	private int heal;

	private int extraCards;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isSkill() {
		return isSkill;
	}

	public void setSkill(boolean isSkill) {
		this.isSkill = isSkill;
	}

	public int getpDamage() {
		return pDamage;
	}

	public void setpDamage(int pDamage) {
		this.pDamage = pDamage;
	}

	public int getsDamage() {
		return sDamage;
	}

	public void setsDamage(int sDamage) {
		this.sDamage = sDamage;
	}

	public int getpDefense() {
		return pDefense;
	}

	public void setpDefense(int pDefense) {
		this.pDefense = pDefense;
	}

	public int getsDefense() {
		return sDefense;
	}

	public void setsDefense(int sDefense) {
		this.sDefense = sDefense;
	}

	public int getHeal() {
		return heal;
	}

	public void setHeal(int heal) {
		this.heal = heal;
	}

	public int getExtraCards() {
		return extraCards;
	}

	public void setExtraCards(int extraCards) {
		this.extraCards = extraCards;
	}

}
