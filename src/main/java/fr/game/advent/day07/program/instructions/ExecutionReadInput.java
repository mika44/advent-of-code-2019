	package fr.game.advent.day07.program.instructions;

import java.io.IOException;

import fr.game.advent.day07.program.Program;


public class ExecutionReadInput implements Execution {
	
	@Override
	public Integer execute(Program program, Mode[] parameterModes) {
		Integer operande1 = program.readMemoryIPOffset(1);
		Integer operande2;
		try {
			//System.out.println(String.format("%s - READ - en attente", program.getName()));
			String input = null;
			while (input == null) {
				input = program.getReader().readLine();
			}
			//System.out.println(String.format("%s - READ - fin attente", program.getName()));
			operande2 = Integer.valueOf(input);
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
		
		System.out.println(String.format("%s - READ - op1=[%d] -> %d", program.getName(), operande1, operande2));

		program.writeMemory(operande1, operande2);		
		program.shiftIP(+2);
		return null;
	}
}
