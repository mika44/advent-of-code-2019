package fr.game.advent.day08;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameOne extends AbstractGame<List<Integer>, Integer> {

	private static final String INPUT_FILENAME = "day08/input-day08-1";
	
	private static final int HIGH = 6;
	private static final int WIDTH = 25;
	
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, s -> s.chars().mapToObj(c -> new Integer(c - '0')).collect(Collectors.toList()));
	}

	@Override
	public Integer play(List<List<Integer>> inputs) {
		List<Integer[][]> layers = toLayers(inputs.get(0));
		int layerFewest0Digits = findLayerFewest0Digits(layers);
		int numberOf1Digits = 0;
		int numberOf2Digits = 0;
		for (int i = 0; i < HIGH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				int digit = layers.get(layerFewest0Digits)[i][j];
				if (digit == 1) numberOf1Digits++;
				if (digit == 2) numberOf2Digits++;
			}
		}
		return numberOf1Digits * numberOf2Digits;
	}

	private int findLayerFewest0Digits(List<Integer[][]> layers) {
		int min = HIGH * WIDTH + 1;
		int minLayer = 0;
		for (int l = 0; l < layers.size(); l++) {
			Integer[][] layer = layers.get(l);
			int numberOf0Digits = 0;
			for (int i = 0; i < HIGH; i++) {
				for (int j = 0; j < WIDTH; j++) {
					if (layer[i][j] == 0) numberOf0Digits++;
				}
			}
			if (numberOf0Digits < min) {
				min = numberOf0Digits;
				minLayer = l;
			}
		}
		return minLayer;
	}

	private List<Integer[][]> toLayers(List<Integer> data) {
		List<Integer[][]> result = new ArrayList<>();
		int indexData = 0;
		while (indexData < data.size()) {
			Integer[][] layer = new Integer[HIGH][WIDTH];
			for (int i = 0; i < HIGH; i++) {
				for (int j = 0; j < WIDTH; j++) {
					layer[i][j] = data.get(indexData);
					indexData++;
				}
			}
			result.add(layer);
		}
		return result;
	}
}
