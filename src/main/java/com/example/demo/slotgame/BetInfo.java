package com.example.demo.slotgame;

import java.math.BigDecimal;

/**
 * 下注資訊
 */
public class BetInfo {

	/** 注數 */
	private int betCount;
	
	/** 單注代幣 */
	private int singleBetToken;
	
	/** 幣值比 */
	private BigDecimal gainRate = BigDecimal.ONE;

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public int getSingleBetToken() {
		return singleBetToken;
	}

	public void setSingleBetToken(int singleBetToken) {
		this.singleBetToken = singleBetToken;
	}

	public BigDecimal getGainRate() {
		return gainRate;
	}

	public void setGainRate(BigDecimal gainRate) {
		this.gainRate = gainRate;
	}
	
}
