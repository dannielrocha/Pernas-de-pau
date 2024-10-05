package com.ferias.game.swing.version_0_1.Server;

import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 */
public class Server {
	public static void main(String[] args) {
		/*
		 * cria um socket "servidor" associado a porta 8000 já aguardando conexões
		 */
		ServerSocket servidor;
		int porta = 16000;
		try {
			LocateRegistry.createRegistry(1099);
			ServerGame game = new ServerGame(new Dimension(300,300));
			servidor = new ServerSocket(porta);
			System.out.println("[Server]Servidor inicializado na porta " + porta);
			ExecutorService pool = Executors.newFixedThreadPool(30);
			
			while (true) {
				//cria uma nova thread para cada nova solicitacao de conexao
				pool.execute(new ThreadConexao(servidor.accept(), game));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
