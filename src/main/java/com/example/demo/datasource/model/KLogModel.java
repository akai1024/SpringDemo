package com.example.demo.datasource.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "k_log")
public class KLogModel {

	private int msgId;

	private Date eventDate;

	private String kTopic;

	private String kKey;

	private String kMessage;

	private int counter;

	private BigDecimal num;

	@Id
	@Column(columnDefinition = "int(11) auto_increment")
	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	@Column(columnDefinition = "datetime default CURRENT_TIMESTAMP")
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getkTopic() {
		return kTopic;
	}

	public void setkTopic(String kTopic) {
		this.kTopic = kTopic;
	}

	public String getkKey() {
		return kKey;
	}

	public void setkKey(String kKey) {
		this.kKey = kKey;
	}

	public String getkMessage() {
		return kMessage;
	}

	public void setkMessage(String kMessage) {
		this.kMessage = kMessage;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Column(columnDefinition = "decimal(18,4) default 0.0000")
	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

}
