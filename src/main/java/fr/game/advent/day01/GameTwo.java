package fr.game.advent.day01;

import java.util.List;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<Integer, Integer> {
	
	/**
	 * Nom du fichier d'inputs à lire
	 */
	private static final String INPUT_FILENAME = "day01/input-day01-1";
	
	/**
	 * On étend la classe abstraite AbstractGame.
	 * On utilise la méthode basique de construction des inputs.
	 * Et on mappe chaque ligne vers un integer avec le constructeur new de la classe Integer.
	 */
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Integer::new);
	}

	@Override
	public Integer play(List<Integer> listOfInputs) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
