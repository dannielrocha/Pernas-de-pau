package com.ferias.game.pernasdepau.model;

public enum FieldSide {
	UPSIDE, DOWNSIDE;

	boolean isGoodGoal(int y) {
		return (this.equals(UPSIDE) && y < 0) || (this.equals(DOWNSIDE) && y > 0);
	}
}
