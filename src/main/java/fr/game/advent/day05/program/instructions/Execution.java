package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

public abstract class Execution implements ExecutionInterface {
	
	protected Integer[] getParametersValues(Program program, Parameter[] parameters) {
		Integer[] operandes = new Integer[parameters.length];
		for (int i = 0; i < operandes.length; i++) {
			operandes[i] = program.getParameterValue(parameters[i]);
		}
		return operandes;
	}

}
