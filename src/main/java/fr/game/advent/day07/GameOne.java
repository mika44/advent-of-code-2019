package fr.game.advent.day07;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.game.advent.day07.program.Program;
import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<Integer, Integer> {

	private static final String NEW_LINE = "\n";
	private static final String INPUT_FILENAME = "day07/input-day07-1";

	public GameOne() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Integer::new);
	}

	@Override
	public Integer play(List<Integer> intcodeProgram) {
		Set<List<Integer>> combinations = combinate(new HashSet<>(Arrays.asList(0, 1, 2, 3, 4)), 5);
		int maxSignal = -1;
		for (List<Integer> combination : combinations) {
			int currentSignal = applyCombination(combination, intcodeProgram);
			if (currentSignal > maxSignal) {
				maxSignal = currentSignal;
				System.out.println("" + combination + " -> " + maxSignal);
			}
		}
		return maxSignal;
	}

	
	private int applyCombination(List<Integer> combination, List<Integer> intcodeProgram) {
		int lastOutputSignal = 0;
		for (Integer inputSignal : combination) {
			String inputMessage = "" + inputSignal + NEW_LINE + lastOutputSignal + NEW_LINE;
			Program program = new Program(intcodeProgram, new ByteArrayInputStream(inputMessage.getBytes()));
			lastOutputSignal = program.execute();
		}
		return lastOutputSignal;
	}

	private Set<List<Integer>> combinate(Set<Integer> inputs, int sizeCombination) {
		Set<List<Integer>> combinations = new HashSet<>();
		Set<Integer> localInput = new HashSet<>(inputs);
		for (Integer input : inputs) {
			if (sizeCombination == 1) {
				List<Integer> combination = new ArrayList<>();
				combination.add(input);
				combinations.add(combination);
			} else {
				localInput.remove(input);
				for (List<Integer> underCombination : combinate(localInput, sizeCombination - 1)) {
					List<Integer> combination = new ArrayList<>();
					combination.add(input);
					combination.addAll(underCombination);
					combinations.add(combination);
				}
				localInput.add(input);
			}
		}
		return combinations;
	}
}
