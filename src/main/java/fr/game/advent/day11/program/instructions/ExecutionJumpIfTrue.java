package fr.game.advent.day11.program.instructions;

import fr.game.advent.day11.program.Program;

public class ExecutionJumpIfTrue extends Execution implements ExecutionInterface {

	@Override
	public Long execute(Program program, Parameter[] parameters) {
		Long[] operandes = getParametersValues(program, parameters);
		
		StringBuffer sb = new StringBuffer("JIT - IP->")
				.append(program.parameterToString(parameters[1]))
				.append(" IF ")
				.append(program.parameterToString(parameters[0]))
				.append(" != 0  = ")
				.append(operandes[0] != 0L);
		log.info(sb.toString());
		
		if (operandes[0] != 0L) {
			program.setIP(operandes[1]);
		} else {
			program.shiftIP(parameters.length + 1L);
		}
		return null;
	}

}
