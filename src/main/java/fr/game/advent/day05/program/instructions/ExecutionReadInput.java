package fr.game.advent.day05.program.instructions;

import java.util.Scanner;

import fr.game.advent.day05.program.Program;

public class ExecutionReadInput extends Execution implements ExecutionInterface {

	@Override
	public Integer execute(Program program, Parameter[] parameters) {
		Integer[] operandes = getParametersValues(program, parameters);
		
		System.out.print("Enter input >");
		Integer input;
		try (Scanner scanner = new Scanner(System.in) ){
			input = scanner.nextInt();
		} 
		
//		System.out.print("  READ -");
//		System.out.println(String.format(" op1=[%d] -> %d", operande1, operande2));

		program.writeMemory(operandes[0], input);		
		program.shiftIP(parameters.length + 1);
		return null;
	}

}
