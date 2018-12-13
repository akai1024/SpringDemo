package com.example.demo.codegame;

public class GuessRecord {

	private String guess;

	private String result;

	@Override
	public String toString() {
		return "guess:" + guess + ", result:" + result;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
