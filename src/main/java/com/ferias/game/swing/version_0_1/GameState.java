package com.ferias.game.swing.version_0_1;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Map<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	Map<Integer, Prize> prizes = new ConcurrentHashMap<Integer, Prize>();
	
	
	public Map<Integer, Player> getPlayers(){
		return this.players;
	}
	
	public Map<Integer, Prize> getPrizes() {
		return prizes;
	}
}
