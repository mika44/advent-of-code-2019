package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public class ExecutionWriteOutput extends Execution implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		Integer[] operandes = getParametersValues(program, parameters);

		System.out.println(" " + operandes[0]);		
		
//		System.out.print("  WRITE -");
//		System.out.println(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryIPOffset(1) + "]" : program.readMemoryIPOffset(1), operande1));

		program.shiftIP(parameters.length + 1);
		return operandes[0];
	}

}
