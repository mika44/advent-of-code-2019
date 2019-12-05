package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public class ExecutionEquals implements Execution {

	@Override
	public Integer execute(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		Integer operande2 = program.readMemoryFromIPWithMode(2, parameterModes[1]);
		Integer operande3 = program.readMemoryFromIP(3);

		System.out.print("  EQUAL -");
		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));
		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryFromIP(2) + "]" : program.readMemoryFromIP(2), operande2));
		System.out.println(String.format(" op3=[%d] -> %d", operande3, operande1.equals(operande2) ? 1 : 0));
		
		program.writeMemory(operande3, operande1.equals(operande2) ? 1 : 0);
		program.shiftIP(+4);
		return null;
	}

}
