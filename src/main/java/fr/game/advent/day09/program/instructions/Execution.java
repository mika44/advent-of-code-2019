package fr.game.advent.day09.program.instructions;

import fr.game.advent.day09.program.Program;

public abstract class Execution implements ExecutionInterface {
	
	protected Long[] getParametersValues(Program program, Parameter[] parameters) {
		Long[] operandes = new Long[parameters.length];
		for (int i = 0; i < operandes.length; i++) {
			operandes[i] = program.getParameterValue(parameters[i]);
		}
		return operandes;
	}

}
