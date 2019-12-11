package fr.game.advent.day11.program.instructions;

import fr.game.advent.day11.program.Program;

public class ExecutionAdjustRelativeBase extends Execution implements ExecutionInterface {

	@Override
	public Long execute(Program program, Parameter[] parameters) {
		Long[] operandes = getParametersValues(program, parameters);
		
		StringBuffer sb = new StringBuffer("ADJRB - BR <- BR + ")
				.append(program.parameterToString(parameters[0]));
		log.info(sb.toString());
		
		program.shiftRelativeBase(operandes[0]);
		
		program.shiftIP(parameters.length + 1L);
		return null;
	}

}
