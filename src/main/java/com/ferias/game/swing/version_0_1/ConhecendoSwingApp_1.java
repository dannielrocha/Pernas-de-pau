package com.ferias.game.swing.version_0_1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 */
public class ConhecendoSwingApp_1 {
	public static void main(String[] args) {

		/*Cria uma nova janela com o título 'Simple Swing App'.*/
		JFrame frontView = new JFrame("Simple Swing App");
		/*Define se a janela pode ser sobreposta por outras.*/
		frontView.setAlwaysOnTop(true);
		/*Define se a janela pode ser redimensionada pelo usuário.*/
		frontView.setResizable(false);
		/*Define o comportamento padrão para o fechamento da janela.*/
		frontView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Define se a janela requisitará foco para si logo que for exibida.*/
		frontView.setAutoRequestFocus(true);
		/*Define um algoritmo de organização automática dos componentes.*/
		frontView.setLayout(new BorderLayout());
		/*Define um componente relativo ao qual o centro da janela será posicionado.*/
		frontView.setLocationRelativeTo(null);

		TestPane painel = new TestPane();
		frontView.getContentPane().add(painel);
		
		// pede para o usuário inserir seu nome
		String name = JOptionPane.showInputDialog("Digite seu nome:");
		// cria a mensagem
		String message = String.format("Olá, %s, bem-vindo ao Swing: programação Java com Interface Gráfica (GUI)!",
				name);
		// exibe a mensagem para cumprimentar o usuário pelo nome
		JOptionPane.showMessageDialog(frontView, message);

		/*Redimensiona a janela com base nas propriedades preferredSize de seus subcomponentes.*/
		frontView.pack();
		/*Torna a janela visível para o usuário.*/
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
		g.fillArc(this.getWidth()/2, 250, 30, 30, 135, 180);
		g.fillOval(200, 100, 30, 30);
		g.fillRect(100, 200, 30, 30);
		g.fillRoundRect(30, 30, 25, 25, 20, 20);
		g.fill3DRect(40, 50, 40, 40, true);
		g.clearRect(100, 200, 20, 20);
		
		g.setColor(Color.BLUE);
		g.drawLine(0, this.getHeight()/2, this.getWidth()/2, this.getHeight());
		g.drawOval(150, 100, 30, 30);
		g.drawRect(150, 200, 30, 30);
		g.drawRoundRect(70, 30, 25, 25, 20, 20);
		g.setFont(new Font("Arial Bold", Font.PLAIN, 14));
		g.drawString("Um texto pode ser facilmente adicionado", 10, 20);
		g.draw3DRect(70, 50, 40, 40, true);
		
		ImageIcon image = new ImageIcon("src/resources/duke.png");
		g.drawImage(image.getImage(), 60, 110, 137, 80, null, null);
	}
}
