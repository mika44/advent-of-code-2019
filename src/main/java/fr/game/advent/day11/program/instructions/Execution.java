package fr.game.advent.day11.program.instructions;

import java.util.logging.Logger;

import fr.game.advent.day11.program.Program;
import fr.game.utils.LoggerUtils;

public abstract class Execution implements ExecutionInterface {
	
	protected Logger log;

	public Execution() {
		log = LoggerUtils.getLogger();
	}
	
	protected Long[] getParametersValues(Program program, Parameter[] parameters) {
		Long[] operandes = new Long[parameters.length];
		for (int i = 0; i < operandes.length; i++) {
			operandes[i] = program.getParameterValue(parameters[i]);
		}
		return operandes;
	}

}
