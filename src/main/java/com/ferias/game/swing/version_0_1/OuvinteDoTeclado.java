package com.ferias.game.swing.version_0_1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class OuvinteDoTeclado extends KeyAdapter {
	
	Map<Integer, Comando> comandos = loadComandos();
	private Game game;
	
	public OuvinteDoTeclado(Game game) {
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		try {
			
			int keyCode = e.getKeyCode();	
			game.execute(comandos.get(keyCode));
			
		} catch (IndexOutOfBoundsException exception) {
			
		}
	}
	
	private Map<Integer, Comando> loadComandos() {
		Map<Integer, Comando> map = new HashMap<Integer, Comando>();
		map.put(KeyEvent.VK_ENTER, Comando.NEW);
		
		map.put(KeyEvent.VK_UP, Comando.MOVE_UP_P1);
		map.put(KeyEvent.VK_DOWN, Comando.MOVE_DOWN_P1);
		map.put(KeyEvent.VK_LEFT, Comando.MOVE_LEFT_P1);
		map.put(KeyEvent.VK_RIGHT, Comando.MOVE_RIGHT_P1);
		
		map.put(KeyEvent.VK_W, Comando.MOVE_UP_P2);
		map.put(KeyEvent.VK_S, Comando.MOVE_DOWN_P2);
		map.put(KeyEvent.VK_A, Comando.MOVE_LEFT_P2);
		map.put(KeyEvent.VK_D, Comando.MOVE_RIGHT_P2);
		
		return map;
	}
}