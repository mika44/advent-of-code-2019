package fr.game.advent.day05;

import java.util.List;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<Integer, Integer> {
	
	
	private static final String INPUT_FILENAME = "day05/input-day05-1";
	
	public GameOne() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Integer::new);
	}
	

	@Override
	public Integer play(List<Integer> intcodeProgram) {
		Program program = new Program(intcodeProgram);
		return program.execute();
	}

}