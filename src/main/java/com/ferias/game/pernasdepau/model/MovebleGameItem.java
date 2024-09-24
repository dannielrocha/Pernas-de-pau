package com.ferias.game.pernasdepau.model;

public interface MovebleGameItem extends GameItem{	
	public static final int SIZE = 10;
	
	public void verticalMove(int y);
	
	public void horizontalMove(int x);

	public boolean hasCollision(MovebleGameItem item);
}
