package com.ferias.game.pernasdepau.model;

import java.awt.Color;
import java.awt.Point;
import java.util.Objects;

public abstract class AbstractMovebleGameItem implements MovebleGameItem{

	
	private int stepSize = 10;
	protected int x = 0, y = 0;
	protected int xAnterior, yAnterior;
	private int name;
	private int r, g, b;
	
	public AbstractMovebleGameItem(int name, Point posicaoInicial) {
		this.name = name;
		x = posicaoInicial.x;
		y = posicaoInicial.y;
		setPosicoesAnteriores();
	}
	
	@Override
	public int getName() {
		return name;
	}

	@Override
	public int x() {
		return this.x;
	}

	@Override
	public int y() {
		return this.y;
	}
	
	@Override
	public boolean hasCollision(MovebleGameItem item) {
		if (this.x == item.x() && this.y == item.y()) {
			return true;
		}
		return false;
	}

	@Override
	public void verticalMove(int y) {
		setPosicoesAnteriores();
		this.y += y;
	}

	@Override
	public void horizontalMove(int x) {
		setPosicoesAnteriores();
		this.x += x;
	}

	@Override
	public Color getColor() {
		return new Color(r, g, b);
	}
	
	@Override
	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void setColor(Color color) {
		setColor(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractMovebleGameItem other = (AbstractMovebleGameItem) obj;
		return name == other.name;
	}

	private void setPosicoesAnteriores() {
		this.xAnterior = this.x;
		this.yAnterior = this.y;
	}

	public int stepSize() {
		return stepSize;
	}

	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}
	
	public int size() {
		return SIZE;
	}
}
