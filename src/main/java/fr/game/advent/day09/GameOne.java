package fr.game.advent.day09;

import java.util.List;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<Long, Long> {
	
	private static final String INPUT_FILENAME = "day09/input-day09-1";
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Long::new);
	}

	@Override
	public Long play(List<Long> listOfInputs) {
		return null;
	}
}