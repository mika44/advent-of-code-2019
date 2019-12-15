package fr.game.advent.day13.program.services;

import java.util.function.Function;

import fr.game.advent.day13.program.model.Instruction;
import fr.game.advent.day13.program.model.Program;

public interface InstructionServicesI {

	Function<Program, Long> getExecution(Instruction instruction);

}