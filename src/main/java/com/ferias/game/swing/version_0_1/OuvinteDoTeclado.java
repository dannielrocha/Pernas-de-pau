package com.ferias.game.swing.version_0_1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OuvinteDoTeclado extends KeyAdapter {
	
	private GamePanel gamePanel;
	private Game game;
	
	public OuvinteDoTeclado(GamePanel gamePanel, Game game) {
		this.game = game;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_UP) {
			game.verticalMove(-10);
		} else if (keyCode == KeyEvent.VK_DOWN) {
			game.verticalMove(10);
		} else if (keyCode == KeyEvent.VK_LEFT) {
				game.horizontalMove(-10);
		} else if (keyCode == KeyEvent.VK_RIGHT) {
				game.horizontalMove(10);
		}
		gamePanel.repaint();
	}
}