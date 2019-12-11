package fr.game.advent.day11;

import java.util.HashSet;
import java.util.Set;

public class Panel {

	private Set<ColoredPoint> visitedPoints;

	public Panel() {
		this.visitedPoints = new HashSet<>();
	}
	
	public ColoredPoint moveDirection(ColoredPoint currentPosition, Direction direction) {
		int dx = direction.getMove().getX();
		int dy = direction.getMove().getY();
		final ColoredPoint cible = new ColoredPoint(currentPosition.getX() + dx, currentPosition.getY() + dy, ColoredPoint.BLACK);
		return visitedPoints.stream().filter(v -> v.equals(cible)).findFirst().orElse(cible);
	}
	
	
}
