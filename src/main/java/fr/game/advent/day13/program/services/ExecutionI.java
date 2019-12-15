package fr.game.advent.day13.program.services;

import fr.game.advent.day13.program.model.Parameter;
import fr.game.advent.day13.program.model.Program;

@FunctionalInterface
public interface ExecutionI {

	public Long execute(Program program, Parameter[] parameters);
}
