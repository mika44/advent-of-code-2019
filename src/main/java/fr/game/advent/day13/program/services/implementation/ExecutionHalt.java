package fr.game.advent.day13.program.services.implementation;

import fr.game.advent.day13.program.model.Parameter;
import fr.game.advent.day13.program.model.Program;
import fr.game.advent.day13.program.services.ExecutionI;

public class ExecutionHalt extends Execution implements ExecutionI {

	@Override
	public Long execute(Program program, Parameter[] parameters, Long[] operandes) {
		// Do nothing
		return null;
	}

	@Override
	protected String logInfo(Program program, Parameter[] parameters, Long[] operandes) {
		return "HALT";
	}

}
