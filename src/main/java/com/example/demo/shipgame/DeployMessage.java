package com.example.demo.shipgame;

import java.util.ArrayList;

public class DeployMessage {

	private ArrayList<Grid> deployment;

	public void parseByText(String text) {
		if (text == null) {
			return;
		}

		deployment = new ArrayList<>();
		String[] grids = text.split(",");
		for (String grid : grids) {
			Grid realGrid = new Grid();
			realGrid.parseByText(grid);
			deployment.add(realGrid);
		}
	}

	public ArrayList<Grid> getDeployment() {
		return deployment;
	}

	public void setDeployment(ArrayList<Grid> deployment) {
		this.deployment = deployment;
	}

}
