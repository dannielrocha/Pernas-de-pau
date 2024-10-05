package com.ferias.game.swing.version_0_1;

import java.io.Serializable;

public enum Comando implements Serializable {
	NEW(0), 
	MOVE_LEFT(0), MOVE_RIGHT(0), MOVE_UP(0), MOVE_DOWN(0),
	MOVE_LEFT_P2(1), MOVE_RIGHT_P2(1), MOVE_UP_P2(1), MOVE_DOWN_P2(1),
	DESCONECTAR(0);

	int playerID;

	Comando(int playerID) {
		this.playerID = playerID;
	}

	public int getPlayerID() {
		return this.playerID;
	}
	
	public Comando getEquivalent() {
		for (Comando c: Comando.values())
			if (c.name().equals(this.name().substring(0, this.name().length()-3)))
				return c;
		return this;
	}
}
