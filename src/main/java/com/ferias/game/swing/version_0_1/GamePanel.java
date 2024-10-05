package com.ferias.game.swing.version_0_1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements LocalObserver{
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
		int yPlacar = 0;
		Map<Integer, Player> players = gameState.getPlayers();
		for (Player player: players.values()){
			g.setColor(player.getColor());
			g.fillOval(player.getX(), player.getY(), 10, 10);
			
			int xPlacar = 10;
			g.setColor(Color.BLACK);
			g.drawString("Player "+player.getID()+": "+player.getScore(), xPlacar, ++yPlacar*12);
		}
		
		Map<Integer, Prize> prizes = gameState.getPrizes();
		prizes.values().forEach(prize -> {
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

