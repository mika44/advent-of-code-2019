package fr.game.advent.day09.program.instructions;

import fr.game.advent.day09.program.Program;

public class ExecutionWriteOutput extends Execution implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		Integer[] operandes = getParametersValues(program, parameters);
		program.getWriter().println(operandes[0]);
		program.getWriter().flush();
		
		//System.out.println(String.format("%s - WRITE OUTPUT - op1=%d", program.getName(), operande1));

		program.shiftIP(parameters.length + 1);
		return operandes[0];
	}

}
