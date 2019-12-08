package fr.game.advent.day07.program.instructions;

import java.util.HashMap;
import java.util.Map;

public enum Opcode {
	
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
	

	private int intOpcode;
	private int parametersNumber;
	private Execution execution;
	
	private Opcode(int opcode, int parametersNumber, Execution execution) {
		this.intOpcode = opcode;
		this.parametersNumber = parametersNumber;
		this.execution = execution;
	}

	public int getParametersNumber() {
		return parametersNumber;
	}

	public Execution getExecution() {
		return execution;
	}


	private static Map<Integer, Opcode> opcodesByIntOpcode;
	
	private synchronized static void fillOperationCodesByOpcode() {
		opcodesByIntOpcode = new HashMap<>();
		for (Opcode operationCode : Opcode.values()) {
			opcodesByIntOpcode.put(operationCode.intOpcode, operationCode);
		}
	}

	public synchronized static Opcode toOpcode(Integer opcode) {
		if (opcodesByIntOpcode == null) {
			fillOperationCodesByOpcode();
		}
		return opcodesByIntOpcode.get(opcode);
	}

}
