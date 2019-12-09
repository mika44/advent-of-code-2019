package fr.game.advent.day09.program.instructions;

import fr.game.advent.day09.program.Program;

public class ExecutionHalt implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		// Do nothing
		System.out.print("  HALT");
		return null;
	}

}
