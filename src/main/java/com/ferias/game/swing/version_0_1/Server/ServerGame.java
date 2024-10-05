package com.ferias.game.swing.version_0_1.Server;

import java.awt.Color;
import java.awt.Dimension;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import com.ferias.game.swing.version_0_1.Comando;
import com.ferias.game.swing.version_0_1.GameState;
import com.ferias.game.swing.version_0_1.Player;
import com.ferias.game.swing.version_0_1.Prize;
import com.ferias.game.swing.version_0_1.netclient.RMIObserver;

public class ServerGame implements RMICommandExecutor {
	Map<String, RMIObserver> observers = new ConcurrentHashMap<String, RMIObserver>();
	Map<Comando, BiConsumer<Player, Dimension>> strategies = loadStrategies();
	GameState gameState = new GameState();
	Dimension dimensao = new Dimension();
	
	public ServerGame(Dimension dimension) {
		
		try {
			
				RMICommandExecutor stub = (RMICommandExecutor) UnicastRemoteObject.exportObject(this,0);
				//Retorna o servidor de registro RMI no localhost e porta padrão 1099:
				Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress());
				
				for (String s: registry.list())
					System.out.println("Stubs registrados no RMIRegistry com nome: " + s);
				
				//Registro do Objeto Remoto com Java RMI Registry:
				registry.rebind(GAME_SERVER_NAME,stub);
			
		} catch ( UnknownHostException | RemoteException e) {
			e.printStackTrace();
		}
		
		this.dimensao = dimension;
		newPrize();
	}
	
	public void newPlayer(int playerID) {
		if (!gameState.getPlayers().containsKey(playerID)) {
			Player player = new Player(playerID, 0,0, Color.BLUE);
			gameState.getPlayers().put(playerID, player);
		}
	}
	
	public void newPrize() {
		if(gameState.getPrizes().size()<1) {
			SecureRandom rand = new SecureRandom();
			int xPrize = rand.nextInt(dimensao.width/10)*10;
			int yPrize = rand.nextInt(dimensao.height/10)*10;
			int id = gameState.getPrizes().size();
			Prize prize = new Prize(id, xPrize, yPrize, Color.PINK);
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
			if (player.getX() == prize.getX() && player.getY() == prize.getY()) {
				player.scored();
				return prize;
			}
		}
		return null;
	}
	
	private void updateAll() {
		if (!observers.isEmpty())
			observers.values().forEach(observer -> {
				try {
					observer.update(gameState);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			});
	}
	
	public void subscribe(RMIObserver observer) {
		
		try {
			String clientIP = RemoteServer.getClientHost();

			observers.put(clientIP, observer);
			
			int playerID = Integer.parseInt( clientIP.replaceAll("[^0-9]", ""));
			Player player = gameState.getPlayers().get( playerID );
			if (player == null)
				newPlayer(playerID);
		} catch (NumberFormatException | ServerNotActiveException e) {
			e.printStackTrace();
		}
		
		updateAll();
	}
	
	public void unsubscribe(RMIObserver observer) {
		try {
			observers.remove(RemoteServer.getClientHost());
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
	}


	public synchronized void execute(Comando comando) {
		try {
			int playerID = Integer.parseInt( RemoteServer.getClientHost().replaceAll("[^0-9]", ""));
			
			Player player = gameState.getPlayers().get( playerID );
			
			if (player != null){
				strategies.get(comando).accept(player, dimensao);
				colorir(player);
				
				SecureRandom rand = new SecureRandom();
				Prize prize = hasCollision(player);
				if (prize != null) {
					prize.moveTo(rand.nextInt(dimensao.width/Prize.SIZE)*Prize.SIZE,
					rand.nextInt(dimensao.height/Prize.SIZE)*Prize.SIZE);
				}
				
				updateAll();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
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
