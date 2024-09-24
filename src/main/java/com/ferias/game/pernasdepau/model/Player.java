package com.ferias.game.pernasdepau.model;

import java.awt.Point;
import java.util.List;

public class Player extends AbstractMovebleGameItem {

	private boolean isRunning = false;
	private FieldSide goalBox;
	private int score;
	
	public Player(int name, FieldSide goalBox, Point posicaoInicial) {
		super(name, posicaoInicial);
		this.goalBox = goalBox;
	}

	public void kick(Ball ball) {
		if (this.xAnterior == ball.x())
			ball.verticalMove(y-yAnterior);
		else //if (this.yAnterior == ball.y())
			ball.horizontalMove(x-xAnterior);
		
		this.isRunning = false;
	}

	public Ball hasCollision(List<Ball> balls) {
		if (balls != null && !balls.isEmpty()) {
			for (Ball ball : balls) {
				if (super.hasCollision(ball))
					return ball;
			}
		}
		return null;
	}

	public void run() {
		this.isRunning = !isRunning;
	}
	
	public int stepSize() {
		return isRunning ? size() * 2: size();
	}

	public FieldSide getFieldSide() {
		return goalBox;
	}

	public int getScore() {
		return score;
	}

	public void goalScored() {
		this.score++;
	}
	
	@Override
	public String toString() {
		return "Player "+this.getName()+ " \nPosicao["+this.x+", "+this.y+"], \nPosicaoAnterior: ["+this.xAnterior+", "+this.yAnterior+"],\nEstado[isRunning: "+this.isRunning+", score: "+this.score+", Lado do Campo: " + this.goalBox.name()+"]";
	}
}
