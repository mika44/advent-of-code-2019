package fr.game.advent.day09.program.instructions;

import fr.game.advent.day09.program.Program;

@FunctionalInterface
public interface ExecutionInterface {

	public Long execute(Program program, Parameter[] parameters);
}
