package com.ferias.game.swing.version_0_1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OuvinteDoTeclado extends KeyAdapter {
	
	private Game game;
	
	public OuvinteDoTeclado(Game game) {
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		try {
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_ENTER) {
				game.newPlayer();
				game.newPrize();
			}
			else if (keyCode == KeyEvent.VK_UP) {
				game.moveUP(game.getPlayer(0));
			} else if (keyCode == KeyEvent.VK_DOWN) {
				game.moveDown(game.getPlayer(0));
			} else if (keyCode == KeyEvent.VK_LEFT) {
					game.moveLeft(game.getPlayer(0));
			} else if (keyCode == KeyEvent.VK_RIGHT) {
					game.moveRight(game.getPlayer(0));
			}
			
	
			else if (keyCode == KeyEvent.VK_W) {
				game.moveUP(game.getPlayer(1));
			} else if (keyCode == KeyEvent.VK_S) {
				game.moveDown(game.getPlayer(1));
			} else if (keyCode == KeyEvent.VK_A) {
					game.moveLeft(game.getPlayer(1));
			} else if (keyCode == KeyEvent.VK_D) {
					game.moveRight(game.getPlayer(1));
			}
		} catch (IndexOutOfBoundsException exception) {
			
		}
	}
}