package com.example.demo.codegame;

import java.io.Serializable;

public class CodeGameRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1452239692366507285L;

	private String code;

	private int guessTimes;

	private boolean isSuccess;

	@Override
	public String toString() {
		if (isSuccess) {
			return "(" + code + "/" + guessTimes + "/SUCCESS)";
		}
		return "(" + code + "/" + guessTimes + "/FAIL)";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getGuessTimes() {
		return guessTimes;
	}

	public void setGuessTimes(int guessTimes) {
		this.guessTimes = guessTimes;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
