package com.ferias.game.pernasdepau.model;

import java.awt.Dimension;
import java.awt.Point;

public class Ball extends AbstractMovebleGameItem {

	public Ball(int name, Point posicaoInicial) {
		super(name, posicaoInicial);
	}

	public Ball(int name) {
		super(name, new Point(0,0));
	}

	public Ball toFieldCenter(Dimension limites) {
		this.x = limites.width/2-SIZE/2;
		this.y = limites.height/2-SIZE/2;
//		int step = 40+limites.height/2;
//		verticalMove((-y/Math.abs(y)) * step);
//		this.x = limites.width/2;
		return this;
	}
	
	public FieldSide isInsideGoalBox(Dimension limites) {
		if (this.y < 0 ) {
			return FieldSide.UPSIDE;
		} else if (this.y + this.size() > limites.height) {
			return FieldSide.DOWNSIDE;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Ball [" +this.x +", "+ this.y + "] ("+ super.toString()+")";
	}
}
