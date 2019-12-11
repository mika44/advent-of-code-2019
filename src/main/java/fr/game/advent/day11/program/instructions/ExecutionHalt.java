package fr.game.advent.day11.program.instructions;

import fr.game.advent.day11.program.Program;

public class ExecutionHalt extends Execution implements ExecutionInterface {

	@Override
	public Long execute(Program program, Parameter[] parameters) {
		// Do nothing
		log.info("HALT");
		return null;
	}

}
