package fr.game.advent.day02;

import java.util.List;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOneBis extends AbstractGame<Integer, Integer> {
	
	private static final String INPUT_FILENAME = "day02/input-day02-1";
	
	public GameOneBis() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Integer::new);
	}
	

	@Override
	public Integer play(List<Integer> intcodeProgram) {
		intcodeProgram.set(1, 12);
		intcodeProgram.set(2, 2);
		executeIntcodeProgram(intcodeProgram);
		return intcodeProgram.get(0);
	}


	public List<Integer> executeIntcodeProgram(List<Integer> intcodeProgram) {
		int instructionPointer = 0;
		Integer opcode = intcodeProgram.get(instructionPointer);
		while (opcode != 99) {
			switch (opcode) {
			case 1:	instructionPointer = executeOpcode1(intcodeProgram, instructionPointer); break;
			case 2:	instructionPointer = executeOpcode2(intcodeProgram, instructionPointer); break;
			default: throw new RuntimeException(String.format("ERREUR - opcode inconnu - opcode = %d - instructionPointer = %d", opcode, instructionPointer));
			}
			opcode = intcodeProgram.get(instructionPointer);
		}
		return intcodeProgram;
	}


	public int executeOpcode1(List<Integer> intcodeProgram, int instructionPointer) {
		Integer parameter1 = intcodeProgram.get(instructionPointer + 1);
		Integer parameter2 = intcodeProgram.get(instructionPointer + 2);
		Integer parameter3 = intcodeProgram.get(instructionPointer + 3);
		intcodeProgram.set(parameter3, intcodeProgram.get(parameter1) + intcodeProgram.get(parameter2));
		instructionPointer = instructionPointer + 4;
		return instructionPointer;
	}

	public int executeOpcode2(List<Integer> intcodeProgram, int instructionPointer) {
		Integer parameter1 = intcodeProgram.get(instructionPointer + 1);
		Integer parameter2 = intcodeProgram.get(instructionPointer + 2);
		Integer parameter3 = intcodeProgram.get(instructionPointer + 3);
		intcodeProgram.set(parameter3, intcodeProgram.get(parameter1) * intcodeProgram.get(parameter2));
		instructionPointer = instructionPointer + 4;
		return instructionPointer;	
	}

}

