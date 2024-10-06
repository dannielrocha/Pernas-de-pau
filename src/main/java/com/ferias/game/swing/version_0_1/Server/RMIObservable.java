package com.ferias.game.swing.version_0_1.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIObservable extends Remote {

	public void subscribe(String observer) throws RemoteException;
	
	public void unsubscribe(String observer) throws RemoteException;
}
