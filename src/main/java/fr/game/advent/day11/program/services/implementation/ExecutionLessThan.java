package fr.game.advent.day11.program.services.implementation;

import fr.game.advent.day11.program.model.Parameter;
import fr.game.advent.day11.program.model.Program;
import fr.game.advent.day11.program.services.ExecutionI;

public class ExecutionLessThan extends Execution implements ExecutionI {

	@Override
	public Long execute(Program program, Parameter[] parameters, Long[] operandes) {
		program.writeMemory(operandes[2], operandes[0] < operandes[1] ? 1L : 0L);
		program.shiftIP(parameters.length + 1L);
		return null;
	}

	@Override
	protected String logInfo(Program program, Parameter[] parameters, Long[] operandes) {
		return new StringBuffer("LESS - ")
				.append(parameterServices.parameterToString(parameters[2], program))
				.append(" <- ")
				.append(parameterServices.parameterToString(parameters[0], program))
				.append(" < ")
				.append(parameterServices.parameterToString(parameters[1], program))
				.append(" = ")
				.append(operandes[0] < operandes[1] ? 1L : 0L)
				.toString();
	}

}
