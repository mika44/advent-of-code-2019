package fr.game.advent.day05.program.instructions;

import java.util.Arrays;
import java.util.function.Function;

import fr.game.advent.day05.program.Program;

public class Instruction {

	private Integer instructionCode;
	private Opcode opcode;
	private Parameter[] parameters;
	
	public Instruction(Integer instructionCode) {
		this.instructionCode = instructionCode;
		this.opcode = Opcode.toOpcode(instructionCode % 100);
		this.parameters =  buildParameters(instructionCode, this.opcode.getParametersUsage());
	}
	
	private Parameter[] buildParameters(Integer instructionCode, ParameterUsage[] parametersUsage) {
		Parameter[] resultat = new Parameter[parametersUsage.length];
		Integer parameterModesCode = instructionCode / 100;
		for (int i = 0; i < parametersUsage.length; i++) {
			resultat[i] = new Parameter(parametersUsage[i], Mode.toMode(parameterModesCode % 10), i + 1);
			parameterModesCode = parameterModesCode / 10;
		}
		return resultat;
	}
	
	public boolean isHaltInstruction() {
		return Opcode.HALT.equals(opcode);
	}
	
	public Function<Program, Integer> getExecution() {
		return p -> opcode.getExecution().execute(p, parameters);
	}
	
	@Override
	public String toString() {
		return String.format("Instruction [instructionCode=%s, opcode=%s, parameters=%s]", instructionCode, opcode, Arrays.toString(parameters));
	}	
}
