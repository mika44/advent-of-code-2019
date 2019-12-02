package fr.game.advent.day02;

import java.util.ArrayList;
import java.util.List;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<Integer, Integer> {
	
	private static final String INPUT_FILENAME = "day02/input-day02-1";
	
	public GameTwo() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Integer::new);
	}
	
	@Override
	public Integer play(List<Integer> intcodeProgram) {
		for (int noun = 0; noun < 99; noun++) {
			for (int verb = 0; verb < 99; verb++) {
				if (playWithPairOfInputs(intcodeProgram, noun, verb) == 19690720) 
					return 100 * noun + verb;
			}
		}
		return null;
	}

	private Integer playWithPairOfInputs(List<Integer> intcodeProgram, Integer noun, Integer verb) {
		List<Integer> currentIntcodeProgram = new ArrayList<>(intcodeProgram);
		currentIntcodeProgram.set(1, noun);
		currentIntcodeProgram.set(2, verb);
		executeIntcodeProgram(currentIntcodeProgram);
		return currentIntcodeProgram.get(0);
	}

	private List<Integer> executeIntcodeProgram(List<Integer> intcodeProgram) {
		int instructionPointer = 0;
		Integer opcode = intcodeProgram.get(instructionPointer);
		while (opcode != 99) {
			Integer parameter1 = intcodeProgram.get(instructionPointer + 1);
			Integer parameter2 = intcodeProgram.get(instructionPointer + 2);
			Integer parameter3 = intcodeProgram.get(instructionPointer + 3);
			switch (opcode) {
			case 1:
				intcodeProgram.set(parameter3, intcodeProgram.get(parameter1) + intcodeProgram.get(parameter2));
				break;
			case 2:
				intcodeProgram.set(parameter3, intcodeProgram.get(parameter1) * intcodeProgram.get(parameter2));
				break;
			default:
				break;
			}
			instructionPointer = instructionPointer + 4;
			opcode = intcodeProgram.get(instructionPointer);
		}
		return intcodeProgram;
	}

}

