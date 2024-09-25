package com.ferias.game.pernasdepau;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/*
*	Todo processamento do Swing é feito em uma thread chamada EDT (Event Dispatching Thread - Thread de Lançamento de Eventos). 
*	Portanto, caso a aplicação exija computações mais demoradas, a GUI ficaria bloqueada pelo tempo necessário para processar as partes demoradas que utilizem a Thread principal, a EDT.
*
*	A maneira de evitar isso é processar esses cálculos lançando threads diferentes, para que a GUI permaneça responsiva. 
*	Após a execução do subprocesso, é necessário atualizar sua GUI com os resultados, o que deve ser feito dentro do EDT.
*	É aqui que EventQueue.invokeLater entra em ação. Ele publica um evento (seu Runnable) no final da lista de eventos do Swing para ser processado após todos os eventos da GUI.
*
*	O uso de EventQueue.invokeAndWait também seria possível, aqui. A diferença seria que a thread de cálculo fica bloqueada até que a GUI termine de ser atualizada. 
*	Portanto, é óbvio que isso não deve ser usado a partir do EDT.
*
*	Tenha cuidado para NÂO atualizar sua GUI Swing a partir de uma thread externa. Na maioria dos casos, isso produz alguns problemas estranhos de atualização/renovação (updating/refreshing).
*
*	Ainda há código Java por aí que inicia um JFrame simples a partir do thread principal. Isso pode causar problemas, mas não é impedido pelo Swing. 
*	A maioria dos IDEs modernos agora cria algo assim para iniciar a GUI:
*/

import com.ferias.game.pernasdepau.frontView.ArenaPanel;
import com.ferias.game.pernasdepau.input.Command;
import com.ferias.game.pernasdepau.input.CommandSet;
import com.ferias.game.pernasdepau.input.KeyAction;
import com.ferias.game.pernasdepau.service.Game;
import com.ferias.game.pernasdepau.service.Observavel;

public class PernaDePauApp {

	public static void main(String[] args) {
		new PernaDePauApp();
	}

	public PernaDePauApp() {
		SwingUtilities.invokeLater(new Runnable() {

			private Observavel game;

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

				/* Cria uma nova janela. */
				JFrame janelaPrincipal = new JFrame("Pernas-de-pau");
				/*Impede que a janela tenha seu formato/tamanho modificado.*/
				janelaPrincipal.setResizable(false);
				/*
				 * Indica a operação padrão ao fechar a janela. Como Swing utiliza subrotinas
				 * (threads) para executar suas operações, a aplicação não termina
				 * automaticamente quando a janela é fechada e isso precisa ser indicado
				 * explicitamente.
				 */
				janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				Dimension dimension = new Dimension(311, 451);
				ArenaPanel arena = new ArenaPanel(dimension);
				this.game = new Game(dimension.width, dimension.height, getQuantidadeJogadores());
				
				game.addObservador(arena);
				arena.bindKeys(JComponent.WHEN_IN_FOCUSED_WINDOW, loadKeyStrokeMap(game));
				
				/* Adiciona um componente-filho */
				janelaPrincipal.add(arena);

				/*
				 * Utilizando preferredSize, pack() garante que a janela terá o tamanho
				 * necessário para comportar os componentes internos a ela.
				 */
				janelaPrincipal.pack();
				/*
				 * Indica que a posição da janela será relativa ao ao componente indicado. No
				 * caso nulo, a janela será posicionada no centro da tela.
				 */
				janelaPrincipal.setLocationRelativeTo(null);
				janelaPrincipal.setVisible(true);				
			}

			private Map<KeyStroke, Action> loadKeyStrokeMap(Observavel observavel) {
				Map<KeyStroke, Action> map = new HashMap<KeyStroke, Action>();

				for (CommandSet set : CommandSet.values())
				for (Command command : Command.getCommands(set)) {
					for (int key : command.getKeys()) {
						map.put(KeyStroke.getKeyStroke(key, 0), new KeyAction(observavel, command));
					}
				}
				
				return map;
			}
			
			private int getQuantidadeJogadores() {

				String[] values = {"2", "4"};

				Object selected = JOptionPane.showInputDialog(null, "Quantas pessoas vão jogar?", "Selecione a quantidade de players", JOptionPane.DEFAULT_OPTION, null, values, "0");
				
				if ( selected != null ){//null if the user cancels. 
					return Integer.valueOf(selected.toString());
				}
				
				return 2;
			}
		});
	}
}