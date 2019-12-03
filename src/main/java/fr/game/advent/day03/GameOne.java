package fr.game.advent.day03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<List<Move>, Integer> {
	
	private static final String INPUT_FILENAME = "day03/input-day03-1";
	
	public static final Function<String, List<Move>> MAPPER = s -> Arrays.stream(s.split(",")).map(Move::new).collect(Collectors.toList());
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, MAPPER);
	}
	
	private Set<Point> getParcoursWire(List<Move> path) {
		Set<Point> parcours = new HashSet<>();
		Point currentPoint = Point.getOrigine();
		for (Move move : path) {
			for (int step = 0; step < move.getNumberofSteps(); step++) {
				currentPoint = currentPoint.applyDirectionStep(move.getDirection());
				parcours.add(currentPoint);
			}
		}		
		return parcours;
	}


	@Override
	public Integer play(List<List<Move>> listOfInputs) {
		Set<Point> parcoursWire1 = getParcoursWire(listOfInputs.get(0));
		Set<Point> parcoursWire2 = getParcoursWire(listOfInputs.get(1));
		Set<Point> communParcours =  parcoursWire1.stream().filter(parcoursWire2::contains).collect(Collectors.toSet());
		return communParcours.stream().peek(System.out::println).mapToInt(Point::distanceToOrigin).min().getAsInt();
	}
}
