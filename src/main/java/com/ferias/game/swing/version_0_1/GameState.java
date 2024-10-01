package com.ferias.game.swing.version_0_1;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	List<Player> players = new ArrayList<Player>();
	List<Prize> prizes = new ArrayList<Prize>();
	
	
	public List<Player> getPlayers(){
		return this.players;
	}
	
	public List<Prize> getPrizes() {
		return prizes;
	}
}
