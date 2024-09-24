package com.ferias.game.pernasdepau.input;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.ferias.game.pernasdepau.service.Observavel;

public class KeyAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private Command command;
	private Observavel game;
	
	public KeyAction(Observavel game, Command command) {
		this.game = game;
		this.command = command;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.update(command);
	}
	
	public String getCommandName() {
		return this.command.name();
	}
	
	@Override
	public String toString() {
		return command.name() + game.toString();
	}
}
