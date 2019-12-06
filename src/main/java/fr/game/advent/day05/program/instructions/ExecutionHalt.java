package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public class ExecutionHalt implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		// Do nothing
		System.out.print("  HALT");
		return null;
	}

}
