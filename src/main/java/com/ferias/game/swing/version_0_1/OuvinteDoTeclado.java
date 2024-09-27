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
		try {
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_ENTER) {
				game.newBall();
				game.newPrize();
			}
			else if (keyCode == KeyEvent.VK_UP) {
				game.moveUP(game.getBola(0));
			} else if (keyCode == KeyEvent.VK_DOWN) {
				game.moveDown(game.getBola(0));
			} else if (keyCode == KeyEvent.VK_LEFT) {
					game.moveLeft(game.getBola(0));
			} else if (keyCode == KeyEvent.VK_RIGHT) {
					game.moveRight(game.getBola(0));
			}
			
	
			else if (keyCode == KeyEvent.VK_W) {
				game.moveUP(game.getBola(1));
			} else if (keyCode == KeyEvent.VK_S) {
				game.moveDown(game.getBola(1));
			} else if (keyCode == KeyEvent.VK_A) {
					game.moveLeft(game.getBola(1));
			} else if (keyCode == KeyEvent.VK_D) {
					game.moveRight(game.getBola(1));
			}
		} catch (IndexOutOfBoundsException exception) {
			
		}
		
		gamePanel.repaint();
	}
}