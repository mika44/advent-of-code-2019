package fr.game.advent.day09.program.instructions;

import java.io.IOException;

import fr.game.advent.day09.program.Program;


public class ExecutionReadInput extends Execution implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		Integer[] operandes = getParametersValues(program, parameters);
		
		Integer input;
		try {
			//System.out.println(String.format("%s - READ - en attente", program.getName()));
			String inputString = null;
			while (inputString == null) {
				inputString = program.getReader().readLine();
			}
			//System.out.println(String.format("%s - READ - fin attente", program.getName()));
			input = Integer.valueOf(inputString);
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
		
//		System.out.print("  READ -");
//		System.out.println(String.format(" op1=[%d] -> %d", operande1, operande2));

		program.writeMemory(operandes[0], input);		
		program.shiftIP(parameters.length + 1);
		return null;
	}

}
