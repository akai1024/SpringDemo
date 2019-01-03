package com.example.demo.datasource.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(
	name = "players",
	indexes = {
			@Index(name = "IDX_NAME", columnList = "name"),
			}
	)
public class PlayerModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9152372259266436047L;

	private String account;

	private String name;

	private int level;

	private int experience;

	@Id
	@Column(columnDefinition = "varchar(32) NOT NULL")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(columnDefinition = "varchar(32) NOT NULL", unique = true)
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

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

}
