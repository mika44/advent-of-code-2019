package fr.game.advent.day11.program.services.implementation;

import fr.game.advent.day11.program.model.Parameter;
import fr.game.advent.day11.program.model.Program;
import fr.game.advent.day11.program.services.ExecutionI;

public class ExecutionJumpIfTrue extends Execution implements ExecutionI {

	@Override
	public Long execute(Program program, Parameter[] parameters, Long[] operandes) {
		if (operandes[0] != 0L) {
			program.setIP(operandes[1]);
		} else {
			program.shiftIP(parameters.length + 1L);
		}
		return null;
	}

	@Override
	protected String logInfo(Program program, Parameter[] parameters, Long[] operandes) {
		return new StringBuffer("JIT - IP->")
				.append(parameterServices.parameterToString(parameters[1], program))
				.append(" IF ")
				.append(parameterServices.parameterToString(parameters[0], program))
				.append(" != 0  = ")
				.append(operandes[0] != 0L)
				.toString();
	}

}
