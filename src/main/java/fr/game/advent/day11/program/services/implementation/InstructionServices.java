package fr.game.advent.day11.program.services.implementation;

import java.util.function.Function;

import fr.game.advent.day11.program.model.Instruction;
import fr.game.advent.day11.program.model.Program;
import fr.game.advent.day11.program.services.InstructionServicesI;

public class InstructionServices implements InstructionServicesI {

	@Override
	public Function<Program, Long> getExecution(Instruction instruction) {
		return p -> instruction.getOpcode().getExecution().execute(p, instruction.getParameters());
	}
}
