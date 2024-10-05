package com.ferias.game.swing.version_0_1.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.ferias.game.swing.version_0_1.Comando;
import com.ferias.game.swing.version_0_1.GameState;
import com.ferias.game.swing.version_0_1.LocalObserver;
import com.ferias.game.swing.version_0_1.netclient.RMIObserver;

public class ThreadConexao implements Runnable, LocalObserver {

	private final Socket socket;
	private boolean conectado;
	private RMICommandExecutor game;
	private String clientIP;

	public ThreadConexao(Socket socket, RMICommandExecutor game) {
		this.socket = socket;
		this.game = game;
	}

	@Override
	public void run() {
		conectado = true;
		//imprime na tela o IP do cliente
		clientIP = socket.getInetAddress().toString();
		System.out.println("[ServerThread]Conexão iniciada com cliente no endereço " + clientIP);
		ObjectInputStream receiver = null;

		try {
			receiver = new ObjectInputStream(socket.getInputStream());

			while (conectado) {
					System.out.println("[ServerThread]Iniciando recepção de um comando...");
					//game.subscribe(this);
					Comando comando = null;
					
					while(receiver.available() <= 0);
					
					comando = (Comando) receiver.readObject();
					
					if (comando == null || comando.equals(Comando.DESCONECTAR))
						this.conectado = false;
					else {

						game.execute(comando);
					}
			}
			
			if (socket != null)
				socket.close();
			System.out.println("[ServerThread]Conexão com " + socket.getInetAddress() +" encerrada.\n");
		} catch (Exception ex) {
			conectado = false;
			System.out.println("[ServerThread]Um erro ocorreu e a conexão com " + socket.getInetAddress() +" foi encerrada.\n");
			ex.printStackTrace();
		} finally {
				try {
					receiver.close();
				} catch (IOException e) {
					e.printStackTrace();
				}			
		}
	}

	@Override
	public void update(GameState gameState) {
		System.out.println("[TrheadConexao] gameState changed! Updating client "+clientIP);
		
		try {
			
			Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress() );/*serverIP*/

			for (String s: registry.list())
				System.out.println("Stub registrados no RMIRegistry com nome: " + s);
			
			//obtém o stub para o objeto remoto(Ola) do registro:
			RMIObserver stub = (RMIObserver) registry.lookup("CallbackFunction"+clientIP);
			//Invoca o método remoto:
			stub.update(gameState);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}