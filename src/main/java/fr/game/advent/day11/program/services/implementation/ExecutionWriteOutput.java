package fr.game.advent.day11.program.services.implementation;

import fr.game.advent.day11.program.model.Parameter;
import fr.game.advent.day11.program.model.Program;
import fr.game.advent.day11.program.services.ExecutionI;

public class ExecutionWriteOutput extends Execution implements ExecutionI {

	@Override
	public Long execute(Program program, Parameter[] parameters, Long[] operandes) {
		program.getWriter().println(operandes[0]);
		program.getWriter().flush();
		program.shiftIP(parameters.length + 1L);
		return operandes[0];
	}

	@Override
	protected String logInfo(Program program, Parameter[] parameters, Long[] operandes) {
		return new StringBuffer("WRITE-OUT - ")
				.append(parameterServices.parameterToString(parameters[0], program))
				.toString();
	}

}
