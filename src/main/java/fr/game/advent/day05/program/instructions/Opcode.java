package fr.game.advent.day05.program.instructions;

import static fr.game.advent.day05.program.instructions.ParameterUsage.*;

import java.util.HashMap;
import java.util.Map;

public enum Opcode {
	
	ADD				( 1, new ExecutionAdd(), 			TO_READ, TO_READ, TO_WRITE),
	MULTIPLY		( 2, new ExecutionMultiply(),		TO_READ, TO_READ, TO_WRITE),
	READ_INPUT		( 3, new ExecutionReadInput(),  	TO_WRITE),
	WRITE_OUTPUT	( 4, new ExecutionWriteOutput(),	TO_READ),
	JUMP_IF_TRUE	( 5, new ExecutionJumpIfTrue(),  	TO_READ, TO_READ),
	JUMP_IF_FALSE	( 6, new ExecutionJumpIfFalse(), 	TO_READ, TO_READ),
	LESS_THAN		( 7, new ExecutionLessThan(), 		TO_READ, TO_READ, TO_READ),
	EQUALS			( 8, new ExecutionEquals(), 		TO_READ, TO_READ, TO_READ),
	HALT			(99, new ExecutionHalt())
	;
	

	private int intOpcode;
	private ParameterUsage[] parametersUsage;
	private ExecutionInterface execution;
	
	

	private Opcode(int intOpcode, ExecutionInterface execution, ParameterUsage... parametersUsage) {
		this.intOpcode = intOpcode;
		this.parametersUsage = parametersUsage;
		this.execution = execution;
	}

	public ParameterUsage[] getParametersUsage() {
		return parametersUsage;
	}

	public ExecutionInterface getExecution() {
		return execution;
	}


	private static Map<Integer, Opcode> opcodesByIntOpcode;
	
	private static void fillOperationCodesByOpcode() {
		opcodesByIntOpcode = new HashMap<>();
		for (Opcode operationCode : Opcode.values()) {
			opcodesByIntOpcode.put(operationCode.intOpcode, operationCode);
		}
	}

	public static Opcode toOpcode(Integer opcode) {
		if (opcodesByIntOpcode == null) {
			fillOperationCodesByOpcode();
		}
		return opcodesByIntOpcode.get(opcode);
	}

}
