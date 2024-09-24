package com.ferias.game.pernasdepau.frontView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.ferias.game.pernasdepau.input.KeyAction;
import com.ferias.game.pernasdepau.model.Ball;
import com.ferias.game.pernasdepau.model.FieldSide;
import com.ferias.game.pernasdepau.model.Player;

/* Cria o painel que será utilizado para desenhar e renderizar a aplicação */
public class ArenaPanel extends JPanel implements Observador {
	private static final long serialVersionUID = 1L;
	int quantidadeJogadores = 2;
	private List<Player> players = new ArrayList<Player>();
	private List<Ball> balls = new ArrayList<Ball>();
	private Dimension dimension;
	
	/* Construtor padrão */
	public ArenaPanel(Dimension dimension) {
		this.dimension = dimension;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return this.dimension;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		desenhaArena(g);

		balls.forEach(b -> desenhaBola(g, b));
		
		players.forEach(p -> desenhaPlayer(g, p));
	}

	private void desenhaArena(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.LIGHT_GRAY);
		int largura = getWidth();
		int altura = getHeight();
		int diametroCirculoCentral = largura/3;
		int raioCirculoCentral = diametroCirculoCentral/2;
		
		/*Desenha entorno*/
		g2d.drawRect(1, 1, largura-3, altura-3);
		/*Desenha grande área UPSIDE: começa em 1/4 a largura e tem tamanho de metade da largura*/
		g2d.drawRect(largura/4, -10, largura/2, altura/6);
		/*Desenha grande área DOWNSIDE*/
		g2d.drawRect(largura/4, altura - (altura/6), largura/2, altura/6);
		/*Desenha linha central*/
		g2d.drawLine(0, altura/2, largura, altura/2);
		/*Desenha círculo central*/
		g2d.drawArc( ((getWidth()/diametroCirculoCentral)-1)*raioCirculoCentral, altura/2-(raioCirculoCentral), diametroCirculoCentral, diametroCirculoCentral, 0, 360);
		
	}
	
	private void desenhaPlacar(Graphics g2d, Player player) {
		/* obs.: a fonte tem tamanho aprox. de 6 de largura X 12 de altura (em pixels) 
		 * e seu ponto 0,0 fica no canto inferior-esquerdo, não no superior, como nas demais figuras*/
		g2d.setColor(Color.LIGHT_GRAY);
		String nomeTime = "TIME " + player.getFieldSide().name();
		FieldSide ladoCampo = player.getFieldSide();
		/*Desenha o placar*/
		String texto = nomeTime+ ": "+player.getScore();
		Point posicaoPlacar = new Point((getWidth()/2 - (texto.length()/2)*6),  ladoCampo.equals(FieldSide.UPSIDE) ? 11 : getHeight()-4);
		g2d.drawString(texto, posicaoPlacar.x, posicaoPlacar.y);
	}
	
	private void desenhaBola(Graphics g, Ball ball) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.DARK_GRAY);
				
		/*Desenha um círculo que representa a bola*/
		g2d.fillOval(ball.x(), ball.y(), ball.size(), ball.size());
		g2d.setColor(Color.WHITE);
		g2d.drawLine(ball.x(), ball.y(), ball.x()+ ball.size(), ball.y() + ball.size());
		g2d.drawLine(ball.x()+ ball.size(), ball.y(), ball.x(), ball.y() + ball.size());
		g2d.dispose();
	}

	private void desenhaPlayer(Graphics g, Player player) {
		Graphics2D g2d = (Graphics2D) g.create();
				
		/* Desenha o quadrado equivalente a um player. */
		g2d.setColor(player.getColor());
		g2d.fillRect(player.x(), player.y(), player.size(), player.size());
		
		desenhaPersonagem(g2d, player);
		
		desenhaPlacar(g2d, player);
		/* Dispensa o Graphic2d Context, pois não será mais necessário. */
		g2d.dispose();
	}
	
	private void desenhaPersonagem(Graphics2D pai, Player player) {
		/* Desenha o número do player, na cor branca, no interior do quadrado que o representa.*/
		pai.setColor(Color.WHITE);
		pai.drawString(""+player.getName(), player.x() + 3, player.y() + player.size() - 1);
		
		Graphics2D g2d = (Graphics2D) pai.create();
		g2d.setColor(player.getColor());
		Font font = new Font(null, Font.BOLD, 12);    
		AffineTransform affineTransform = new AffineTransform();
		
		Point pPernas = new Point(player.x(), player.y()-1 + player.size()*2);
		Point pBracos = new Point(pPernas.x-4, pPernas.y-10);
		Point pCabeca = new Point(pPernas.x+2, pPernas.y-20);
		if (player.getFieldSide().equals(FieldSide.DOWNSIDE)) {
			affineTransform.rotate(Math.toRadians(180), 0, 0);
			pPernas = new Point(player.x() + 10, player.y() - 9);
			pBracos = new Point(pPernas.x+4, pPernas.y+10);
			pCabeca = new Point(pPernas.x-2, pPernas.y+20);
		}

		Font rotatedFont = font.deriveFont(affineTransform);
		g2d.setFont(rotatedFont);
		g2d.drawString("LL", pPernas.x, pPernas.y);
		g2d.drawString("/    \\", pBracos.x, pBracos.y);
		g2d.drawString("ø", pCabeca.x, pCabeca.y);
		
	}
	/*
	 * KeyListener: KeyListener é bem conhecido por responder apenas a eventos de
	 * teclas que ocorrem em componentes que podem receber foco e têm foco de
	 * teclado. Um JPanel, por padrão, não é focável, portanto, não recebe foco de
	 * teclado. Uma solução melhor seria usar a key binding (vinculação de teclas)
	 * API, que permite definir o nível de foco que um componente precisa ter antes
	 * que as vinculações sejam acionadas e permite que se reutilize uma classe
	 * Action (Ação) para várias teclas, reduzindo a duplicação de código
	 * 
	 * É altamente recomendável sobrescrever o método paintComponent e altamente
	 * não-recomendado sobrescrever o método paint ao executar renderização
	 * personalizada. É essencial manter a cadeia de renderização (paint chain), ou
	 * ocorrerá uma infinidade de maravilhosos artefatos estranhos sendo
	 * renderizados.
	 * 
	 * Evitar layout nulo é uma boa prática (não executar .setLayout(null);).
	 * Pixelização perfeita em layouts é uma ilusão dentro do design de interface de
	 * usuário modernas. Existem muitos fatores que afetam o tamanho individual dos
	 * componentes, nenhum dos quais estão sob controle do programador. Swing foi
	 * projetado para sempre trabalhar com gerenciadores de layout e descartá-los
	 * levará a uma infinidade de problemas e questões que consumirão cada vez mais
	 * tempo para corrigir
	 *
	 * Para mais informações, consulte: Renderização (painting) em AWT e Swing
	 * (http://www.oracle.com/technetwork/java/painting-140037.html) 
	 * Executando renderização personalizada
	 * (http://docs.oracle.com/javase/tutorial/uiswing/painting/)
	 * Um guia ilustrado sobre Layouts
	 * (https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html)
	 */
	
	/*
	 * Método utilizado para associar os eventos, neste caso, KeyStrokes disparados por cada
	 * tecla pressionada, à respectiva ação desejada (KeyAction).
	 */
	


	public void bindKeys(int condition, Map<KeyStroke, Action> map) {
		map.forEach( (k, a) -> bindKeyStroke(condition, ((KeyAction)a).getCommandName(), k, a) );
	}

	private void bindKeyStroke(int condition, String command, KeyStroke keyStroke, Action action) {
		InputMap inputMap = getInputMap(condition);
		ActionMap actionMap = getActionMap();

		/*Quando uma tecla é pressinada, caso um comando tenha sido registrado, o mesmo será chamado, na linha seguinte*/
		inputMap.put(keyStroke, command);
		/*Caso uma tecla esteja ligada a um comando, chama a ação (Action) que esteja relacionada ao comando.*/
		actionMap.put(command, action);
	}

	@Override
	public void update(GameState estado) {
		this.players = estado.getPlayers();
		this.balls = estado.getBalls();
		repaint();
	}	
}
