package fr.game.advent.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameOne extends AbstractGame<String, String> {
	
	private static final String INPUT_FILENAME = "day16/input-day16-1";
	private static final List<Integer> BASE_PATTERN = Arrays.asList(0, 1, 0, -1);
	private static final int DEFAULT_PHASE_NUMBER_TO_REACH = 100;
	
	private Logger log;
	private int phaseNumberToReach;
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Function.identity());
		log = LoggerUtils.getLogger();
		phaseNumberToReach = DEFAULT_PHASE_NUMBER_TO_REACH;
	}

	
	public void setPhaseNumberToReach(int phaseNumberToReach) {
		this.phaseNumberToReach = phaseNumberToReach;
	}



	@Override
	public String play(List<String> inputString) {
		List<Integer> input = inputString.get(0).chars().mapToObj(c -> c -'0').collect(Collectors.toList());
		int phase = 0;
		log.info("Input signal " + toString(input));
		while (phase < phaseNumberToReach) {
			input = applyPhase(input);
			phase++;
			log.info("After " + phase + " phase: " + toString(input));
		}
		return toString(input, 8);
	}


	private String toString(List<Integer> input) {
		return toString(input, input.size());
	}


	private String toString(List<Integer> input, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < Math.min(input.size(), length); i++) {
			sb.append(input.get(i));
		}
		return sb.toString();
	}


	private List<Integer> applyPhase(List<Integer> input) {
		List<Integer> result = new ArrayList<>();
		int position = 0;
		while (position < input.size()) {
			int sum = 0;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < input.size(); i++) {
				sum = sum + input.get(i) * getPattern(position, i);
				if (i > 0) sb.append("  + ");
				sb.append(input.get(i)).append("*").append(getPattern(position, i));
			}
			sum = Math.abs(sum) % 10;
			sb.append("  = ").append(sum);
			log.info(sb.toString());
			result.add(sum);
			position++;
		}
		return result;
	}


	private Integer getPattern(int position, int i) {
		int indexPattern = ((i + 1) / (position + 1)) % BASE_PATTERN.size();
		return BASE_PATTERN.get( indexPattern );
	}
	

}
