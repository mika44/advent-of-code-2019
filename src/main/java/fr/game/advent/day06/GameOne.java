package fr.game.advent.day06;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<Relationship, Integer> {

	private static final String INPUT_FILENAME = "day06/input-day06-1";

	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Relationship::fromString);
	}

	private Map<String, String> orbits;
	private Map<String, Set<String>> orbitsByFather;

	@Override
	public Integer play(List<Relationship> orbitsList) {
		orbits = orbitsList.stream().collect(Collectors.toMap(Relationship::getInOrbitAround, Relationship::getCenter));
		orbitsByFather = orbitsList.stream().collect(Collectors.groupingBy(Relationship::getCenter,
													 Collectors.mapping(Relationship::getInOrbitAround, Collectors.toSet())));
		System.out.println(orbits);
		System.out.println(orbitsByFather);
		return countOrbits();
	}

	private Integer countOrbits() {
		int sumNumberOfOrbits = 0;

		int currentNumberOfOrbits = 1;
		System.out.println(getCom());
		Set<String> currentObjects = orbitsByFather.get(getCom());
		while (!currentObjects.isEmpty()) {
			System.out.println(currentNumberOfOrbits);
			System.out.println(currentObjects);
			sumNumberOfOrbits = sumNumberOfOrbits + currentNumberOfOrbits * currentObjects.size();
			System.out.println(sumNumberOfOrbits);
			currentNumberOfOrbits++;
			currentObjects = getChildren(currentObjects);
		}
		return sumNumberOfOrbits;
	}

	public String getCom() {
		return orbits.values().parallelStream().filter(o -> !orbits.keySet().contains(o)).findFirst().get();
	}

	private Set<String> getChildren(Set<String> fathers) {
		return fathers.stream().map(orbitsByFather::get).filter(s -> s != null).flatMap(Set::stream).collect(Collectors.toSet());
	}

}
