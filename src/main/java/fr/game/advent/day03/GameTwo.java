package fr.game.advent.day03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<List<Move>, Integer> {
	
	private static final String INPUT_FILENAME = "day03/input-day03-1";
	
	public static final Function<String, List<Move>> MAPPER = s -> Arrays.stream(s.split(",")).map(Move::new).collect(Collectors.toList());
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, MAPPER);
	}
	
	private Set<PointWithNumberOfStep> getParcoursWire(List<Move> path) {
		Set<PointWithNumberOfStep> parcours = new HashSet<>();
		PointWithNumberOfStep pointCourant = new PointWithNumberOfStep(Point.getOrigine(), 0);
		int numberOfStepToReachCurrentPoint = 0;
		for (Move move : path) {
			for (int step = 0; step < move.getNumberofSteps(); step++) {
				numberOfStepToReachCurrentPoint++;
				pointCourant = new PointWithNumberOfStep(pointCourant.getPoint().applyDirectionStep(move.getDirection()), numberOfStepToReachCurrentPoint);
				if (!parcours.contains(pointCourant)) {
					parcours.add(pointCourant);
				}
			}
		}		
		return parcours;
	}


	@Override
	public Integer play(List<List<Move>> listOfInputs) {
		Set<PointWithNumberOfStep> parcoursWire1 = getParcoursWire(listOfInputs.get(0));
		Set<PointWithNumberOfStep> parcoursWire2 = getParcoursWire(listOfInputs.get(1));
		Set<PointWithNumberOfStep> croisements =  parcoursWire1.stream().filter(parcoursWire2::contains).collect(Collectors.toSet());
		
		return croisements.stream()
				.map(p -> new Point( parcoursWire1.stream().filter(p::equals).findFirst().get().getNumberOfStep(), 
						             parcoursWire2.stream().filter(p::equals).findFirst().get().getNumberOfStep() ) )
				.mapToInt(p -> p.getX() + p.getY())
				.min()
				.getAsInt();
	}
}
