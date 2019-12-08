package fr.game.advent.day06;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<Relationship, Integer> {

	private static final String INPUT_FILENAME = "day06/input-day06-1";

	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Relationship::fromString);
	}

	private Map<String, Set<String>> neighbours;

	@Override
	public Integer play(List<Relationship> orbitsList) {
		neighbours = new HashMap<>();
		for (Relationship relationship : orbitsList) {
			addObjectToNeighbours(relationship.getCenter(), relationship.getInOrbitAround());
			addObjectToNeighbours(relationship.getInOrbitAround(), relationship.getCenter());
		}
		return distanceBetweenSanAndYou();
	}

	private Integer distanceBetweenSanAndYou() {
		int distance = 0;
		Set<String> newVisited = neighbours.get("YOU");
		Set<String> alreadyVisited = new HashSet<>();
		while (!newVisited.contains("SAN")) {
			distance++;
			alreadyVisited.addAll(newVisited);
			Set<String> nextToVisit = new HashSet<>();
			for (String o : newVisited) {
				nextToVisit.addAll(neighbours.get(o));
			}
			nextToVisit.removeAll(alreadyVisited);
			newVisited = nextToVisit;
		}
		return distance - 1;
	}

	private void addObjectToNeighbours(String you, String neighbour) {
		Set<String> yourNeighbours = neighbours.get(you);
		if (yourNeighbours == null) {
			yourNeighbours = new HashSet<>();
			neighbours.put(you, yourNeighbours);
		}
		yourNeighbours.add(neighbour);
	}

}
