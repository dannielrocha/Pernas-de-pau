package com.ferias.game.swing.version_0_1.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.ferias.game.swing.version_0_1.netclient.RMIObserver;

public interface RMIObservable extends Remote {

	public void subscribe(RMIObserver observer) throws RemoteException;
	
	public void unsubscribe(RMIObserver observer) throws RemoteException;
}
