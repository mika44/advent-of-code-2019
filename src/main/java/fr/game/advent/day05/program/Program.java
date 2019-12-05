package fr.game.advent.day05.program;

import java.util.List;

import fr.game.advent.day05.program.instructions.Instruction;

public class Program {

	private List<Integer> memory;
	private Integer instructionPointer;

	public Program(List<Integer> memory) {
		this.memory = memory;
		this.instructionPointer = 0;
	}

	private Integer readMemory(Integer position) {
		return memory.get(position);
	}
	
	public Integer readMemoryFromIP(Integer deltaIP) {
		return memory.get(instructionPointer + deltaIP);
	}

	public Integer readMemoryFromIPWithMode(Integer deltaIP, Integer accessMode) {
		Integer operande = readMemoryFromIP(deltaIP);
		return accessMode == 0 ? readMemory(operande) : operande;
	}
	
	public void writeMemory(Integer position, Integer value) {
		memory.set(position, value);
	}

	public void setIP(Integer newInstructionPointer) {
		instructionPointer = newInstructionPointer;
	}
	
	public void shiftIP(Integer deltaIP) {
		instructionPointer = instructionPointer + deltaIP;
	}
	
	public Integer execute() {
		Integer lastOutput = null;
		Instruction instructionCourante = new Instruction(memory.get(instructionPointer));
		while (!instructionCourante.isHaltInstruction()) {
			Integer output = instructionCourante.getExecution().apply(this);
			if (output != null) lastOutput = output;
			instructionCourante = new Instruction(memory.get(instructionPointer));
		}
		return lastOutput;
	}
}
