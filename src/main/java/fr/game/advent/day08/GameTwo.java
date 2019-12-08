package fr.game.advent.day08;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;

public class GameTwo extends AbstractGame<List<Integer>, Integer> {

	private static final String INPUT_FILENAME = "day08/input-day08-1";
	
	private static final int HIGH = 6;
	private static final int WIDTH = 25;
	
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, s -> s.chars().mapToObj(c -> new Integer(c - '0')).collect(Collectors.toList()));
	}

	@Override
	public Integer play(List<List<Integer>> inputs) {
		List<Integer[][]> layers = toLayers(inputs.get(0));
		for (int i = 0; i < HIGH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				int color = findColor(layers, i, j);
				System.out.print(color == 0 ? " " : "*");
			}
			System.out.println();
		}
		return 0;
	}

	private int findColor(List<Integer[][]> layers, int i, int j) {
		for (Integer[][] layer : layers) {
			if (layer[i][j] != 2) return layer[i][j];
		}
		return 0;
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
