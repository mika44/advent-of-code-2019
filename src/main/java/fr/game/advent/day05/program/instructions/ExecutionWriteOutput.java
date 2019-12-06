package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public class ExecutionWriteOutput implements Execution {

	@Override
	public Integer execute(Program program, Mode[] parameterModes) {
		Integer operande1 = program.readMemoryIPOffset(1, parameterModes[0]);
		System.out.println(" " + operande1);		
		
//		System.out.print("  WRITE -");
//		System.out.println(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryIPOffset(1) + "]" : program.readMemoryIPOffset(1), operande1));

		program.shiftIP(+2);
		return operande1;
	}

}
