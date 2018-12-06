package com.example.demo.player;

import java.io.Serializable;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5737950242405119638L;
	
	private String account;
	
	private String name;
	
	private int level;

	
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Player) {
			return this.account.equals(((Player)object).getAccount());
		}
		return false;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
