package com.ferias.game.swing.version_0_1.netclient;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ferias.game.swing.version_0_1.GamePanel;
import com.ferias.game.swing.version_0_1.OuvinteDoTeclado;

/**
 * 
 */
public class NetClientApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					/*
					 * Tenta selecionar um modelo de aparência para as janelas baseado no sistema
					 * operacional.
					 */
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				String serverIP = JOptionPane.showInputDialog("Digite o endereço IP do servidor: ");
				
				JFrame frontView = new JFrame("Simple Swing App");
				frontView.setResizable(false);
				frontView.setAlwaysOnTop(true);
				frontView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frontView.setAutoRequestFocus(true);
				frontView.setLayout(new BorderLayout());
				Dimension dimensoes = new Dimension(300, 300);
				
				ClientGame game = new ClientGame(dimensoes, serverIP);
				GamePanel gamePanel = new GamePanel(dimensoes);
				game.subscribe(gamePanel);
				
				frontView.add(gamePanel);
				frontView.pack();
				frontView.setLocationRelativeTo(null);
				
				frontView.setVisible(true);
				frontView.addKeyListener(new OuvinteDoTeclado(game));
			}
		});
	}
}