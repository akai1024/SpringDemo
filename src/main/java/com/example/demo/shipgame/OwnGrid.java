package com.example.demo.shipgame;

public class OwnGrid extends Grid {

	private boolean isDeployed = false;
	
	private boolean isDestoryed = false;

	public boolean isDeployed() {
		return isDeployed;
	}

	public void setDeployed(boolean isDeployed) {
		this.isDeployed = isDeployed;
	}

	public boolean isDestoryed() {
		return isDestoryed;
	}

	public void setDestoryed(boolean isDestoryed) {
		this.isDestoryed = isDestoryed;
	}
	
}
