package fr.game.advent.day07.program.instructions;

import fr.game.advent.day07.program.Program;

public class ExecutionJumpIfTrue implements Execution {

	@Override
	public Integer execute(Program program, Mode[] parameterModes) {
		Integer operande1 = program.readMemoryIPOffset(1, parameterModes[0]);
		Integer operande2 = program.readMemoryIPOffset(2, parameterModes[1]);
		
//		System.out.print("  JMPIT -");
//		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryIPOffset(1) + "]" : program.readMemoryIPOffset(1), operande1));
//		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryIPOffset(2) + "]" : program.readMemoryIPOffset(2), operande2));
//		System.out.println(" IP -> " + (operande1 != 0 ? operande2 : "IP + 3"));
		
		if (operande1 != 0) {
			program.setIP(operande2);
		} else {
			program.shiftIP(+3);
		}
		return null;
	}

}
