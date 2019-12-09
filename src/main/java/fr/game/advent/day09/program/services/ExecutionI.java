package fr.game.advent.day09.program.services;

import fr.game.advent.day09.program.model.Parameter;
import fr.game.advent.day09.program.model.Program;

@FunctionalInterface
public interface ExecutionI {

	public Long execute(Program program, Parameter[] parameters);
}
