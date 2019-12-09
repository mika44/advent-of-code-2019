package fr.game.advent.day09;

import java.io.ByteArrayInputStream;
import java.util.List;

import fr.game.advent.day09.program.Program;
import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<Long, Long> {
	
	private static final String INPUT_FILENAME = "day09/input-day09-1";

	private static final String NEW_LINE = "\n";

	public GameTwo() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Long::new);
	}


	@Override
	public Long play(List<Long> intcodeProgram) {
		String inputMessage = "" + 2 + NEW_LINE;
		Program program = new Program(intcodeProgram, new ByteArrayInputStream(inputMessage.getBytes()));
		return program.execute();
	}
}
