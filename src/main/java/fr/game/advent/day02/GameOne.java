package fr.game.advent.day02;

import java.util.List;
import java.util.function.Function;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<String, Integer> {
	
	private static final String INPUT_FILENAME = "day02/input-day02-1";
	
	/**
	 * Le mapper est la fonction identité : on traite directemet chaque ligne du fichier d'input dans cette partie.
	 */
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Function.identity());
	}

	@Override
	public Integer play(List<String> listOfInputs) {
		// TODO Auto-generated method stub
		return null;
	}

}
