package fr.game.advent.day07.program.instructions;

import fr.game.advent.day07.program.Program;

public class ExecutionWriteOutput implements Execution {

	@Override
	public Integer execute(Program program, Mode[] parameterModes) {
		Integer operande1 = program.readMemoryIPOffset(1, parameterModes[0]);
		program.getWriter().println(operande1);
		program.getWriter().flush();
		
		System.out.println(String.format("%s - WRITE OUTPUT - op1=%d", program.getName(), operande1));
		 				
		program.shiftIP(+2);
		return operande1;
	}

}
