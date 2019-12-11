package fr.game.advent.day11;

public class ColoredPoint extends Point {

	public static final Integer BLACK = 0;
	public static final Integer WHITE = 1;
	public static final Integer DEFAULT_COLOR = WHITE;

	private int color;

	public ColoredPoint(int x, int y) {
		super(x, y);
		this.color = DEFAULT_COLOR;
	}

	public ColoredPoint(int x, int y, int color) {
		super(x, y);
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return String.format("ColoredPoint [color=%s, X=%s, Y=%s]", color, getX(), getY());
	}

	
	
}
