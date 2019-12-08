package fr.game.advent.day07.program.instructions;

import fr.game.advent.day07.program.Program;

@FunctionalInterface
public interface Execution {

	public Integer execute(Program program, Mode[] parameterModes);
}
