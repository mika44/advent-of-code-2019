package fr.game.advent.day16;

import java.util.Arrays;
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
	private static final long POWER_OF_TWO = 1L << TWO_POWER;
	private static final long POWER_OF_TWO_MINUS_ONE = POWER_OF_TWO - 1L;

	private Logger log;
	private int phaseNumberToReach;
	private int[] input;
	private int indexMax;

	private long[][] necessaryIndex;
	private int[] necessaryIndexNumberPerPhase;
	private int[] lastIndexCalled;
	
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

		int necessaryIndexSize = 1 + (indexMax >>> TWO_POWER);
		necessaryIndex = new long[phaseNumberToReach + 1][necessaryIndexSize];
		necessaryIndexNumberPerPhase = new int[phaseNumberToReach + 1];
		lastIndexCalled = new int[phaseNumberToReach + 1];

		log.warning("début marquage");
		int index = 0;
		while (index < RESULT_SIZE) {
			calculAndMarkNecessaryIndex(phaseNumberToReach, numberOfDigitsToSkip + index);
			setNecessary(phaseNumberToReach, numberOfDigitsToSkip + index);
			index++;
		}
		for (int i = 0; i < necessaryIndexNumberPerPhase.length; i++) {
			log.warning("- phase " + i + " -> " + necessaryIndexNumberPerPhase[i] + " index marqués sur " + indexMax);
		}

		results = new int[phaseNumberToReach + 1][];
		int phase = 1;
		while (phase <= phaseNumberToReach) {
			log.warning("Calcul phase " + phase + " -> " + necessaryIndexNumberPerPhase[phase] + " valeurs à calculer");
			results[phase] = new int[necessaryIndexNumberPerPhase[phase]];
			calculNecessaryIndexPhase(phase);
			results[phase - 1] = null;
			phase++;
		}
		return toString(results[phaseNumberToReach], 0, RESULT_SIZE);
	}

	private String toString(int[] input, int begin, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = begin; i < Math.min(input.length, begin + length); i++) {
			sb.append(input[i]);
		}
		return sb.toString();
	}

	private boolean isNecessary(int phaseNumber, int index) {
		long bitNecessaryIndex = valueBitInItemOfNecessaryIndex(index);
		return (necessaryIndex[phaseNumber][positionInNecessaryIndex(index)] & bitNecessaryIndex) != 0L;
	}

	private void setNecessary(int phaseNumber, int index) {
		long bitNecessaryIndex = valueBitInItemOfNecessaryIndex(index);
		necessaryIndex[phaseNumber][positionInNecessaryIndex(index)] |= bitNecessaryIndex;
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
			lastResult = results[phaseNumber];
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
		if (phaseNumber > 0) {
			if (lastIndexCalled[phaseNumber] == index - 1 && index > 1) {
				calculAndMarkNecessaryIndexModeDelta(phaseNumber, index);
			} else {
				calculAndMarkNecessaryIndexModeClassic(phaseNumber, index);
			}
		}
		necessaryIndexNumberPerPhase[phaseNumber]++;
		lastIndexCalled[phaseNumber] = index;
	}
	
	
	private void calculAndMarkNecessaryIndexModeClassic(int phaseNumber, int index) {
		int indexPattern = -1;
		
		int indexValuePrecedentPhase = -1;
		int positionInNecessaryIndexPrecedentPhase = 0;
		long positionBitInNecessaryIndexPrecedentPhase = 0;
		long significantBitPrecedentPhase = 1L;
		long[] currentNecessaryIndex = necessaryIndex[phaseNumber - 1];
		long indicatorNecessaryIndexPrecedentPhase = currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase];
		
		while (indexValuePrecedentPhase < indexMax) {
			indexPattern++;
			if (indexPattern >= BASE_PATTERN.length)
				indexPattern = 0;
			int pattern = BASE_PATTERN[indexPattern];

			int indexLimit = indexValuePrecedentPhase + index + 1;
			if (indexLimit > indexMax) indexLimit = indexMax;
			
			if (pattern == 0) {
				indexValuePrecedentPhase = indexLimit;
				positionInNecessaryIndexPrecedentPhase = positionInNecessaryIndex(indexValuePrecedentPhase);
				positionBitInNecessaryIndexPrecedentPhase = positionInItemOfNecessaryIndex(indexValuePrecedentPhase);
				significantBitPrecedentPhase = 1L << positionBitInNecessaryIndexPrecedentPhase;
				indicatorNecessaryIndexPrecedentPhase = currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase];
			} else {
				while (indexValuePrecedentPhase < indexLimit) {
					if ((indicatorNecessaryIndexPrecedentPhase & significantBitPrecedentPhase) == 0) {
						calculAndMarkNecessaryIndex(phaseNumber - 1, indexValuePrecedentPhase);
						indicatorNecessaryIndexPrecedentPhase |= significantBitPrecedentPhase;
					}

					if (positionBitInNecessaryIndexPrecedentPhase == POWER_OF_TWO_MINUS_ONE) {
						currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase] |= indicatorNecessaryIndexPrecedentPhase;
						positionInNecessaryIndexPrecedentPhase++;
						positionBitInNecessaryIndexPrecedentPhase = 0L;
						significantBitPrecedentPhase = 1L;
						indicatorNecessaryIndexPrecedentPhase = currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase];
					} else {
						positionBitInNecessaryIndexPrecedentPhase++; 
						significantBitPrecedentPhase <<= 1L;
					}
					indexValuePrecedentPhase++;
				}
				currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase] |= indicatorNecessaryIndexPrecedentPhase;
			}
		}
	}
	
	
	private void calculAndMarkNecessaryIndexModeDelta(int phaseNumber, int index) {
		int indexPattern = -1;
		
		int decal = 0;
		
		int indexValuePrecedentPhase = -1;
		int positionInNecessaryIndexPrecedentPhase = 0;
		long positionBitInNecessaryIndexPrecedentPhase = 0;
		long significantBitPrecedentPhase = 1L;
		long[] currentNecessaryIndex = necessaryIndex[phaseNumber - 1];
		long indicatorNecessaryIndexPrecedentPhase = currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase];
		
		while (indexValuePrecedentPhase < indexMax) {
			indexPattern++;
			if (indexPattern >= BASE_PATTERN.length)
				indexPattern = 0;
			int pattern = BASE_PATTERN[indexPattern];

			int indexLimit = indexValuePrecedentPhase + index + 1;
			if (indexLimit > indexMax) indexLimit = indexMax;
			
			if (pattern == 0) {
				indexValuePrecedentPhase = indexLimit;
			} else {
				indexValuePrecedentPhase += decal;
				positionInNecessaryIndexPrecedentPhase = positionInNecessaryIndex(indexValuePrecedentPhase);
				positionBitInNecessaryIndexPrecedentPhase = positionInItemOfNecessaryIndex(indexValuePrecedentPhase);
				significantBitPrecedentPhase = 1L << positionBitInNecessaryIndexPrecedentPhase;
				indicatorNecessaryIndexPrecedentPhase = currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase];
				
				while (indexValuePrecedentPhase < indexLimit) {
					if ((indicatorNecessaryIndexPrecedentPhase & significantBitPrecedentPhase) == 0) {
						calculAndMarkNecessaryIndex(phaseNumber - 1, indexValuePrecedentPhase);
						indicatorNecessaryIndexPrecedentPhase |= significantBitPrecedentPhase;
					}

					if (positionBitInNecessaryIndexPrecedentPhase == POWER_OF_TWO_MINUS_ONE) {
						currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase] |= indicatorNecessaryIndexPrecedentPhase;
						positionInNecessaryIndexPrecedentPhase++;
						positionBitInNecessaryIndexPrecedentPhase = 0L;
						significantBitPrecedentPhase = 1L;
						indicatorNecessaryIndexPrecedentPhase = currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase];
					} else {
						positionBitInNecessaryIndexPrecedentPhase++; 
						significantBitPrecedentPhase <<= 1L;
					}
					indexValuePrecedentPhase++;
				}
				currentNecessaryIndex[positionInNecessaryIndexPrecedentPhase] |= indicatorNecessaryIndexPrecedentPhase;
			}
			
			decal++;
			if (decal == index) decal = 0;
		}
	}
	
	
	

	private void calculNecessaryIndexPhase(int phaseNumber) {
		int numberCalculated = 0;
		for (int index = 0; index < indexMax; index++) {
			if (isNecessary(phaseNumber, index)) {
				results[phaseNumber][numberCalculated] = calculNecessaryIndexPhase(phaseNumber, index);
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

//	private void calculNecessaryIndexPhaseBis(int phaseNumber) {
//		int indexResultatCorrige = 0;
//		int indexResultat = 0;
//		for (int index = 0; index < necessaryIndex[phaseNumber].length; index++) {
//			long indicateur = necessaryIndex[phaseNumber][index];
//			if (indicateur != 0) {
//				indexResultatCorrige = calculNecessaryIndexPhase(phaseNumber, indexResultat, necessaryIndex[phaseNumber][index], indexResultatCorrige);
//			}
//			indexResultat += POWER_OF_TWO;
//		}
//	}
//
//	private int calculNecessaryIndexPhase(int phaseNumber, int indexResultat, long indicateur, int numberCalculated) {
//		boolean[] necessary = new boolean[(int) POWER_OF_TWO];
//		long bit = 1L;
//		for (int i = 0; i < necessary.length; i++) {
//			necessary[i] = (indicateur & bit) != 0;
//		}
//
//		int[] indexPattern = new int[(int) POWER_OF_TWO];
//		int[] pattern = new int[(int) POWER_OF_TWO];
//		Arrays.fill(indexPattern, 0);
//		Arrays.fill(pattern, BASE_PATTERN[0]);
//		
//		int[] countdownIndexPattern = new int[(int) POWER_OF_TWO];
//		for (int i = 0; i < countdownIndexPattern.length; i++) {
//			countdownIndexPattern[i] = indexResultat + i - 1;
//		}
//		
//		int[] temporarySum = new int[(int) POWER_OF_TWO];
//
//		int indexResultatCorrigeSource = 0;
//		for (int indexSource = 0; indexSource < necessaryIndex[phaseNumber - 1].length; indexSource++) {
//			long indicateurSource = necessaryIndex[phaseNumber - 1][indexSource];
//			if (indicateurSource != 0) {
//				indexResultatCorrigeSource = calculNecessaryIndexPhase(indexResultat, necessary, indexPattern, pattern, countdownIndexPattern, temporarySum, indicateurSource, indexResultatCorrigeSource, results[phaseNumber - 1]);
//			}
//		}
//
//		for (int i = 0; i < temporarySum.length; i++) {
//			if (necessary[i]) {
//				results[phaseNumber][numberCalculated] = temporarySum[i];
//				numberCalculated++;
//			}
//		}
//
//		return numberCalculated;
//	}
//
//	private int calculNecessaryIndexPhase(int indexResultat, boolean[] indicateurCible, int[] indexPattern, int[] pattern, int[] countdownIndexPattern, int[] temporarySum, long indicateur, int indexResultatCorrigeSource, int[] source) {
//		boolean[] indicateurSource = new boolean[(int) POWER_OF_TWO];
//		long bit = 1L;
//		for (int i = 0; i < indicateurSource.length; i++) {
//			indicateurSource[i] = (indicateur & bit) != 0;
//		}
//
//		for (int i = 0; i < indicateurSource.length; i++) {
//			if (indicateurSource[i]) {
//				int valueSource = source[indexResultatCorrigeSource];
//				for (int j = 0; j < indicateurCible.length; j++) {
//					if (indicateurCible[j]) {
//						temporarySum[j] = temporarySum[j] + valueSource * pattern[j];
//						countdownIndexPattern[j]--;
//						if (countdownIndexPattern[j] == 0) {
//							countdownIndexPattern[j] = indexResultat + j;
//							indexPattern[j]++;
//							pattern[j] = BASE_PATTERN[indexPattern[j] % BASE_PATTERN.length];
//						}
//					}
//				}
//				indexResultatCorrigeSource++;
//			}
//		}
//		
//		for (int j = 0; j < indicateurCible.length; j++) {
//			if (indicateurCible[j]) {
//				temporarySum[j] = temporarySum[j] % 10;
//				if (temporarySum[j] < 0) temporarySum[j] = - temporarySum[j];
//			}
//		}
//		
//		return indexResultatCorrigeSource;
//	}

}



