package com.ferias.game.swing.version_0_1.netclient;

import java.awt.Dimension;
import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.ferias.game.swing.version_0_1.Comando;
import com.ferias.game.swing.version_0_1.CommandExecutor;
import com.ferias.game.swing.version_0_1.GameState;
import com.ferias.game.swing.version_0_1.LocalObserver;
import com.ferias.game.swing.version_0_1.Player;
import com.ferias.game.swing.version_0_1.Server.RMICommandExecutor;

public class ClientGame implements CommandExecutor, RMIObserver {
	List<LocalObserver> observers = new ArrayList<LocalObserver>();

	GameState gameState = new GameState();
	Dimension dimensao = new Dimension();
	String localIP, serverAddress;
	RMICommandExecutor serverStub;
	//SocketClient socket;

	public ClientGame(Dimension dimension, String[] serverAddr) {
		this.dimensao = dimension;
		try {
			this.localIP = InetAddress.getLocalHost().getHostAddress();
			this.serverAddress = serverAddr[0];
//			Encontrando os IPs da máquina local:
//			System.out.println("Usando InetAddress: "+InetAddress.getLocalHost()+"\n\n\n Usando NetworkInterface:");
//			for (NetworkInterface inet: Collections.list(NetworkInterface.getNetworkInterfaces()))
//				if (inet.isUp() && !inet.isVirtual())
//					System.out.printf("Nome da interface: %s IP: %s%n ", inet.getName(), inet.getInetAddresses().nextElement());
		
		
			/*Expõe um objeto remoto para tornar possível receber chamadas de método usando uma dada porta passada como parâmetro. 
			O objeto é exportado por meio de um ServerSocket criado pela classe RMISocketFactory.
			
			Parâmetros:obj - o objeto a ser exportado. port - a porta para expor o objeto. 
			Retorno: uma referência (stub) para o objeto remoto.*/
			RMIObserver clientStub = (RMIObserver) UnicastRemoteObject.exportObject(this,2099);
			//Retorna o servidor de registro RMI no enderço passado como parâmetro e porta padrão 1099:
			Registry registry = LocateRegistry.getRegistry(serverAddress, Integer.parseInt(serverAddr.length > 1 ? serverAddr[1] : "1099"));
			
			try {
				//Registro do Objeto Remoto com Java RMI Registry:
				registry.rebind(CALLBACK_PREFIX+ localIP,clientStub);
			} catch (AccessException e) {
				e.printStackTrace();
				System.out.println("Problemas no acesso ao RMIRegistry [AccessException]");
			} catch (RemoteException e) {
				e.printStackTrace();
				System.out.println("RemoteExeception: Mal funcionamento do RMIRegistry");
			}
			
			for (String s: registry.list())
				System.out.println("Stubs registrados no RMIRegistry com nome: " + s);
			
			//obtém o stub para o objeto remoto com nome RMICommandExecutor.GAME_SERVER_NAME do registro:
			serverStub = (RMICommandExecutor) registry.lookup(RMICommandExecutor.GAME_SERVER_NAME);
			serverStub.subscribe(CALLBACK_PREFIX+ localIP);

			//O socket (upstream) precisa ser criado depois do RMI (callback way)
			//socket = new SocketClient(endereco).conectar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer(int idPlayer) {
		return gameState.getPlayers().get(idPlayer);
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
		try {			
			//Invoca o método remoto:
			serverStub.execute(comando);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(GameState gameState)  throws RemoteException {
		this.gameState = gameState;
		updateAll();
	}
}
