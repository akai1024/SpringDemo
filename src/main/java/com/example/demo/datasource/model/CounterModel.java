package com.example.demo.datasource.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "counter")
public class CounterModel {

	private int id;

	private int counter;

	@Id
	@Column(columnDefinition = "int(11) auto_increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
