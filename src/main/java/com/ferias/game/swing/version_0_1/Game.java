package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;

public class Game {
	Ball bola = new Ball(0, 0, Color.YELLOW);
	Dimension dimensao = new Dimension();
	
	public Game(Dimension dimension) {
		this.dimensao = dimension;
		colorify();
	}
	
	public void horizontalMove(int step){
		if (step < 0 && (bola.getX() > 0))
			bola.horizontalMove(step);
		else if (((bola.getX() + 10) < dimensao.getWidth()))
			bola.horizontalMove(step);
		
		colorify();
	}
	
	public void verticalMove(int step){
		if (step < 0 && bola.getY() > 0)
			bola.verticalMove(step);
		else if ((bola.getY()+10) < dimensao.getHeight())
			bola.verticalMove(step);
		
		colorify();
	}
	
	public Ball getBola() {
		return this.bola;
	}
	
	private final void colorify() {
		if (bola.getY() > dimensao.getHeight()/2 && bola.getX() < dimensao.getWidth()/2)
			bola.setColor(Color.BLUE);
		else if (bola.getY() < dimensao.getHeight()/2 && bola.getX() < dimensao.getWidth()/2)
			bola.setColor(Color.GREEN);
		else if (bola.getX() > dimensao.getWidth()/2 && bola.getY() > dimensao.getHeight()/2)
			bola.setColor(Color.RED);
		else bola.setColor(Color.ORANGE);
	}
}
