package fr.game.advent.day10;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<List<Boolean>, Integer> {
	
	private static final String INPUT_FILENAME = "day10/input-day10-1";
	
	
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, s -> s.chars().mapToObj(c -> c == '#').collect(Collectors.toList()));
	}

	@Override
	public Integer play(List<List<Boolean>> spatialMap) {
		Point instantMonitoringStation = getInstantMonitoringStation(spatialMap);
		Point vaporized200th = getVaporized200th(spatialMap, instantMonitoringStation);
		return vaporized200th.getX() * 100 + vaporized200th.getY();
	}

	
	private Point getVaporized200th(List<List<Boolean>> spatialMap, Point instantMonitoringStation) {
		// Premier point vaporisé virtuel en dehors du plan pour initialiser le process
		Point lastVaporized = new Point(instantMonitoringStation.getX(), -1);
		// La recherche ne se fait que sur les visibles
		Set<Point> visibles = getVisibles(spatialMap, instantMonitoringStation);
		Point newVisiblePoint = null;
		int vaporizedNumber = 0;
		while (vaporizedNumber < 200) {
			vaporizedNumber++;
			Point nextVaporized = getNextToVaporized(spatialMap, instantMonitoringStation, lastVaporized, visibles);
			System.out.println(" The " + vaporizedNumber + " asteroid to be vaporized is at " + nextVaporized);
			lastVaporized = nextVaporized;
			spatialMap.get(nextVaporized.getY()).set(nextVaporized.getX(), false);
			// Suppression point vaporized des visibles
			visibles.remove(nextVaporized);
			// Ajout éventuel nouveau point visible en décalé 
			if (newVisiblePoint != null) visibles.add(newVisiblePoint);
			// Recherche éventuel nouveau point visible suite à vaporisation
			newVisiblePoint = findEventualNewVisiblePoint(spatialMap, instantMonitoringStation, visibles);
		}
		return lastVaporized;
	}

	private Point findEventualNewVisiblePoint(List<List<Boolean>> spatialMap, Point instantMonitoringStation,
			Set<Point> visibles) {
		Point newVisiblePoint;
		Set<Point> updateVisibles = getVisibles(spatialMap, instantMonitoringStation);
		updateVisibles.removeAll(visibles);
		newVisiblePoint = updateVisibles.isEmpty() ? null : updateVisibles.iterator().next();
		return newVisiblePoint;
	}

	private Point getNextToVaporized(List<List<Boolean>> spatialMap, Point instantMonitoringStation, Point lastVaporized, Set<Point> visibles) {
		Point nextToVaporize = null;
		double minAngle = 2 * Math.PI;
		for (Point visiblePoint : visibles) {
			double angle = angleDifference(instantMonitoringStation, lastVaporized, visiblePoint);
			//System.out.println("IMS = " + instantMonitoringStation + " - Angle difference entre " + lastVaporized + " et " + visiblePoint + " vaut " + angle);
			if (angle < minAngle) {
				//System.out.println("Point " + visiblePoint + " devient minimum avec angle égal à " + angle);
				minAngle = angle;
				nextToVaporize = visiblePoint;
			}
		}
		return nextToVaporize;
	}

	private double angleDifference(Point center, Point pointA, Point pointB) {
		double angleDifference =  Math.atan2(pointB.getY() - center.getY(), pointB.getX() - center.getX()) 
								- Math.atan2(pointA.getY() - center.getY(), pointA.getX() - center.getX());
		if (angleDifference < 0) angleDifference = angleDifference + 2 * Math.PI;
		return angleDifference;
	}

	private Point getInstantMonitoringStation(List<List<Boolean>> spatialMap) {
		Point instantMonitoringStation = null;
		Integer maxVisible = -1;
		for (int i = 0; i < spatialMap.size(); i++) {
			List<Boolean> row = spatialMap.get(i);
			for (int j = 0; j < row.size(); j++) {
				if (row.get(j)) {
					Point pointCourant = new Point(j, i);
					Integer currentVisibleNumber = getVisibles(spatialMap, pointCourant).size();
					if (currentVisibleNumber > maxVisible) {
						maxVisible = currentVisibleNumber;
						instantMonitoringStation = pointCourant;
					}
				}
			}
		}
		return instantMonitoringStation;
	}

	private Set<Point> getVisibles(List<List<Boolean>> spatialMap, Point pointCentre) {
		Set<Point> invisibles = new HashSet<>();
		Set<Point> visibles = new TreeSet<>();
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
		return visibles;
	}

	private boolean isVisibleFromPointCentre(Point pointCentre, Point pointCourant, List<List<Boolean>> spatialMap) {
		for (int i = 0; i < spatialMap.size(); i++) {
			List<Boolean> row = spatialMap.get(i);
			for (int j = 0; j < row.size(); j++) {
				if (row.get(j)) {
					Point pointTest = new Point(j, i);
					if (!pointCourant.equals(pointTest)
						&& !pointTest.equals(pointCentre)
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
