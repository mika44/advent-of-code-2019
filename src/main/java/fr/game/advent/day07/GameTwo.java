package fr.game.advent.day07;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.game.advent.day07.program.Program;
import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<Integer, Integer> {

	private static final char NEW_LINE = "\n".charAt(0);
	private static final String INPUT_FILENAME = "day07/input-day07-1";

	public GameTwo() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Integer::new);
	}

	@Override
	public Integer play(List<Integer> intcodeProgram) {
		Set<List<Integer>> combinations = combinate(new HashSet<>(Arrays.asList(5, 6, 7, 8, 9)), 5);
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

	private int applyCombination(List<Integer> combination, List<Integer> intcodeProgram) {
		int lastOutputSignal = 0;
		
		try {
			PipedOutputStream osAmpA = new PipedOutputStream();
			PipedOutputStream osAmpB = new PipedOutputStream();
			PipedOutputStream osAmpC = new PipedOutputStream();
			PipedOutputStream osAmpD = new PipedOutputStream();
			PipedOutputStream osAmpE = new PipedOutputStream();
	
			PipedInputStream isAmpA = new PipedInputStream(osAmpE);
			PipedInputStream isAmpB = new PipedInputStream(osAmpA);
			PipedInputStream isAmpC = new PipedInputStream(osAmpB);
			PipedInputStream isAmpD = new PipedInputStream(osAmpC);
			PipedInputStream isAmpE = new PipedInputStream(osAmpD);
			
			osAmpA.write(combination.get(1).toString().getBytes());
			osAmpA.write(NEW_LINE);
			osAmpB.write(combination.get(2).toString().getBytes());
			osAmpB.write(NEW_LINE);
			osAmpC.write(combination.get(3).toString().getBytes());
			osAmpC.write(NEW_LINE);
			osAmpD.write(combination.get(4).toString().getBytes());
			osAmpD.write(NEW_LINE);
			osAmpE.write(combination.get(0).toString().getBytes());
			osAmpE.write(NEW_LINE);
			osAmpE.write('0');
			osAmpE.write(NEW_LINE);

			
			Program ampA = new Program("AMP-A", intcodeProgram, isAmpA, osAmpA);
			Program ampB = new Program("AMP-B", intcodeProgram, isAmpB, osAmpB);
			Program ampC = new Program("AMP-C", intcodeProgram, isAmpC, osAmpC);
			Program ampD = new Program("AMP-D", intcodeProgram, isAmpD, osAmpD);
			Program ampE = new Program("AMP-E", intcodeProgram, isAmpE, osAmpE);
			new Thread(() -> ampA.execute()).start();
			new Thread(() -> ampB.execute()).start();
			new Thread(() -> ampC.execute()).start();
			new Thread(() -> ampD.execute()).start();
			lastOutputSignal = ampE.execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return lastOutputSignal;
	}

}
