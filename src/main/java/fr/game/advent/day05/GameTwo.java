package fr.game.advent.day05;

import java.util.List;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<Long, Long> {
	
	private static final String INPUT_FILENAME = "day05/input-day05-1";
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Long::new);
	}

	@Override
	public Long play(List<Long> listOfInputs) {
		return null;
	}
}
