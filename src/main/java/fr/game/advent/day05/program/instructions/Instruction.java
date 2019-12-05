package fr.game.advent.day05.program.instructions;

import java.util.Arrays;
import java.util.function.Function;

import fr.game.advent.day05.program.Program;

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
	
	public Function<Program, Integer> getExecution() {
		return p -> opcode.getExecution().execute(p, parameterModes);
	}
	
	@Override
	public String toString() {
		return String.format("Instruction [instructionCode=%s, opcode=%s, parameterModes=%s]", instructionCode, opcode, Arrays.toString(parameterModes));
	}	
}
