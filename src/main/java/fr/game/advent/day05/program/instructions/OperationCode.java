package fr.game.advent.day05.program.instructions;

import java.util.HashMap;
import java.util.Map;

public enum OperationCode {
	
	ADD				( 1, 3, new ExecutionAdd()),
	MULTIPLY		( 2, 3, new ExecutionMultiply()),
	READ_INPUT		( 3, 1, new ExecutionReadInput()),
	WRITE_OUTPUT	( 4, 1, new ExecutionWriteOutput()),
	JUMP_IF_TRUE	( 5, 2, new ExecutionJumpIfTrue()),
	JUMP_IF_FALSE	( 6, 2, new ExecutionJumpIfFalse()),
	LESS_THAN		( 7, 3, new ExecutionLessThan()),
	EQUALS			( 8, 3, new ExecutionEquals()),
	HALT			(99, 0, new ExecutionHalt())
	;
	

	private int opcode;
	private int parameterNumber;
	private Execution execution;
	
	private OperationCode(int opcode, int parameterNumber) {
		this.opcode = opcode;
		this.parameterNumber = parameterNumber;
	}

	private OperationCode(int opcode, int parameterNumber, Execution instructionExecution) {
		this.opcode = opcode;
		this.parameterNumber = parameterNumber;
		this.execution = instructionExecution;
	}

	public int getParameterNumber() {
		return parameterNumber;
	}

	public Execution getExecution() {
		return execution;
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
