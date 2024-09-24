package com.ferias.game.pernasdepau.service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.ferias.game.pernasdepau.frontView.GameState;
import com.ferias.game.pernasdepau.frontView.Observador;
import com.ferias.game.pernasdepau.input.Command;
import com.ferias.game.pernasdepau.model.Ball;
import com.ferias.game.pernasdepau.model.FieldSide;
import com.ferias.game.pernasdepau.model.Player;

public class Game implements Observavel {
	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	private final Map<Command, Consumer<Player>> strategies = populateStrategies();
	private List<Player> players = new ArrayList<Player>();
	private List<Ball> balls = new ArrayList<Ball>();
	private Dimension limitesDaArena;

	private List<Observador> observadores = new ArrayList<Observador>();
	
	public Game (int width, int height, int quantidadeJogadores) {
		this.limitesDaArena = new Dimension(width, height);
		/*
		 * Cria os jogadores e a bola
		 */
		for (int i = 0; i < quantidadeJogadores; i++) {
			newPlayer();
		}
		newBall();
	}
	
	
	/* Método para criar um novo jogador */
	private void newPlayer() {
		
		int playerName = players.size() + 1;
		Random rand = new Random(~(new Date().getTime()));
		int limiteX = limitesDaArena.width/10;
		int limiteY = (limitesDaArena.height-20)/20;
		FieldSide time = FieldSide.values()[playerName%2];
		int ladoCampo = time.equals(FieldSide.UPSIDE) ? 0 : limiteY;
		int playerX = rand.nextInt(limiteX)*10;
		int playerY = (rand.nextInt(limiteY)+ladoCampo)*10;
		
		Point posicaoInicial = new Point(playerX, playerY);
		Player newPlayer = new Player(playerName, time, posicaoInicial);
		newPlayer.setColor(time.equals(FieldSide.UPSIDE) ? Color.BLUE : Color.BLACK);
		players.add(newPlayer);
		
		//((limitesDaArenaPanel)limitesDaArena).associaPlayerComConjuntoDeTeclas(newPlayer);
	}
	

	/* Método para criar uma nova bola */
	private void newBall() {
		Point posicaoInicial = new Point(limitesDaArena.width/2-Ball.SIZE/2, limitesDaArena.height/2-Ball.SIZE/2);
		balls.add(new Ball(balls.size(), posicaoInicial));
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	public List<Ball> getBalls() {
		return balls;
	}

	public void keepRolling(Ball ball) { logger.info("keepRolling");
		/*Impede que a bola desapareça na lateral do campo.*/
		if (ball.x() < 0 ) ball.horizontalMove(ball.size()*2);
		else if (ball.x() +ball.size() >= limitesDaArena.width) ball.horizontalMove(-ball.size()*2);
		
	}

	public void rulesPlayer(Player player) { logger.info("rulesPlayer");
		Ball ball = player.hasCollision(balls);
		if (ball != null) {
			player.kick(ball);

		
			FieldSide golDoTime = ball.isInsideGoalBox(limitesDaArena);
			if (golDoTime != null) {
				players.forEach(  p -> { if (!p.getFieldSide().equals(golDoTime)) p.goalScored(); }  );
				ball.toFieldCenter(limitesDaArena);
			}
		} else {
			player.run();
		}
	}
	
	
	
	
	
	private Map<Command, Consumer<Player>> populateStrategies() {
		
		Consumer<Player> rightStrategy = p -> { logger.info("rightStrategy\n" + p);
			/*Executa o movimento para o comando de ir para a direita*/
			p.horizontalMove(p.stepSize());
			/*Caso o jogador tenha passado para fora do limite direto da limitesDaArena, retorna o jogador para dentro.*/
			if (p.x() + p.stepSize() > limitesDaArena.width) {
				p.horizontalMove(-p.stepSize());
			}
		};

		Consumer<Player> leftStrategy = p -> { logger.info("leftStrategy\n " + p);
			p.horizontalMove(-p.stepSize());
			if (p.x() < 0) {
				p.horizontalMove(p.stepSize());
			}
		};

		Consumer<Player> downStrategy = p -> { logger.info("downStrategy\n" + p);
			p.verticalMove(p.stepSize());
			if (p.y() + p.stepSize() > limitesDaArena.height) {
				p.verticalMove(-p.stepSize());
			}
		};

		Consumer<Player> upStrategy = p -> { logger.info("upStrategy\n" + p);
			p.verticalMove(-p.stepSize());
			if (p.y() < 0) {
				p.verticalMove(p.stepSize());
			}
		};
		
		Map<Command, Consumer<Player>> base = new EnumMap<>(Command.class);
		base.put(Command.MOVE_RIGHT, rightStrategy);
		base.put(Command.MOVE_LEFT, leftStrategy);
		base.put(Command.MOVE_DOWN, downStrategy);
		base.put(Command.MOVE_UP, upStrategy);
		
		return base;
	}

	@Override
	public void update(Command command) {
		Player player = players.get(command.getCommandSet().ordinal());
		strategies.get(command.getEquivalent()).accept(player);
		rulesPlayer(player);
		balls.forEach(ball -> keepRolling(ball));
		
		
		notificaObservadores();
	}

	@Override
	public void addObservador(Observador observador) {
		this.observadores.add(observador);
		observador.update(new GameState(players, balls));
	}
	
	@Override
	public void removeObservador(Observador observador) {
		this.observadores.remove(observador);
	}
	
	private void notificaObservadores() {
		observadores.forEach(o -> o.update(new GameState(players, balls)));
	}
}