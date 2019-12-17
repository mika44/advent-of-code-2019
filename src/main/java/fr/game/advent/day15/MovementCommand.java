package fr.game.advent.day15;

public enum MovementCommand {

	NORTH	(1L, 0, +1),
	SOUTH	(2L, 0, -1),
	WEST	(3L, +1, 0),
	EAST 	(4L, -1, 0);
	
	private long movementCommandValue; 
	private Point move;
	
	private MovementCommand(long movementCommandValue, long dx, long dy) {
		this.movementCommandValue = movementCommandValue;
		this.move = new Point(dx, dy);
	}
	
	public Point getMove() {
		return move;
	}
	
	public long getMovementCommandValue() {
		return movementCommandValue;
	}

	public static MovementCommand turnToLeft(MovementCommand initialMovementCommand) {
		switch (initialMovementCommand) {
		case NORTH :	return EAST;
		case EAST :		return SOUTH; 
		case SOUTH :	return WEST; 
		case WEST :		return NORTH; 
		}
		return null;
	}
	
	public static MovementCommand opposite(MovementCommand initialMovementCommand) {
		return turnToLeft(turnToLeft(initialMovementCommand));
	}

}
