package fr.game.advent.day05;

import java.util.HashMap;
import java.util.Map;

public enum OperationCode {
	
	ADD(1, 3),
	MUL(2, 3),
	READ(3, 1),
	WRITE(4, 1),
	JMPIT(5, 2),
	JMPIF(6, 2),
	LESS(7, 3),
	EQU(8, 3),
	HALT(99, 0);
	

	private int opcode;
	private int parameterNumber;
	private InstructionExecution instructionExecution;
	
	private OperationCode(int opcode, int parameterNumber) {
		this.opcode = opcode;
		this.parameterNumber = parameterNumber;
	}

	private OperationCode(int opcode, int parameterNumber, InstructionExecution instructionExecution) {
		this.opcode = opcode;
		this.parameterNumber = parameterNumber;
		this.instructionExecution = instructionExecution;
	}

	public int getParameterNumber() {
		return parameterNumber;
	}

	public InstructionExecution getInstructionExecution() {
		return instructionExecution;
	}


	private static Map<Integer, OperationCode> operationCodesByOpcode;
	
	private static void fillOperationCodesByOpcode() {
		operationCodesByOpcode = new HashMap<>();
		for (OperationCode operationCode : OperationCode.values()) {
			operationCodesByOpcode.put(operationCode.opcode, operationCode);
		}
	}

	public static OperationCode getOperationCode(Integer opcode) {
		if (operationCodesByOpcode == null) {
			fillOperationCodesByOpcode();
		}
		return operationCodesByOpcode.get(opcode);
	}

}
