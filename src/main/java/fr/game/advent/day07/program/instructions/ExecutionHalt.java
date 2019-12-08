package fr.game.advent.day07.program.instructions;

import fr.game.advent.day07.program.Program;

public class ExecutionHalt implements Execution {

	@Override
	public Integer execute(Program program, Mode[] parameterModes) {
		// Do nothing
		System.out.print("  HALT");
		return null;
	}

}
