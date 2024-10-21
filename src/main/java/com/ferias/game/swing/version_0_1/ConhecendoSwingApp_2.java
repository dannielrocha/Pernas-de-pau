package com.ferias.game.swing.version_0_1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 
 */
public class ConhecendoSwingApp_2 {
	
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
				JFrame frontView = new JFrame("Simple Swing App");
		
				frontView.setAlwaysOnTop(true);
				frontView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frontView.setAutoRequestFocus(true);
				frontView.setLayout(new BorderLayout());
				GamePanel gamePane = new GamePanel();
				frontView.add(gamePane);
				frontView.pack();
				frontView.setLocationRelativeTo(null);
				
				frontView.setVisible(true);
		
				frontView.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						int keyCode = e.getKeyCode();
						if (keyCode == KeyEvent.VK_UP) {
							System.out.println("Up Arrrow-Key is pressed!");
							gamePane.verticalMove(-10);
						} else if (keyCode == KeyEvent.VK_DOWN) {
							System.out.println("Down Arrrow-Key is pressed!");
							gamePane.verticalMove(10);
						} else if (keyCode == KeyEvent.VK_LEFT) {
							System.out.println("Left Arrrow-Key is pressed!");
							gamePane.horizontalMove(-10);
						} else if (keyCode == KeyEvent.VK_RIGHT) {
							System.out.println("Right Arrrow-Key is pressed!");
							gamePane.horizontalMove(10);
						}
						gamePane.repaint();
					}
				});
		
				frontView.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		
			}//fim: run(){
		}/*fim: new Runnable() { */);//fim: SwingUtilities.invokeLater(
	}
}

//Innerclass for convinience of this exercise
class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int xPos;
	private int yPos;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillOval(xPos, yPos, 10, 10);
	}

	public int getxPos() {
		return xPos;
	}

	public void horizontalMove(int xPos) {
		this.xPos += xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void verticalMove(int yPos) {
		this.yPos += yPos;
	}

}
