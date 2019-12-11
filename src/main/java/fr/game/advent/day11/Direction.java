package fr.game.advent.day11;

public enum Direction {

	UP 		(0, +1),
	DOWN	(0, -1),
	RIGHT	(+1, 0),
	LEFT	(-1, 0);
	
	private Point move;
	
	private Direction(int dx, int dy) {
		this.move = new Point(dx, dy);
	}
	
	public Point getMove() {
		return move;
	}



	public static final int TURN_TO_LEFT 	= 0;
	public static final int TURN_TO_RIGHT	= 1;
	
	public static Direction turn(Direction initialDirection, int turnDirection) {
		switch (initialDirection) {
		case UP :		return (turnDirection == TURN_TO_LEFT) ? LEFT : RIGHT;
		case DOWN :		return (turnDirection == TURN_TO_LEFT) ? RIGHT : LEFT; 
		case RIGHT :	return (turnDirection == TURN_TO_LEFT) ? UP : DOWN; 
		case LEFT :		return (turnDirection == TURN_TO_LEFT) ? DOWN : UP; 
		}
		return null;
	}
}
