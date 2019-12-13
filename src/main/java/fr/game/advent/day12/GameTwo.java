package fr.game.advent.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RegExUtils;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameTwo extends AbstractGame<List<Long>, Long> {

	private static final String INPUT_FILENAME = "day12/input-day12-1";

	public static final Function<String, List<Long>> MAPPER = s -> Arrays
			.stream(RegExUtils.removeAll(s, "<|>|=|x|y|z| ").split(",")).map(Long::valueOf)
			.collect(Collectors.toList());

	private Logger log;

	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, MAPPER);
		this.log = LoggerUtils.getLogger();
	}

	private class Cycle {
		private long firstOccurence;
		private long secondOccurence;

		private Cycle(long firstOccurence, long secondOccurence) {
			this.firstOccurence = firstOccurence;
			this.secondOccurence = secondOccurence;
		}

		public long getFirstOccurence() {
			return firstOccurence;
		}

		public long getSecondOccurence() {
			return secondOccurence;
		}

		public long getModule() {
			return secondOccurence - firstOccurence;
		}

		public boolean isInCycle(long step) {
			return (step - firstOccurence) % getModule() == 0L;
		}

		@Override
		public String toString() {
			return String.format("Cycle [firstOccurence=%s, secondOccurence=%s, getModule()=%s]", firstOccurence,
					secondOccurence, getModule());
		}
	}

	@Override
	public Long play(List<List<Long>> initialMoons) {
		boolean allFirstOccurenceZero = true;
		List<Axis> axisList = toAxis(initialMoons);
		List<Cycle> cycles = new ArrayList<>();
		long maxModule = 0L;
		long seedMaxModule = 0L;
		for (Axis axis : axisList) {
			Cycle cycle = findCycle(axis);
			cycles.add(cycle);
			allFirstOccurenceZero = allFirstOccurenceZero && cycle.getFirstOccurence() == 0L;
			if (cycle.getModule() > maxModule) {
				maxModule = cycle.getModule();
				seedMaxModule = cycle.getSecondOccurence();
			}
			log.info("Cycle trouvé : " + cycle);
		}

		if (allFirstOccurenceZero) 
			return cycles.stream().mapToLong(Cycle::getModule).reduce(1L, (a, b) -> ppcm(a, b));

		boolean findMinimumCommonCycle = false;
		long currentStep = seedMaxModule;
		long i = 0;
		while (!findMinimumCommonCycle) {
			i++;
			if (i % 100000 == 0L)
				log.info("currentStep = " + currentStep);
			findMinimumCommonCycle = true;
			for (Cycle cycle : cycles) {
				findMinimumCommonCycle = findMinimumCommonCycle && cycle.isInCycle(currentStep);
			}
			currentStep = currentStep + maxModule;
		}
		return currentStep - maxModule;
	}

	
	private long ppcm(long n1, long n2) {
		return (n1 * n2) / pgcd(n1, n2);
	} 

	private long pgcd(long n1, long n2) {
		long reste;
		reste = n1 % n2;
		while (reste != 0L) {
			n1 = n2;
			n2 = reste;
			reste = n1 % n2;
		}
		return n2;
	} 
	
	private Cycle findCycle(Axis axis) {
		Map<Axis, Long> stepsByAxis = new HashMap<>();
		boolean alreadySeen = false;
		long currentStep = 0L;
		long pastStep = 0L;
		while (!alreadySeen) {
			pastStep = stepsByAxis.getOrDefault(axis, -1L);
			if (pastStep == -1L) {
				stepsByAxis.put(new Axis(axis), currentStep);
				axis.doStep();
				currentStep++;
			} else {
				alreadySeen = true;
			}
		}
		Cycle cycle = new Cycle(pastStep, currentStep);
		return cycle;
	}

	private List<Axis> toAxis(List<List<Long>> initialMoons) {
		List<Axis> axis = new ArrayList<Axis>();
		for (int i = 0; i < 3; i++) {
			axis.add(new Axis(initialMoons.get(0).get(i), initialMoons.get(1).get(i), initialMoons.get(2).get(i),
					initialMoons.get(3).get(i)));
		}
		return axis;
	}

}
