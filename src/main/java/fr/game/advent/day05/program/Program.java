package fr.game.advent.day05.program;

import java.util.List;

import fr.game.advent.day05.program.instructions.Instruction;
import fr.game.advent.day05.program.instructions.Mode;

public class Program {

	private List<Integer> memory;
	private Integer instructionPointer;

	public Program(List<Integer> memory) {
		this.memory = memory;
		this.instructionPointer = 0;
	}

	private Integer readMemoryDirectAccess(Integer position) {
		return memory.get(position);
	}
	
	public Integer readMemoryIPOffset(Integer offset) {
		return memory.get(instructionPointer + offset);
	}

	public Integer readMemoryIPOffset(Integer offset, Mode accessMode) {
		Integer operande = readMemoryIPOffset(offset);
		return Mode.POSITION_MODE.equals(accessMode) ? readMemoryDirectAccess(operande) : operande;
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
		Instruction instructionCourante = new Instruction(readMemoryIPOffset(0));
		while (!instructionCourante.isHaltInstruction()) {
			Integer output = instructionCourante.getExecution().apply(this);
			if (output != null) lastOutput = output;
			instructionCourante = new Instruction(readMemoryIPOffset(0));
		}
		return lastOutput;
	}
}
