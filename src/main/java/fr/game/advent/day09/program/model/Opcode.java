package fr.game.advent.day09.program.model;

import static fr.game.advent.day09.program.model.ParameterUsage.*;

import java.util.HashMap;
import java.util.Map;

import fr.game.advent.day09.program.services.ExecutionI;
import fr.game.advent.day09.program.services.implementation.ExecutionAdd;
import fr.game.advent.day09.program.services.implementation.ExecutionAdjustRelativeBase;
import fr.game.advent.day09.program.services.implementation.ExecutionEquals;
import fr.game.advent.day09.program.services.implementation.ExecutionHalt;
import fr.game.advent.day09.program.services.implementation.ExecutionJumpIfFalse;
import fr.game.advent.day09.program.services.implementation.ExecutionJumpIfTrue;
import fr.game.advent.day09.program.services.implementation.ExecutionLessThan;
import fr.game.advent.day09.program.services.implementation.ExecutionMultiply;
import fr.game.advent.day09.program.services.implementation.ExecutionReadInput;
import fr.game.advent.day09.program.services.implementation.ExecutionWriteOutput;

public enum Opcode {
	
	ADD						( 1, new ExecutionAdd(), 				TO_READ, TO_READ, TO_WRITE),
	MULTIPLY				( 2, new ExecutionMultiply(),			TO_READ, TO_READ, TO_WRITE),
	READ_INPUT				( 3, new ExecutionReadInput(),  		TO_WRITE),
	WRITE_OUTPUT			( 4, new ExecutionWriteOutput(),		TO_READ),
	JUMP_IF_TRUE			( 5, new ExecutionJumpIfTrue(),  		TO_READ, TO_READ),
	JUMP_IF_FALSE			( 6, new ExecutionJumpIfFalse(), 		TO_READ, TO_READ),
	LESS_THAN				( 7, new ExecutionLessThan(), 			TO_READ, TO_READ, TO_WRITE),
	EQUALS					( 8, new ExecutionEquals(), 			TO_READ, TO_READ, TO_WRITE),
	ADJUST_RELATIVE_BASE	( 9, new ExecutionAdjustRelativeBase(),	TO_READ),
	HALT					(99, new ExecutionHalt())
	;
	

	private int intOpcode;
	private ParameterUsage[] parametersUsage;
	private ExecutionI execution;
	
	

	private Opcode(int intOpcode, ExecutionI execution, ParameterUsage... parametersUsage) {
		this.intOpcode = intOpcode;
		this.parametersUsage = parametersUsage;
		this.execution = execution;
	}

	public ParameterUsage[] getParametersUsage() {
		return parametersUsage;
	}

	public ExecutionI getExecution() {
		return execution;
	}


	private static Map<Integer, Opcode> opcodesByIntOpcode;
	
	private static void fillOperationCodesByOpcode() {
		opcodesByIntOpcode = new HashMap<>();
		for (Opcode operationCode : Opcode.values()) {
			opcodesByIntOpcode.put(operationCode.intOpcode, operationCode);
		}
	}

	public static Opcode toOpcode(Long opcode) {
		if (opcodesByIntOpcode == null) {
			fillOperationCodesByOpcode();
		}
		return opcodesByIntOpcode.get(opcode.intValue());
	}

}
