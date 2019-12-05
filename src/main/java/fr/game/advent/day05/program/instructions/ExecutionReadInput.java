package fr.game.advent.day05.program.instructions;

import java.util.Scanner;

import fr.game.advent.day05.program.Program;

public class ExecutionReadInput implements Execution {

	@Override
	public Integer execute(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIP(1);
		System.out.print("Enter input >");
		Integer operande2;
		try (Scanner scanner = new Scanner(System.in) ){
			operande2 = scanner.nextInt();
		} 
		
		System.out.print("  READ -");
		System.out.println(String.format(" op1=[%d] -> %d", operande1, operande2));

		program.writeMemory(operande1, operande2);		
		program.shiftIP(+2);
		return null;
	}

}
