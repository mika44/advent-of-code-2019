package fr.game.advent.day03;

import java.util.List;
import java.util.function.Function;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<String, String> {
	
	private static final String INPUT_FILENAME = "day02/input-day02-1";
	
	/**
	 * Idem partie un du même jour.
	 */
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Function.identity());
	}

	@Override
	public String play(List<String> listOfInputs) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
