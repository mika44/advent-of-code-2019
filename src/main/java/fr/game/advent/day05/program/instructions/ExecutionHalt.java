package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public class ExecutionHalt implements Execution {

	@Override
	public Integer execute(Program program, Integer[] parameterModes) {
		// Do nothing
		System.out.print("  HALT");
		return null;
	}

}
