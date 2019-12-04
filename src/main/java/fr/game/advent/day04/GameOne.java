package fr.game.advent.day04;

import java.util.List;
import java.util.stream.Stream;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<Integer, Long> {
	
	/**
	 * Nom du fichier d'inputs à lire
	 */
	private static final String INPUT_FILENAME = "day04/input-day04-1";
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Integer::new);
	}

	private Boolean isPassword(Integer value) {
		return true;
	}
	
	@Override
	public Long play(List<Integer> listOfInputs) {
		Integer begin = listOfInputs.get(0);
		Integer end = listOfInputs.get(1);
		return Stream.iterate(begin, i -> i + 1).limit(end - begin + 1)
			.filter(this::isPassword)
			.count()
			;
	}


}
