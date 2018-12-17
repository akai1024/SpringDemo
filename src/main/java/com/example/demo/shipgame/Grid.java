package com.example.demo.shipgame;

public class Grid {

	private int x;

	private int y;
	
	@Override
	public String toString() {
		return x + "_" + y;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Grid) {
			Grid grid = (Grid) object;
			return this.toString().equals(grid.toString());
		}
		return false;
	}
	
	public void parseByText(String text) {
		if(text == null) {
			return;
		}
		
		String[] coordinate = text.split(":");
		x = Integer.valueOf(coordinate[0]);
		y = Integer.valueOf(coordinate[1]);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
