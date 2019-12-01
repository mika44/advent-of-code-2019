package fr.game.advent.day01;

import java.util.List;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<Long, Long> {
	
	/**
	 * Nom du fichier d'inputs à lire
	 */
	private static final String INPUT_FILENAME = "day01/input-day01-1";
	
	/**
	 * On étend la classe abstraite AbstractGame.
	 * On utilise la méthode basique de construction des inputs.
	 * Et on mappe chaque ligne vers un integer avec le constructeur new de la classe Integer.
	 */
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Long::new);
	}

	@Override
	public Long play(List<Long> listOfInputs) {
		return listOfInputs.stream()
				.mapToLong(this::getFuelRequiredFromMass)
				.sum()
				;
	}

	private Long getFuelRequiredFromMass(Long mass) {
		return (mass / 3L) - 2L;
	}
}
