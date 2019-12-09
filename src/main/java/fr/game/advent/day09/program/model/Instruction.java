package fr.game.advent.day09.program.model;

import java.util.Arrays;


public class Instruction {

	private Long instructionCode;
	private Opcode opcode;
	private Parameter[] parameters;
	
	public Instruction(Long instructionCode) {
		this.instructionCode = instructionCode;
		this.opcode = Opcode.toOpcode(instructionCode % 100);
		this.parameters =  buildParameters(instructionCode, this.opcode.getParametersUsage());
	}
	
	public Opcode getOpcode() {
		return opcode;
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	public boolean isHaltInstruction() {
		return Opcode.HALT.equals(opcode);
	}
	
	@Override
	public String toString() {
		return String.format("Instruction [instructionCode=%s, opcode=%s, parameters=%s]", instructionCode, opcode, Arrays.toString(parameters));
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
	

}
