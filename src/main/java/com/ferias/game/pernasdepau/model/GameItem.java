package com.ferias.game.pernasdepau.model;

import java.awt.Color;

public interface GameItem {
	public int getName();
	public int x();
	public int y();
	public Color getColor();
	public void setColor(int r, int g, int b);
}
