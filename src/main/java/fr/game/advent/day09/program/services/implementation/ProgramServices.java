package fr.game.advent.day09.program.services.implementation;

import java.util.logging.Logger;

import fr.game.advent.day09.program.model.Instruction;
import fr.game.advent.day09.program.model.Program;
import fr.game.advent.day09.program.services.InstructionServicesI;
import fr.game.advent.day09.program.services.ProgramServicesI;
import fr.game.utils.LoggerUtils;

public class ProgramServices implements ProgramServicesI {
	
	private static final Logger LOGGER = LoggerUtils.getLogger();
	
	private InstructionServicesI instructionServices = new InstructionServices();
	
	@Override
	public Long execute(Program program) {
		Long lastOutput = null;
		Instruction instructionCourante = new Instruction(program.readMemoryIPOffset(0L));
		while (!instructionCourante.isHaltInstruction()) {
			LOGGER.info("(IP, RB)=(" + program.getInstructionPointer() + ", " + program.getRelativeBase() +") -> " + instructionCourante);
			Long output = instructionServices.getExecution(instructionCourante).apply(program);
			if (output != null) lastOutput = output;
			instructionCourante = new Instruction(program.readMemoryIPOffset(0L));
		}
		program.close();
		return lastOutput;
	}
}
