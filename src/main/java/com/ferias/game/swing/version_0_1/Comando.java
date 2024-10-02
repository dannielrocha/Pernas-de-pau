package com.ferias.game.swing.version_0_1;

public enum Comando {
	NEW(null), MOVE_LEFT(null), MOVE_RIGHT(null), MOVE_UP(null), MOVE_DOWN(null),
	MOVE_LEFT_P1(0), MOVE_RIGHT_P1(0), MOVE_UP_P1(0), MOVE_DOWN_P1(0),
	MOVE_LEFT_P2(1), MOVE_RIGHT_P2(1), MOVE_UP_P2(1), MOVE_DOWN_P2(1);

	private Integer playerID;

	private Comando(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return this.playerID;
	}

	public Comando getEquivalente() {
		for (Comando comando: Comando.values()) {
			if (comando.name().equals(this.name().substring(0, this.name().length()-3))) {
				return comando;
			}
		}
		return this;
	}
}
