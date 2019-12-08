package fr.game.advent.day07.program.instructions;

import java.util.Arrays;
import java.util.function.Function;

import fr.game.advent.day07.program.Program;


public class Instruction {

	private Integer instructionCode;
	private Opcode opcode;
	private Mode[] parameterModes;
	
	public Instruction(Integer instructionCode) {
		this.instructionCode = instructionCode;
		this.opcode = Opcode.toOpcode(instructionCode % 100);
		this.parameterModes =  buildParameterModes(instructionCode, this.opcode.getParametersNumber());
	}
	
	private Mode[] buildParameterModes(Integer instructionCode, Integer parameterNumber) {
		Mode[] resultat = new Mode[parameterNumber];
		Integer parameterModesCode = instructionCode / 100;
		for (int i = 0; i < parameterNumber; i++) {
			resultat[i] = Mode.toMode(parameterModesCode % 10);
			parameterModesCode = parameterModesCode / 10;
		}
		return resultat;
	}
	
	public boolean isHaltInstruction() {
		return Opcode.HALT.equals(opcode);
	}
	
	public Function<Program, Integer> getExecution() {
		return p -> opcode.getExecution().execute(p, parameterModes);
	}
	
	@Override
	public String toString() {
		return String.format("Instruction [instructionCode=%s, opcode=%s, parameterModes=%s]", instructionCode, opcode, Arrays.toString(parameterModes));
	}	
}
