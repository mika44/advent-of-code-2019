package fr.game.advent.day07.program.instructions;

import fr.game.advent.day07.program.Program;

public class ExecutionLessThan implements Execution {

	@Override
	public Integer execute(Program program, Mode[] parameterModes) {
		Integer operande1 = program.readMemoryIPOffset(1, parameterModes[0]);
		Integer operande2 = program.readMemoryIPOffset(2, parameterModes[1]);
		Integer operande3 = program.readMemoryIPOffset(3);
		
//		System.out.print("  LESS -");
//		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryIPOffset(1) + "]" : program.readMemoryIPOffset(1), operande1));
//		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryIPOffset(2) + "]" : program.readMemoryIPOffset(2), operande2));
//		System.out.println(String.format(" op3=[%d] -> %d", operande3, operande1 < operande2 ? 1 : 0));
		
		program.writeMemory(operande3, operande1 < operande2 ? 1 : 0);
		program.shiftIP(+4);
		return null;
	}

}
