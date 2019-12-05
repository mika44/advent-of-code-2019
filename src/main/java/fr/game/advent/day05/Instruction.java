package fr.game.advent.day05;

import java.util.Arrays;
import java.util.Scanner;

public class Instruction {

	private Integer instructionCode;
	private OperationCode opcode;
	private Integer[] parameterModes;
	
	public Instruction(Integer instructionCode) {
		this.instructionCode = instructionCode;
		this.opcode = OperationCode.getOperationCode(instructionCode % 100);
		this.parameterModes =  buildParameterModes(instructionCode, this.opcode.getParameterNumber());
	}
	
	private Integer[] buildParameterModes(Integer instructionCode, Integer parameterNumber) {
		Integer[] resultat = new Integer[parameterNumber];
		Integer parameterModesCode = instructionCode / 100;
		for (int i = 0; i < parameterNumber; i++) {
			resultat[i] = parameterModesCode % 10;
			parameterModesCode = parameterModesCode / 10;
		}
		return resultat;
	}
	
	public boolean isHaltInstruction() {
		return OperationCode.HALT.equals(opcode);
	}
	
	

	@Override
	public String toString() {
		return String.format("Instruction [instructionCode=%s, opcode=%s, parameterModes=%s]", instructionCode, opcode, Arrays.toString(parameterModes));
	}

	public Integer execute(Program program) {
		switch (opcode) {
		case ADD: return executeAdd(program, parameterModes);
		case MUL: return executeMul(program, parameterModes);
		case READ: return executeRead(program, parameterModes);
		case WRITE: return executeWrite(program, parameterModes);
		case JMPIT: return executeJumpIfTrue(program, parameterModes);
		case JMPIF: return executeJumpIfFalse(program, parameterModes);
		case LESS: return executeLessThan(program, parameterModes);
		case EQU: return executeEqual(program, parameterModes);
		default: return null;
		}
	}
	
	public Integer executeAdd(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		Integer operande2 = program.readMemoryFromIPWithMode(2, parameterModes[1]);
		Integer operande3 = program.readMemoryFromIP(3);
		
		System.out.print("  ADD -");
		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));
		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryFromIP(2) + "]" : program.readMemoryFromIP(2), operande2));
		System.out.println(String.format(" op3=[%d] -> %d", operande3, operande1 + operande2));
		
		program.writeMemory(operande3, operande1 + operande2);
		program.shiftIP(+4);
		return null;
	}

	public Integer executeMul(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		Integer operande2 = program.readMemoryFromIPWithMode(2, parameterModes[1]);
		Integer operande3 = program.readMemoryFromIP(3);
		
		System.out.print("  MUL -");
		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));
		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryFromIP(2) + "]" : program.readMemoryFromIP(2), operande2));
		System.out.println(String.format(" op3=[%d] -> %d", operande3, operande1 * operande2));
		
		program.writeMemory(operande3, operande1 * operande2);
		program.shiftIP(+4);
		return null;
	}
	
	public Integer executeRead(Program program, Integer[] parameterModes) {
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

	public Integer executeWrite(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		System.out.println(" " + operande1);		
		
		System.out.print("  WRITE -");
		System.out.println(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));

		program.shiftIP(+2);
		return operande1;
	}
	
	private Integer executeJumpIfTrue(Program program, Integer[] parameterModes2) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		Integer operande2 = program.readMemoryFromIPWithMode(2, parameterModes[1]);
		
		System.out.print("  JMPIT -");
		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));
		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryFromIP(2) + "]" : program.readMemoryFromIP(2), operande2));
		System.out.println(" IP -> " + (operande1 != 0 ? operande2 : "IP + 3"));
		
		if (operande1 != 0) {
			program.setIP(operande2);
		} else {
			program.shiftIP(+3);
		}
		return null;
	}


	private Integer executeJumpIfFalse(Program program, Integer[] parameterModes2) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		Integer operande2 = program.readMemoryFromIPWithMode(2, parameterModes[1]);
		
		System.out.print("  JMPIF -");
		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));
		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryFromIP(2) + "]" : program.readMemoryFromIP(2), operande2));
		System.out.println(" IP -> " + (operande1 == 0 ? operande2 : "IP + 3"));
		
		if (operande1 == 0) {
			program.setIP(operande2);
		} else {
			program.shiftIP(+3);
		}
		return null;
	}

	private Integer executeLessThan(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		Integer operande2 = program.readMemoryFromIPWithMode(2, parameterModes[1]);
		Integer operande3 = program.readMemoryFromIP(3);
		
		System.out.print("  LESS -");
		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));
		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryFromIP(2) + "]" : program.readMemoryFromIP(2), operande2));
		System.out.println(String.format(" op3=[%d] -> %d", operande3, operande1 < operande2 ? 1 : 0));
		
		program.writeMemory(operande3, operande1 < operande2 ? 1 : 0);
		program.shiftIP(+4);
		return null;
	}

	private Integer executeEqual(Program program, Integer[] parameterModes) {
		Integer operande1 = program.readMemoryFromIPWithMode(1, parameterModes[0]);
		Integer operande2 = program.readMemoryFromIPWithMode(2, parameterModes[1]);
		Integer operande3 = program.readMemoryFromIP(3);

		System.out.print("  EQUAL -");
		System.out.print(String.format(" op1=%s -> %d -", parameterModes[0] == 0 ? "[" + program.readMemoryFromIP(1) + "]" : program.readMemoryFromIP(1), operande1));
		System.out.print(String.format(" op2=%s -> %d -", parameterModes[1] == 0 ? "[" + program.readMemoryFromIP(2) + "]" : program.readMemoryFromIP(2), operande2));
		System.out.println(String.format(" op3=[%d] -> %d", operande3, operande1.equals(operande2) ? 1 : 0));
		
		program.writeMemory(operande3, operande1.equals(operande2) ? 1 : 0);
		program.shiftIP(+4);
		return null;
	}

}
