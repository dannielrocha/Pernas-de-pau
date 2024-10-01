package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;
	private Dimension dimensao;
	private GameState gameState;

	public GamePanel(Dimension dimensoes) {
		this.dimensao = dimensoes;
	}

	@Override
	public Dimension getPreferredSize() {
		return dimensao;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		List<Player> players = gameState.getPlayers();
		players.forEach(player -> {
			g.setColor(player.getColor());
			g.fillOval(player.getX(), player.getY(), 10, 10);
			
			int xPlacar = 10;
			int yPlacar = player.getID()+1;
			g.setColor(Color.BLACK);
			g.drawString("Player "+player.getID()+": "+player.getScore(), xPlacar, yPlacar*12);
		});
		
		List<Prize> prizes = gameState.getPrizes();
		prizes.forEach(prize -> {
			g.setColor(prize.getColor());
			g.drawOval(prize.getX(), prize.getY(), 10, 10);
		});		
	}

	@Override
	public void update(GameState gameState) {
		this.gameState = gameState;
		this.repaint();
		
	}
}

