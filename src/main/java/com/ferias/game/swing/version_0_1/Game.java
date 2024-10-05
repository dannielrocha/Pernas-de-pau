package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class Game implements CommandExecutor {
	List<LocalObserver> observers = new ArrayList<LocalObserver>();
	Map<Comando, BiConsumer<Player, Dimension>> strategies = loadStrategies();
	GameState gameState = new GameState();
	Dimension dimensao = new Dimension();
	
	public Game(Dimension dimension) {
		this.dimensao = dimension;
	}
	
	
	public Player getPlayer(int idPlayer) {
		return gameState.getPlayers().get(idPlayer);
	}

	
	public void newPlayer() {
		if (gameState.getPlayers().size()<2) {
			int id = gameState.getPlayers().size();
			Player player = new Player(id, 0,0, Color.BLUE);
			gameState.getPlayers().put(player.getID(), player);
		}
	}
	
	public void newPrize() {
		if(gameState.getPrizes().size()<1) {
			SecureRandom rand = new SecureRandom();
			int xPrize = rand.nextInt(dimensao.width/10)*10;
			int yPrize = rand.nextInt(dimensao.height/10)*10;
			Prize prize = new Prize(gameState.getPrizes().size(), xPrize, yPrize, Color.PINK);
			gameState.getPrizes().put(prize.getID(), prize);
		}
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
		for (Prize prize : gameState.getPrizes().values()) {
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
	
	public void subscribe(LocalObserver observer) {
		observers.add(observer);
		observer.update(gameState);
	}
	
	public void unsubscribe(LocalObserver observer) {
		observers.remove(observers.indexOf(observer));
	}


	public void execute(Comando comando) {
		if (comando.equals(Comando.NEW)) {
			newPlayer();
			newPrize();
		} else {
			Player player = gameState.getPlayers().get(comando.getPlayerID());
			strategies.get(comando.getEquivalent()).accept(player, dimensao);
			colorir(player);
			
			SecureRandom rand = new SecureRandom();
			Prize prize = hasCollision(player);
			if (prize != null) {
				prize.x = rand.nextInt(dimensao.width/Prize.SIZE)*Prize.SIZE;
				prize.y = rand.nextInt(dimensao.height/Prize.SIZE)*Prize.SIZE;
			}
		}
		
		updateAll();
	}
	
	
	private Map<Comando, BiConsumer<Player, Dimension>> loadStrategies() {
		Map<Comando, BiConsumer<Player, Dimension>> map = new HashMap<Comando, BiConsumer<Player, Dimension>>();
		map.put(Comando.MOVE_LEFT, new BiConsumer<Player, Dimension>() {
			
			@Override
			public void accept(Player player, Dimension dimension) {
				if (player.getX() >= Player.SIZE)
						player.horizontalMove(-Player.SIZE);
			}
		});
		
		map.put(Comando.MOVE_RIGHT, new BiConsumer<Player, Dimension>() {
			
			@Override
			public void accept(Player player, Dimension dimension) {
				if (player.getX()+Player.SIZE <= dimension.getWidth()-Player.SIZE)
						player.horizontalMove(Player.SIZE);
			}
		});
		
		map.put(Comando.MOVE_UP, new BiConsumer<Player, Dimension>() {
			
			@Override
			public void accept(Player player, Dimension dimension) {
				if (player.getY() >= Player.SIZE)
						player.verticalMove(-Player.SIZE);
			}
		});
		
		map.put(Comando.MOVE_DOWN, new BiConsumer<Player, Dimension>() {
			
			@Override
			public void accept(Player player, Dimension dimension) {
				if (player.getY()+Player.SIZE <= dimension.getHeight()-Player.SIZE)
						player.verticalMove(Player.SIZE);
			}
		});
		
		return map;
	}
}
