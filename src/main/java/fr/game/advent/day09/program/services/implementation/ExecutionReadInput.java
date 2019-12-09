package fr.game.advent.day09.program.services.implementation;

import java.io.IOException;

import fr.game.advent.day09.program.model.Parameter;
import fr.game.advent.day09.program.model.Program;
import fr.game.advent.day09.program.services.ExecutionI;


public class ExecutionReadInput extends Execution implements ExecutionI {

	@Override
	protected Long[] getParametersValues(Program program, Parameter[] parameters) {
		Long[] operandes = new Long[parameters.length + 1];
	
		// Read operandes from memory program
		int indexOperande = 0;
		for (Long operande : super.getParametersValues(program, parameters)) {
			operandes[indexOperande] = operande;
			indexOperande++;
		} 
		
		// Read input and add it to operandes
		try {
			String inputString = null;
			while (inputString == null) {
				inputString = program.getReader().readLine();
			}
			operandes[indexOperande] = Long.valueOf(inputString);
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
		
		return operandes;
	}
	
	@Override
	public Long execute(Program program, Parameter[] parameters, Long[] operandes) {
		program.writeMemory(operandes[0], operandes[1]);		
		program.shiftIP(parameters.length + 1L);
		return null;
	}

	@Override
	protected String logInfo(Program program, Parameter[] parameters, Long[] operandes) {
		return new StringBuffer("READ-IN - ")
				.append(parameterServices.parameterToString(parameters[0], program))
				.append(" <- ")
				.append(operandes[1])
				.toString();
	}

}
