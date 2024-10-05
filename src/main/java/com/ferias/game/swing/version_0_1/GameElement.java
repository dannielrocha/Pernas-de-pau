package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

public abstract class GameElement implements Serializable {
	private static final long serialVersionUID = 1L;
	
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

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameElement other = (GameElement) obj;
		return ID == other.ID;
	}
	
	
}
