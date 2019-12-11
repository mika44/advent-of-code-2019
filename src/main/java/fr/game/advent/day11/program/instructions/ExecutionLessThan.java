package fr.game.advent.day11.program.instructions;

import fr.game.advent.day11.program.Program;

public class ExecutionLessThan extends Execution implements ExecutionInterface {

	@Override
	public Long execute(Program program, Parameter[] parameters) {
		Long[] operandes = getParametersValues(program, parameters);
		
		StringBuffer sb = new StringBuffer("LESS - ")
				.append(program.parameterToString(parameters[2]))
				.append(" <- ")
				.append(program.parameterToString(parameters[0]))
				.append(" < ")
				.append(program.parameterToString(parameters[1]))
				.append(" = ")
				.append(operandes[0] < operandes[1] ? 1L : 0L);
		log.info(sb.toString());
		
				
		program.writeMemory(operandes[2], operandes[0] < operandes[1] ? 1L : 0L);
		program.shiftIP(parameters.length + 1L);
		return null;
	}

}
