package com.ferias.game.swing.version_0_1;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * 
 */
public class ConhecendoSwingApp_2 {
	public static void main(String[] args) {

		JFrame frontView = new JFrame("Simple Swing App");
		frontView.setResizable(false);
		frontView.setAlwaysOnTop(true);
		frontView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frontView.setAutoRequestFocus(true);
		frontView.setLayout(new BorderLayout());
		Dimension dimensoes = new Dimension(300, 300);
		
		Game game = new Game(dimensoes);
		GamePanel gamePanel = new GamePanel(dimensoes);
		game.subscribe(gamePanel);
		
		frontView.add(gamePanel);
		frontView.pack();
		frontView.setLocationRelativeTo(null);
		
		frontView.setVisible(true);

		frontView.addKeyListener(new OuvinteDoTeclado(game));
	}
}