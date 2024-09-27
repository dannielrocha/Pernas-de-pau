package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Game {
	List<Ball> bolas = new ArrayList<Ball>();
	List<Prize> prizes = new ArrayList<Prize>();
	Dimension dimensao = new Dimension();
	
	public Game(Dimension dimension) {
		this.dimensao = dimension;
	}
	
	
	private void horizontalMove(Ball bola, int step){
		if ( (bola.getX() >= 10 && step < 0) || (step > 0 && bola.getX()+10 <= dimensao.getWidth()-10) )
			bola.horizontalMove(step);
		
		colorir(bola);
		SecureRandom rand = new SecureRandom();
		Prize prize = hasCollision(bola);
		if (prize != null) {
			prize.x = rand.nextInt(dimensao.width/10)*10;
			prize.y = rand.nextInt(dimensao.height/10)*10;
		}
	}
	
	private void verticalMove(Ball bola, int step){
		if ( (bola.getY() >= 10 && step < 0) || (step > 0 && bola.getY()+10 <= dimensao.getHeight()-10) )
			bola.verticalMove(step);
		
		colorir(bola);
		SecureRandom rand = new SecureRandom();
		Prize prize = hasCollision(bola);
		if (prize != null) {
			prize.x = rand.nextInt(dimensao.width/10)*10;
			prize.y = rand.nextInt(dimensao.height/10)*10;
		}
	}
	
	public void moveUP(Ball bola) {
		verticalMove(bola, -10);
	}
	
	public void moveDown(Ball bola) {
		verticalMove(bola, 10);
	}
	
	public void moveLeft(Ball bola) {
		horizontalMove(bola, -10);
	}
	
	public void moveRight(Ball bola) {
		horizontalMove(bola, 10);
	}
	public Ball getBola(int IdBola) {
		return this.bolas.get(IdBola);
	}
	
	public List<Ball> getBolas(){
		return this.bolas;
	}
	
	public void newBall() {
		if (bolas.size()<2) {
			Ball bola = new Ball(bolas.size(), 0,0, Color.BLUE);
			bolas.add(bola);
		}
	}
	
	public void newPrize() {
		if(prizes.size()<1) {
			SecureRandom rand = new SecureRandom();
			int xPrize = rand.nextInt(dimensao.width/10)*10;
			int yPrize = rand.nextInt(dimensao.height/10)*10;
			Prize prize = new Prize(prizes.size(), xPrize, yPrize, Color.PINK);
			prizes.add(prize);
		}
	}
	
	private final void colorir(Ball bola) {
		if (bola.getY() > dimensao.getHeight()/2 && bola.getX() < dimensao.getWidth()/2)
			bola.setColor(Color.BLUE);
		else if (bola.getY() < dimensao.getHeight()/2 && bola.getX() < dimensao.getWidth()/2)
			bola.setColor(Color.GREEN);
		else if (bola.getX() > dimensao.getWidth()/2 && bola.getY() > dimensao.getHeight()/2)
			bola.setColor(Color.RED);
		else bola.setColor(Color.ORANGE);
	}

	private Prize hasCollision(Ball bola) {
		for (Prize prize : prizes) {
			if (bola.x == prize.x && bola.y == prize.y) {
				bola.scored();
				return prize;
			}
		}
		return null;
	}
	
	public List<Prize> getPrizes() {
		return prizes;
	}
}
