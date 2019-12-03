package fr.game.advent.day03;

public class Move {
	
	private Direction direction;
	private int numberofSteps;

	public Move(String path) {
		this.direction = Direction.setDirectionFromPath(path);
		this.numberofSteps = Integer.valueOf(path.substring(1));
	}

	public Direction getDirection() {
		return direction;
	}

	public int getNumberofSteps() {
		return numberofSteps;
	}
	
	
}
