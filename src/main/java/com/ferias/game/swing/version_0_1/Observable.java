package com.ferias.game.swing.version_0_1;

public interface Observable {
	
	public void subscribe(LocalObserver observer);
	
	public void unsubscribe(LocalObserver observer);
}
