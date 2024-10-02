package com.ferias.game.swing.version_0_1;

import java.awt.Color;

public abstract class GameElement {
	
	public final static int SIZE = 10;
	protected int x;
	protected int y;
	private Color color;
	protected int ID;
	
	protected GameElement(int ID, int x, int y, Color color) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return this.y;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getID() {
		return this.ID;
	}
}
