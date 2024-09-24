package com.ferias.game.swing.version_0_1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 */
public class ConhecendoSwingApp_1 {
	public static void main(String[] args) {

		JFrame frontView = new JFrame("Simple Swing App");

		frontView.setAlwaysOnTop(true);
		frontView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frontView.setAutoRequestFocus(true);
		frontView.setLayout(new BorderLayout());
		TestPane gamePanel = new TestPane();
		frontView.add(gamePanel);
		frontView.setLocationRelativeTo(null);

		// pede para o usuário inserir seu nome
		String name = JOptionPane.showInputDialog("Digite seu nome:");
		// cria a mensagem
		String message = String.format("Olá, %s, bem-vindo ao Swing: programação Java com Interface Gráfica (GUI)!",
				name);
		// exibe a mensagem para cumprimentar o usuário pelo nome
		JOptionPane.showMessageDialog(frontView, message);

		frontView.pack();
		frontView.setVisible(true);

		frontView.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_UP) {
					System.out.println("Up Arrrow-Key is pressed!");
				} else if (keyCode == KeyEvent.VK_DOWN) {
					System.out.println("Down Arrrow-Key is pressed!");
				} else if (keyCode == KeyEvent.VK_LEFT) {
					System.out.println("Left Arrrow-Key is pressed!");
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					System.out.println("Right Arrrow-Key is pressed!");
				}
			}
		});
	}
}

//Innerclass for convinience of this exercise
class TestPane extends JPanel {
	private static final long serialVersionUID = 1L;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillOval(200, 100, 30, 30);
	}
}
