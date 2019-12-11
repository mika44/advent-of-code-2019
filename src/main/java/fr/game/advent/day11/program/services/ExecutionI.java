package fr.game.advent.day11.program.services;

import fr.game.advent.day11.program.model.Parameter;
import fr.game.advent.day11.program.model.Program;

@FunctionalInterface
public interface ExecutionI {

	public Long execute(Program program, Parameter[] parameters);
}
