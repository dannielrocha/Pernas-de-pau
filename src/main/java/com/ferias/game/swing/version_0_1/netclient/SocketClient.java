package com.ferias.game.swing.version_0_1.netclient;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ferias.game.swing.version_0_1.Comando;

public class SocketClient implements Closeable{
	
	private String serverAddress = "localhost";
	private int serverPort = 16000;
	private Socket socket;
	private ObjectOutputStream sender;
	
	public SocketClient(String endereco, int porta) {
		this.serverAddress = endereco;
		this.serverPort = porta;
	}
	
	public SocketClient(String endereco) {
		this.serverAddress = endereco;
	}
	
	public SocketClient() {
	}

	public SocketClient conectar() throws UnknownHostException, IOException {
		this.socket = new Socket(serverAddress, serverPort);

		this.sender = new ObjectOutputStream(socket.getOutputStream());
		sender.writeObject(Comando.NEW);
		sender.flush();
		return this;
	}
	
	public void enviarParaServidor(Object mensagem) throws IOException {
		if (sender != null && isConectado()) {
			if (mensagem != null && !mensagem.equals("")) {
				sender.writeObject(mensagem);
				sender.flush();
			}
		}
	}
	
	private void desconectar() throws IOException {
		sender.flush();
		sender.close();
		socket.close();
		System.out.println("\n Cliente desconectado.");
	}
	
	public boolean isConectado() {
		return socket != null && !socket.isClosed();
	}

	@Override
	public void close() throws IOException {
		desconectar();
	}
}
