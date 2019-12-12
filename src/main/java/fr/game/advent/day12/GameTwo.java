package fr.game.advent.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RegExUtils;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<List<Integer>, Long> {
	
	private static final String INPUT_FILENAME = "day12/input-day12-1";
	
	public static final Function<String, List<Integer>> MAPPER = s -> Arrays.stream(RegExUtils.removeAll(s, "<|>|=|x|y|z| ").split(",")).map(Integer::valueOf).collect(Collectors.toList());
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, MAPPER);
	}

	@Override
	public Long play(List<List<Integer>> initialMoons) {
		boolean findPreviousAxis = false;
		List<Axis> axis = toAxis(initialMoons);
		Set<Axis> previousAxisX = new HashSet<>();
		Axis axisX = axis.get(0);
		long currentStepNumber = 0;
		while (!findPreviousAxis) {
			//System.out.println(" recherche sur axe X");
			while (!previousAxisX.contains(axisX)) {
				previousAxisX.add(new Axis(axisX));
				axisX.doStep();
			}
			System.out.println(" step " + axisX.getStep());
			currentStepNumber = axisX.getStep();
			
			//if (currentStepNumber >= 2028) System.out.println(" recherche sur axe X fructueuse step " + currentStepNumber);
			List<Long> identicalAxisXSteps = previousAxisX.stream().filter(axisX::equals).map(Axis::getStep).sorted().collect(Collectors.toList());
			
			//if (currentStepNumber >= 2028) System.out.println(" recherche sur axe Y");
			Axis axisY = new Axis(axis.get(1));
			Set<Axis> axisYAtIdenticalSteps = findAxisAtIdenticalSteps(currentStepNumber, identicalAxisXSteps, axisY);
			List<Long> identicalAxisXYSteps = axisYAtIdenticalSteps.stream().filter(axisY::equals).map(Axis::getStep).sorted().collect(Collectors.toList());
			
			if (!identicalAxisXYSteps.isEmpty()) {
				System.out.println(" recherche sur axe Z");
				Axis axisZ = new Axis(axis.get(2));
				Set<Axis> axisZAtIdenticalSteps = findAxisAtIdenticalSteps(currentStepNumber, identicalAxisXYSteps, axisZ);
				findPreviousAxis = axisZAtIdenticalSteps.stream().filter(axisZ::equals).map(Axis::getStep).count() > 0;
			}
			
			previousAxisX.add(new Axis(axisX));
			axisX.doStep();
		}
		return currentStepNumber;
	}

	private Set<Axis> findAxisAtIdenticalSteps(long maxStepNumber, List<Long> stepNumbers, Axis axisY) {
		Set<Axis> axisAtIdenticalSteps = new HashSet<>();
		long previousStepNumber = 0;
		for (Long stepNumber : stepNumbers) {
			axisY.doNSteps(stepNumber - previousStepNumber);
			axisAtIdenticalSteps.add(new Axis(axisY));
			previousStepNumber = stepNumber;
		}
		axisY.doNSteps(maxStepNumber - previousStepNumber);
		return axisAtIdenticalSteps;
	}

	private List<Axis> toAxis(List<List<Integer>> initialMoons) {
		List<Axis> axis = new ArrayList<Axis>();
		for (int i = 0; i < 3; i++) {
			axis.add(new Axis(initialMoons.get(0).get(i), initialMoons.get(1).get(i), initialMoons.get(2).get(i), initialMoons.get(3).get(i)));
		}
		return axis;
	}

}
