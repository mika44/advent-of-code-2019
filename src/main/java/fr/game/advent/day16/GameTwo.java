package fr.game.advent.day16;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameTwo extends AbstractGame<String, String> {

	private static final int RESULT_SIZE = 8;
	private static final String INPUT_FILENAME = "day16/input-day16-1";
	private static final int[] BASE_PATTERN = { 0, 1, 0, -1 };
	private static final int DEFAULT_PHASE_NUMBER_TO_REACH = 100;
	private static final int REPEATED_FACTOR = 10_000;

	private static final int TWO_POWER = 6;
	private static final long POWER_OF_TWO_MINUS_ONE = (1L << TWO_POWER) - 1L;

	private Logger log;
	private int phaseNumberToReach;
	private int[] input;
	private int indexMax;

	private long[][] necessaryIndex;
	private int[] necessaryIndexNumberPerPhase;
	private int necessaryIndexNumber;
	private int[][] results;

	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Function.identity());
		log = LoggerUtils.getLogger();
		phaseNumberToReach = DEFAULT_PHASE_NUMBER_TO_REACH;
	}

	public void setPhaseNumberToReach(int phaseNumberToReach) {
		this.phaseNumberToReach = phaseNumberToReach;
	}

	@Override
	public String play(List<String> inputString) {
		input = inputString.get(0).chars().map(c -> c - '0').toArray();
		indexMax = input.length * REPEATED_FACTOR;
		log.warning("Index max -> " + indexMax);

		int numberOfDigitsToSkip = Integer.valueOf(toString(input, 0, 7));
		//int numberOfDigitsToSkip = 0;

		int necessaryIndexSize = 1 + (indexMax >>> TWO_POWER);
		necessaryIndex = new long[phaseNumberToReach][necessaryIndexSize];
		necessaryIndexNumberPerPhase = new int[phaseNumberToReach];

		int index = 0;
		while (index < RESULT_SIZE) {
			markNecessaryIndex(phaseNumberToReach, numberOfDigitsToSkip + index);
			index++;
		}
		log.warning("Nombre index marqués -> " + necessaryIndexNumber);

		results = new int[phaseNumberToReach][];
		int phase = 1;
		while (phase <= phaseNumberToReach) {
			log.warning("Calcul phase " + phase + " -> " + necessaryIndexNumberPerPhase[phase - 1] + " valeurs à calculer");
			results[phase - 1] = new int[necessaryIndexNumberPerPhase[phase - 1]];
			calculNecessaryIndexPhase(phase);
			if (phase > 1)
				results[phase - 2] = null;
			phase++;
		}
		return toString(results[phaseNumberToReach - 1], 0, RESULT_SIZE);
	}

	private String toString(int[] input, int begin, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = begin; i < Math.min(input.length, begin + length); i++) {
			sb.append(input[i]);
		}
		return sb.toString();
	}

	private void markNecessaryIndex(int phaseNumber, int index) {
		if (phaseNumber > 0) {
			if (!isNecessary(phaseNumber, index)) {
				calculAndMarkNecessaryIndex(phaseNumber, index);
				setNecessary(phaseNumber, index);
				necessaryIndexNumberPerPhase[phaseNumber - 1]++;
				necessaryIndexNumber++;
			}
		}
	}

	private boolean isNecessary(int phaseNumber, int index) {
		long bitNecessaryIndex = valueBitInItemOfNecessaryIndex(index);
		return (necessaryIndex[phaseNumber - 1][positionInNecessaryIndex(index)] & bitNecessaryIndex) != 0L;
	}

	private void setNecessary(int phaseNumber, int index) {
		long bitNecessaryIndex = valueBitInItemOfNecessaryIndex(index);
		necessaryIndex[phaseNumber - 1][positionInNecessaryIndex(index)] |= bitNecessaryIndex;
	}

	
	private int lastPhaseNumber = 0;
	private int[] lastResult = null;
	private int lastIndex = -1;
	private int lastIndexCorrige = -1;
	
	private int getValue(int phaseNumber, int index) {
		if (phaseNumber == 0) {
			return input[index % input.length];
		}

		if (lastPhaseNumber != phaseNumber) {
			lastResult = results[phaseNumber - 1];
			lastPhaseNumber = phaseNumber;
			lastIndex = -1;
			lastIndexCorrige = -1;
		}
		
		if (lastIndex == index - 1) {
			lastIndexCorrige++;
		} else {
			lastIndexCorrige = positionInResults(phaseNumber, index);
		}
		lastIndex = index;
		return lastResult[lastIndexCorrige];
	}

	private int positionInResults(int phaseNumber, int index) {
		int indexInResults = 0;
		int indexInNecessaryIndex = positionInNecessaryIndex(index);
		long allBitsBeforBitInItemOfNecessaryIndex = 2 * valueBitInItemOfNecessaryIndex(index) - 1L;
		long[] currentNecessaryIndex = necessaryIndex[phaseNumber - 1];
		indexInResults += Long.bitCount(currentNecessaryIndex[indexInNecessaryIndex] & allBitsBeforBitInItemOfNecessaryIndex);
		indexInNecessaryIndex--;
		while (indexInNecessaryIndex >= 0) {
			int delta = Long.bitCount(currentNecessaryIndex[indexInNecessaryIndex]);
			indexInResults += delta;
			indexInNecessaryIndex--;
		}
		indexInResults--;
		return indexInResults;
	}

	private long valueBitInItemOfNecessaryIndex(int index) {
		return 1L << positionInItemOfNecessaryIndex(index);
	}

	private long positionInItemOfNecessaryIndex(int index) {
		return index & POWER_OF_TWO_MINUS_ONE;
	}

	private int positionInNecessaryIndex(int index) {
		return index >>> TWO_POWER;
	}

	private void calculAndMarkNecessaryIndex(int phaseNumber, int index) {
		int indexValuePrecedentPhase = -1;
		int indexPattern = -1;
		while (indexValuePrecedentPhase < indexMax) {
			indexPattern++;
			if (indexPattern >= BASE_PATTERN.length)
				indexPattern = 0;
			int pattern = BASE_PATTERN[indexPattern];

			int indexLimit = indexValuePrecedentPhase + index + 1;
			if (indexLimit > indexMax)
				indexLimit = indexMax;

			if (pattern == 0) {
				indexValuePrecedentPhase = indexLimit;
			} else {
				while (indexValuePrecedentPhase < indexLimit) {
					markNecessaryIndex(phaseNumber - 1, indexValuePrecedentPhase);
					indexValuePrecedentPhase++;
				}
			}
		}
	}

	private void calculNecessaryIndexPhase(int phaseNumber) {
		int numberCalculated = 0;
		for (int index = 0; index < indexMax; index++) {
			if (isNecessary(phaseNumber, index)) {
				results[phaseNumber - 1][numberCalculated] = calculNecessaryIndexPhase(phaseNumber, index);
				numberCalculated++;
			}
		}
	}

	private int calculNecessaryIndexPhase(int phaseNumber, int index) {
		int indexValuePrecedentPhase = -1;
		int indexPattern = -1;
		int sum = 0;
		while (indexValuePrecedentPhase < indexMax) {
			indexPattern++;
			if (indexPattern >= BASE_PATTERN.length)
				indexPattern = 0;
			int pattern = BASE_PATTERN[indexPattern];

			int indexLimit = indexValuePrecedentPhase + index + 1;
			if (indexLimit > indexMax)
				indexLimit = indexMax;

			if (pattern == 0) {
				indexValuePrecedentPhase = indexLimit;
			} else {
				if (pattern == 1) {
					while (indexValuePrecedentPhase < indexLimit) {
						sum = sum + getValue(phaseNumber - 1, indexValuePrecedentPhase);
						indexValuePrecedentPhase++;
					}
				} else {
					while (indexValuePrecedentPhase < indexLimit) {
						sum = sum - getValue(phaseNumber - 1, indexValuePrecedentPhase);
						indexValuePrecedentPhase++;
					}
				}
			}
		}
		sum = sum % 10;
		if (sum < 0)
			sum = -sum;
		return sum;
	}

}
