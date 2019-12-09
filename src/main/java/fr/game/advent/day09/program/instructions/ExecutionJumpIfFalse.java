package fr.game.advent.day09.program.instructions;

import fr.game.advent.day09.program.Program;

public class ExecutionJumpIfFalse extends Execution implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		Integer[] operandes = getParametersValues(program, parameters);
		
//		System.out.print("  JMPIF -");
//		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryIPOffset(1) + "]" : program.readMemoryIPOffset(1), operande1));
//		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryIPOffset(2) + "]" : program.readMemoryIPOffset(2), operande2));
//		System.out.println(" IP -> " + (operande1 == 0 ? operande2 : "IP + 3"));
		
		if (operandes[0] == 0) {
			program.setIP(operandes[1]);
		} else {
			program.shiftIP(parameters.length + 1);
		}
		return null;
	}

}
