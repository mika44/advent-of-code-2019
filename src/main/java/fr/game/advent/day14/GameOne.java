package fr.game.advent.day14;

import java.util.List;
import java.util.function.Function;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<String, Integer> {
	
	private static final String INPUT_FILENAME = "day14/input-day14-1";
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Function.identity());
	}

	@Override
	public Integer play(List<String> formulas) {
		return 0;
	}
}
