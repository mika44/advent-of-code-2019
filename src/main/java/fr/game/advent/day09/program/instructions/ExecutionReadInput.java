package fr.game.advent.day09.program.instructions;

import java.io.IOException;

import fr.game.advent.day09.program.Program;


public class ExecutionReadInput extends Execution implements ExecutionInterface {

	@Override
	public Long execute(Program program, Parameter[] parameters) {
		Long[] operandes = getParametersValues(program, parameters);
		
		Long input;
		try {
			String inputString = null;
			while (inputString == null) {
				inputString = program.getReader().readLine();
			}
			input = Long.valueOf(inputString);
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
		
		StringBuffer sb = new StringBuffer("READ-IN - ")
				.append(program.parameterToString(parameters[0]))
				.append(" <- ")
				.append(input);
		log.info(sb.toString());
	
		
		program.writeMemory(operandes[0], input);		
		program.shiftIP(parameters.length + 1L);
		return null;
	}

}
