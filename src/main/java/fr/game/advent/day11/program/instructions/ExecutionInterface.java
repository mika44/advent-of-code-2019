package fr.game.advent.day11.program.instructions;

import fr.game.advent.day11.program.Program;

@FunctionalInterface
public interface ExecutionInterface {

	public Long execute(Program program, Parameter[] parameters);
}
