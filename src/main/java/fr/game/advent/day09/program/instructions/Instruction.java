package fr.game.advent.day09.program.instructions;

import java.util.Arrays;
import java.util.function.Function;

import fr.game.advent.day09.program.Program;


public class Instruction {

	private Long instructionCode;
	private Opcode opcode;
	private Parameter[] parameters;
	
	public Instruction(Long instructionCode) {
		this.instructionCode = instructionCode;
		this.opcode = Opcode.toOpcode(instructionCode % 100);
		this.parameters =  buildParameters(instructionCode, this.opcode.getParametersUsage());
	}
	
	private Parameter[] buildParameters(Long instructionCode, ParameterUsage[] parametersUsage) {
		Parameter[] resultat = new Parameter[parametersUsage.length];
		Long parameterModesCode = instructionCode / 100;
		for (Long i = 0L; i < parametersUsage.length; i++) {
			resultat[i.intValue()] = new Parameter(parametersUsage[i.intValue()], Mode.toMode(parameterModesCode % 10), i + 1);
			parameterModesCode = parameterModesCode / 10;
		}
		return resultat;
	}
	
	public boolean isHaltInstruction() {
		return Opcode.HALT.equals(opcode);
	}
	
	public Function<Program, Long> getExecution() {
		return p -> opcode.getExecution().execute(p, parameters);
	}
	
	@Override
	public String toString() {
		return String.format("Instruction [instructionCode=%s, opcode=%s, parameters=%s]", instructionCode, opcode, Arrays.toString(parameters));
	}	
}
