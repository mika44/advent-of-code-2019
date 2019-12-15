package fr.game.advent.day13.program.services.implementation;

import fr.game.advent.day13.program.model.Parameter;
import fr.game.advent.day13.program.model.Program;
import fr.game.advent.day13.program.services.ExecutionI;

public class ExecutionAdjustRelativeBase extends Execution implements ExecutionI {

	@Override
	public Long execute(Program program, Parameter[] parameters, Long[] operandes) {
		program.shiftRelativeBase(operandes[0]);
		program.shiftIP(parameters.length + 1L);
		return null;
	}

	@Override
	protected String logInfo(Program program, Parameter[] parameters, Long[] operandes) {
		return new StringBuffer("ADJRB - BR <- BR + ")
				.append(parameterServices.parameterToString(parameters[0], program))
				.toString();
	}

}
