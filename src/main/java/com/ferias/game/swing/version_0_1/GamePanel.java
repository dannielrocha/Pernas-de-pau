package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Dimension dimensao;
	private Game game;

	public GamePanel(Game game, Dimension dimensoes) {
		this.game = game;
		this.dimensao = dimensoes;
	}

	@Override
	public Dimension getPreferredSize() {
		return dimensao;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		List<Ball> bolas = game.getBolas();
		bolas.forEach(bola -> {
			g.setColor(bola.getColor());
			g.fillOval(bola.getX(), bola.getY(), 10, 10);
			
			int xPlacar = 10;
			int yPlacar = bola.getID()+1;
			g.setColor(Color.BLACK);
			g.drawString("Player "+bola.getID()+": "+bola.getScore(), xPlacar, yPlacar*12);
		});
		
		List<Prize> prizes = game.getPrizes();
		prizes.forEach(prize -> {
			g.setColor(prize.getColor());
			g.drawOval(prize.getX(), prize.getY(), 10, 10);
		});		
	}
}

