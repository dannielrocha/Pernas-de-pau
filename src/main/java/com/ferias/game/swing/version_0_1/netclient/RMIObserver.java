package com.ferias.game.swing.version_0_1.netclient;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.ferias.game.swing.version_0_1.GameState;

public interface RMIObserver extends Remote {
	
	public static final String CALLBACK_PREFIX = "CallbackFunction/";
	
	public void update(GameState gameState) throws RemoteException;
	
}
