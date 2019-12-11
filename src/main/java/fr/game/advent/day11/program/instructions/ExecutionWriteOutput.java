package fr.game.advent.day11.program.instructions;

import fr.game.advent.day11.program.Program;

public class ExecutionWriteOutput extends Execution implements ExecutionInterface {

	@Override
	public Long execute(Program program, Parameter[] parameters) {
		Long[] operandes = getParametersValues(program, parameters);
		program.getWriter().println(operandes[0]);
		program.getWriter().flush();
		
		StringBuffer sb = new StringBuffer("WRITE-OUT - ")
				.append(program.parameterToString(parameters[0]));
		log.info(sb.toString());

		
		program.shiftIP(parameters.length + 1L);
		return operandes[0];
	}

}
