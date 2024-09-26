package com.ferias.game.swing.version_0_1;

import java.awt.Color;

public class Ball {
	private int x;
	private int y;
	private Color color;

	public Ball(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void horizontalMove(int xPos) {
		this.x += xPos;
	}

	public int getY() {
		return this.y;
	}

	public void verticalMove(int yPos) {
		this.y += yPos;
	}
	
	public Color getColor() {
		return this.color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
