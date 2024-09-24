package com.ferias.game.pernasdepau.service;

import com.ferias.game.pernasdepau.frontView.Observador;
import com.ferias.game.pernasdepau.input.Command;

public interface Observavel {

	void update(Command command);

	void addObservador(Observador observador);

	void removeObservador(Observador observador);

}