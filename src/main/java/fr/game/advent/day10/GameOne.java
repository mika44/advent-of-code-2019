package fr.game.advent.day10;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<List<Boolean>, Integer> {
	
	private static final String INPUT_FILENAME = "day10/input-day10-1";
	
	
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, s -> s.chars().mapToObj(c -> c == '#').collect(Collectors.toList()));
	}

	@Override
	public Integer play(List<List<Boolean>> spatialMap) {
		Integer maxVisible = -1;
		for (int i = 0; i < spatialMap.size(); i++) {
			List<Boolean> row = spatialMap.get(i);
			for (int j = 0; j < row.size(); j++) {
				if (row.get(j)) {
					Point pointCourant = new Point(j, i);
					Integer currentVisibleNumber = getNumberVisible(spatialMap, pointCourant);
					if (currentVisibleNumber > maxVisible) {
						maxVisible = currentVisibleNumber;
						System.out.println("max visible = " + maxVisible + " pour point " + pointCourant);
					}
				}
			}
		}
		return maxVisible;
	}

	private Integer getNumberVisible(List<List<Boolean>> spatialMap, Point pointCentre) {
		Set<Point> invisibles = new HashSet<>();
		Set<Point> visibles = new HashSet<>();
		invisibles.add(pointCentre);
		for (int i = 0; i < spatialMap.size(); i++) {
			List<Boolean> row = spatialMap.get(i);
			for (int j = 0; j < row.size(); j++) {
				if (row.get(j)) {
					Point pointCourant = new Point(j, i);
					if (!invisibles.contains(pointCourant)) {
						if (isVisibleFromPointCentre(pointCentre, pointCourant, spatialMap)) {
							visibles.add(pointCourant);
						} else {
							invisibles.add(pointCourant);
						}
					}
				}
			}
		}
		return visibles.size();
	}

	private boolean isVisibleFromPointCentre(Point pointCentre, Point pointCourant, List<List<Boolean>> spatialMap) {
		for (int i = 0; i < spatialMap.size(); i++) {
			List<Boolean> row = spatialMap.get(i);
			for (int j = 0; j < row.size(); j++) {
				if (row.get(j)) {
					Point pointTest = new Point(j, i);
					if (!pointCourant.equals(pointTest)
						&& !pointCentre.equals(pointTest)
						&& isHiddenByPointTest(pointCentre, pointCourant, pointTest)) {
							return false;
					}
				}
			}
		}
		return true;
	}

	private boolean isHiddenByPointTest(Point pointCentre, Point pointCourant, Point pointTest) {
		int dx = pointCourant.getX() - pointCentre.getX();
		int dy = pointCourant.getY() - pointCentre.getY();
		int tx = pointTest.getX() - pointCentre.getX();
		int ty = pointTest.getY() - pointCentre.getY();
		return isSameSign(dx, tx) && isSameSign(dy, ty) && isSameTangent(dx, dy, tx, ty) && isFurther(dx, dy, tx, ty);
	}

	
	private boolean isFurther(int dx, int dy, int tx, int ty) {
		return (dx * dx + dy * dy - tx * tx - ty * ty) > 0;
	}

	private boolean isSameTangent(int dx, int dy, int tx, int ty) {
		return (dy * tx - ty * dx) == 0;
	}

	private boolean isSameSign(int a, int b) {
		return a * b >= 0;
	}
}
