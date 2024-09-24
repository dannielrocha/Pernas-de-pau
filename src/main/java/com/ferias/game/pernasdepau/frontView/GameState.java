package com.ferias.game.pernasdepau.frontView;

import java.util.List;

import com.ferias.game.pernasdepau.model.Ball;
import com.ferias.game.pernasdepau.model.Player;

public class GameState {
	private List<Player> players;
	private List<Ball> balls;
	
	public GameState(List<Player> players, List<Ball> balls) {
		this.players = players;
		this.balls = balls;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public List<Ball> getBalls() {
		return this.balls;
	}
}
