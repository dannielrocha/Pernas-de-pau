package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;

public class Game {
	Ball bola = new Ball(0, 0, Color.YELLOW);
	Dimension dimensao = new Dimension();
	
	public Game(Dimension dimension) {
		this.dimensao = dimension;
		colorir();
	}
	
	public void horizontalMove(int step){
		if ( (bola.getX() >= 10 && step < 0) || (step > 0 && bola.getX()+10 <= dimensao.getWidth()-10) )
			bola.horizontalMove(step);
		
		colorir();
	}
	
	public void verticalMove(int step){
		if ( (bola.getY() >= 10 && step < 0) || (step > 0 && bola.getY()+10 <= dimensao.getHeight()-10) )
			bola.verticalMove(step);
		
		colorir();
	}
	
	public Ball getBola() {
		return this.bola;
	}
	
	private final void colorir() {
		if (bola.getY() > dimensao.getHeight()/2 && bola.getX() < dimensao.getWidth()/2)
			bola.setColor(Color.BLUE);
		else if (bola.getY() < dimensao.getHeight()/2 && bola.getX() < dimensao.getWidth()/2)
			bola.setColor(Color.GREEN);
		else if (bola.getX() > dimensao.getWidth()/2 && bola.getY() > dimensao.getHeight()/2)
			bola.setColor(Color.RED);
		else bola.setColor(Color.ORANGE);
	}
}
