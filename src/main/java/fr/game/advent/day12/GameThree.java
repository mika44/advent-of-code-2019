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

public class GameThree extends AbstractGame<List<Integer>, Long> {
	
	private static final String INPUT_FILENAME = "day12/input-day12-1";
	
	public static final Function<String, List<Integer>> MAPPER = s -> Arrays.stream(RegExUtils.removeAll(s, "<|>|=|x|y|z| ").split(",")).map(Integer::valueOf).collect(Collectors.toList());
	
	private Logger log;
	
	public GameThree() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, MAPPER);
		this.log = LoggerUtils.getLogger();
	}

	
	private Map<Axis, Long> stepsByAxisX = new HashMap<>();
	private Map<Axis, Long> stepsByAxisY = new HashMap<>();
	private Map<Axis, Long> stepsByAxisZ = new HashMap<>();
	
	@Override
	public Long play(List<List<Integer>> initialMoons) {
		boolean findPreviousAxis = false;
	
		List<Axis> axis = toAxis(initialMoons);
		Axis axisX = axis.get(0);
		Axis axisY = axis.get(1);
		Axis axisZ = axis.get(2);
		
		stepsByAxisX.put(axisX, 0L);
		stepsByAxisY.put(axisY, 0L);
		stepsByAxisZ.put(axisZ, 0L);

		Long step = 1L;
		
		while (!findPreviousAxis) {
			axisX.doStep();
			axisY.doStep();
			axisZ.doStep();

			long stepX = stepsByAxisX.getOrDefault(axisX, -1L);
			long stepY = stepsByAxisY.getOrDefault(axisY, -1L);
			long stepZ = stepsByAxisZ.getOrDefault(axisZ, -1L);

			if (stepX != -1L && stepX == stepY && stepX == stepZ) {
				findPreviousAxis = true;
			} else {
				if (stepX == -1L) stepsByAxisX.put(axisX, step);
				if (stepY == -1L) stepsByAxisY.put(axisY, step);
				if (stepZ == -1L) stepsByAxisZ.put(axisZ, step);
				step++;
			}
			
			if (step % 1_000_000L == 0L) log.info(String.format("step %,d traité.", step)); 
		}
		return step;
	}

	private List<Axis> toAxis(List<List<Integer>> initialMoons) {
		List<Axis> axis = new ArrayList<Axis>();
		for (int i = 0; i < 3; i++) {
			axis.add(new Axis(initialMoons.get(0).get(i), initialMoons.get(1).get(i), initialMoons.get(2).get(i), initialMoons.get(3).get(i)));
		}
		return axis;
	}

}
