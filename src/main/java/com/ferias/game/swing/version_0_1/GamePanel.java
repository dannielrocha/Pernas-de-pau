package com.ferias.game.swing.version_0_1;

import java.awt.Dimension;
import java.awt.Graphics;

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
		
		g.setColor(game.getBola().getColor());
		g.fillOval(game.getBola().getX(), game.getBola().getY(), 10, 10);
	}
}

