package com.example.demo.chat;

import java.io.Serializable;
import java.util.Date;

import com.example.demo.CommonUtil;

/**
 * 聊天資訊結構
 * 
 * @author kai
 *
 */
public class ChatMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4806524034252957613L;

	private String time;

	private String name;

	private String msg;

	public ChatMessage(Date date, String name, String msg) {
		this.time = (date == null) ? "unknown time" : CommonUtil.dateToSimpleString(date);
		this.name = name;
		this.msg = msg;
	}

	public String toString() {
		return "(" + time + ")" + name + ": " + msg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
