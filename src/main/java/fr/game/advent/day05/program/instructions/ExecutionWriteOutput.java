package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public class ExecutionWriteOutput implements Execution {

	@Override
	public Integer execute(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		System.out.println(" " + operande1);		
		
		System.out.print("  WRITE -");
		System.out.println(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));

		program.shiftIP(+2);
		return operande1;
	}

}
