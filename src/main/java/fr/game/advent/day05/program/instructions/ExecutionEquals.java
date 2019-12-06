package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public class ExecutionEquals extends Execution implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		Integer[] operandes = getParametersValues(program, parameters);

//		System.out.print("  EQUAL -");
//		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryIPOffset(1) + "]" : program.readMemoryIPOffset(1), operande1));
//		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryIPOffset(2) + "]" : program.readMemoryIPOffset(2), operande2));
//		System.out.println(String.format(" op3=[%d] -> %d", operande3, operande1.equals(operande2) ? 1 : 0));
		
		program.writeMemory(operandes[2], operandes[0].equals(operandes[1]) ? 1 : 0);
		
		program.shiftIP(parameters.length + 1);
		return null;
	}

}
