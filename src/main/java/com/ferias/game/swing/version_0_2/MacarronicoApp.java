package com.ferias.game.swing.version_0_2;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class MacarronicoApp {

    public static void main(String[] args) {
        new MacarronicoApp();
    }

    public MacarronicoApp() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JFrame janelaPrincipal = new JFrame("Testing");
                janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                janelaPrincipal.add(new TestPane());
                janelaPrincipal.pack();
                janelaPrincipal.setLocationRelativeTo(null);
                janelaPrincipal.setVisible(true);
                
                
        		// pede para o usuário inserir seu nome
        		String name = JOptionPane.showInputDialog("Digite seu nome:");
        		// cria a mensagem
        		String message = String.format("Olá, %s, bem-vindo ao Swing: programação Java com Interface Gráfica (GUI)!", name);
        		// exibe a mensagem para cumprimentar o usuário pelo nome
        		JOptionPane.showMessageDialog(janelaPrincipal, message);
            }
        });
    }

    @SuppressWarnings("serial")
	public class TestPane extends JPanel {

        private int xPos;

        public TestPane() {
            Action leftAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    xPos -= 2;
                    if (xPos < 0) {
                        xPos = 0;
                    }
                    repaint();
                }
            };
            Action rightAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    xPos += 2;
                    if (xPos + 10 > getWidth()) {
                        xPos = getWidth() - 10;
                    }
                    repaint();
                }
            };

            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), leftAction);
            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT, 0), leftAction);
            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), leftAction);
            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.left", KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), leftAction);

            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), rightAction);
            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, 0), rightAction);
            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), rightAction);
            bindKeyStroke(WHEN_IN_FOCUSED_WINDOW, "move.right", KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), rightAction);
        }

        protected void bindKeyStroke(int condition, String name, KeyStroke keyStroke, Action action) {
            InputMap im = getInputMap(condition);
            ActionMap am = getActionMap();

            im.put(keyStroke, name);
            am.put(name, action);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int yPos = (getHeight() - 10) / 2;
            g2d.drawRect(xPos, yPos, 10, 10);
            g2d.dispose();
        }

    }

}
