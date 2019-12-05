package fr.game.advent.day05.program.instructions;

import fr.game.advent.day05.program.Program;

@FunctionalInterface
public interface Execution {

	public Integer execute(Program program, Integer[] parameterModes);
}
