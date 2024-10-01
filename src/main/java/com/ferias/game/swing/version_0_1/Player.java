package com.ferias.game.swing.version_0_1;

import java.awt.Color;

public class Player extends GameElement {

	private int score;

	public Player(int ID, int x, int y, Color color) {
		super(ID, x, y, color);
	}
	
	public void horizontalMove(int xPos) {
		this.x += xPos;
	}
	
	public void verticalMove(int yPos) {
		this.y += yPos;
	}

	public int getScore() {
		return this.score;
	}
	
	public void scored() {
		this.score++;
	}
}
