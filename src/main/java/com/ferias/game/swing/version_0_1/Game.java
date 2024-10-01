package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Game {
	List<Observer> observers = new ArrayList<Observer>();
	GameState gameState = new GameState();
	Dimension dimensao = new Dimension();
	
	public Game(Dimension dimension) {
		this.dimensao = dimension;
	}
	
	
	private void horizontalMove(Player player, int step){
		//executa uma regra
		if ( (player.getX() >= 10 && step < 0) || (step > 0 && player.getX()+10 <= dimensao.getWidth()-10) )
			player.horizontalMove(step);
		
		//executa uma regra
		colorir(player);
		
		//executa uma regra
		SecureRandom rand = new SecureRandom();
		Prize prize = hasCollision(player);
		if (prize != null) {
			prize.x = rand.nextInt(dimensao.width/10)*10;
			prize.y = rand.nextInt(dimensao.height/10)*10;
		}
		
		updateAll();
	}
	
	private void verticalMove(Player player, int step){
		//executa uma regra
		if ( (player.getY() >= 10 && step < 0) || (step > 0 && player.getY()+10 <= dimensao.getHeight()-10) )
			player.verticalMove(step);
		
		//executa uma regra
		colorir(player);
		
		//executa uma regra
		SecureRandom rand = new SecureRandom();
		Prize prize = hasCollision(player);
		if (prize != null) {
			prize.x = rand.nextInt(dimensao.width/10)*10;
			prize.y = rand.nextInt(dimensao.height/10)*10;
		}
		
		updateAll();
	}
	
	public void moveUP(Player player) {
		verticalMove(player, -10);
	}
	
	public void moveDown(Player player) {
		verticalMove(player, 10);
	}
	
	public void moveLeft(Player player) {
		horizontalMove(player, -10);
	}
	
	public void moveRight(Player player) {
		horizontalMove(player, 10);
	}
	
	public Player getPlayer(int idPlayer) {
		return gameState.getPlayers().get(idPlayer);
	}

	
	public void newPlayer() {
		if (gameState.getPlayers().size()<2) {
			Player player = new Player(gameState.getPlayers().size(), 0,0, Color.BLUE);
			gameState.getPlayers().add(player);
		}
		
		updateAll();
	}
	
	public void newPrize() {
		if(gameState.getPrizes().size()<1) {
			SecureRandom rand = new SecureRandom();
			int xPrize = rand.nextInt(dimensao.width/10)*10;
			int yPrize = rand.nextInt(dimensao.height/10)*10;
			Prize prize = new Prize(gameState.getPrizes().size(), xPrize, yPrize, Color.PINK);
			gameState.getPrizes().add(prize);
		}
		
		updateAll();
	}
	
	private final void colorir(Player player) {
		if (player.getY() > dimensao.getHeight()/2 && player.getX() < dimensao.getWidth()/2)
			player.setColor(Color.BLUE);
		else if (player.getY() < dimensao.getHeight()/2 && player.getX() < dimensao.getWidth()/2)
			player.setColor(Color.GREEN);
		else if (player.getX() > dimensao.getWidth()/2 && player.getY() > dimensao.getHeight()/2)
			player.setColor(Color.RED);
		else player.setColor(Color.ORANGE);
	}

	private Prize hasCollision(Player player) {
		for (Prize prize : gameState.getPrizes()) {
			if (player.x == prize.x && player.y == prize.y) {
				player.scored();
				return prize;
			}
		}
		return null;
	}
	
	private void updateAll() {
		observers.forEach(observer -> observer.update(gameState));
	}
	
	public void subscribe(Observer observer) {
		observers.add(observer);
		observer.update(gameState);
	}
	
	public void unsubscribe(Observer observer) {
		observers.remove(observers.indexOf(observer));
	}
}
