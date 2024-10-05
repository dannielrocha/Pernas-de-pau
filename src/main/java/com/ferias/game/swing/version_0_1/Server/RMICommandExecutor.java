package com.ferias.game.swing.version_0_1.Server;

import java.rmi.RemoteException;

import com.ferias.game.swing.version_0_1.Comando;

public interface RMICommandExecutor extends RMIObservable {
	
	public static final String GAME_SERVER_NAME = "GameServer";
	
	public void execute(Comando comando) throws RemoteException;
}
