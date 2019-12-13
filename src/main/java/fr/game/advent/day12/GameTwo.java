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
	
	public static final Function<String, List<Long>> MAPPER = s -> Arrays.stream(RegExUtils.removeAll(s, "<|>|=|x|y|z| ").split(",")).map(Long::valueOf).collect(Collectors.toList());
	
	private Logger log;
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, MAPPER);
		this.log = LoggerUtils.getLogger();
	}

	
	private Map<Long, Map<Axis, Long>> stepsByAxisX = new HashMap<>();
	private Map<Long, Map<Axis, Long>> stepsByAxisY = new HashMap<>();
	private Map<Long, Map<Axis, Long>> stepsByAxisZ = new HashMap<>();
	
	@Override
	public Long play(List<List<Long>> initialMoons) {
		boolean findPreviousAxis = false;
	
		List<Axis> axis = toAxis(initialMoons);
		Axis axisX = axis.get(0);
		Axis axisY = axis.get(1);
		Axis axisZ = axis.get(2);
	
		addStep(axisX, 0L, stepsByAxisX);
		addStep(axisY, 0L, stepsByAxisY);
		addStep(axisZ, 0L, stepsByAxisZ);
	
		Long step = 1L;
		
		while (!findPreviousAxis) {
			axisX.doStep();
			axisY.doStep();
			axisZ.doStep();

			long stepX = getStep(axisX, stepsByAxisX);
			long stepY = getStep(axisY, stepsByAxisY);
			long stepZ = getStep(axisZ, stepsByAxisZ);

			if (stepX != -1L && stepX == stepY && stepX == stepZ) {
				findPreviousAxis = true;
			} else {
				if (stepX == -1L) addStep(axisX, step, stepsByAxisX); 
				if (stepY == -1L) addStep(axisY, step, stepsByAxisY); 
				if (stepZ == -1L) addStep(axisZ, step, stepsByAxisZ); 
				step++;
			}
			
			if (step % 100_000_000L == 0L) {
				log.info(String.format("step %,d traité.", step)); 
				log.info(String.format("stepsByAxisX -> %,d", stepsByAxisX.size())); 
				log.info(String.format("stepsByAxisY -> %,d", stepsByAxisY.size())); 
				log.info(String.format("stepsByAxisZ -> %,d", stepsByAxisZ.size())); 
			}
		}
		return step;
	}

	
	private long getStep(Axis axis, Map<Long, Map<Axis, Long>> stepsByAxis) { 
		Long c1 = axis.getMoon(0);
		Map<Axis, Long> mapStepByAxis = stepsByAxis.get(c1);
		return mapStepByAxis == null ? -1L : mapStepByAxis.getOrDefault(new Axis(axis), -1L);	
	}
	
	private void addStep(Axis axis, Long step, Map<Long, Map<Axis, Long>> stepsByAxis) {
		Long c1 = axis.getMoon(0);
		Map<Axis, Long> mapStepByAxis = stepsByAxis.get(c1);
		if (mapStepByAxis == null) {
			mapStepByAxis = new HashMap<>();
			stepsByAxis.put(c1, mapStepByAxis);
		}
		mapStepByAxis.put(new Axis(axis), step);
	}
	
	private List<Axis> toAxis(List<List<Long>> initialMoons) {
		List<Axis> axis = new ArrayList<Axis>();
		for (int i = 0; i < 3; i++) {
			axis.add(new Axis(initialMoons.get(0).get(i), initialMoons.get(1).get(i), initialMoons.get(2).get(i), initialMoons.get(3).get(i)));
		}
		return axis;
	}

}
