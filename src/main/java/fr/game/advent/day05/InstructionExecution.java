package fr.game.advent.day05;

import java.util.List;

@FunctionalInterface
public interface InstructionExecution {

	public Integer execute(List<Integer> intcodeProgram, Integer instructionPointer, Integer[] parameterModes);
}
