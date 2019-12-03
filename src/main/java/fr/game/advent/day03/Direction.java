package fr.game.advent.day03;

public enum Direction {

	R(+1, 0),
	L(-1, 0),
	U(0, +1),
	D(0, -1);
	
	private int dx;
	private int dy;
	
	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public static Direction setDirectionFromPath(String path) {
		switch (path.charAt(0)) {
		case 'R': return R;
		case 'L': return L;
		case 'U': return U;
		case 'D': return D;
		}
		return null;
	}	
}
