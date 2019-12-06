package fr.game.advent.day05.program.instructions;

import java.util.HashMap;
import java.util.Map;

public enum Mode {

	POSITION_MODE(0),
	IMMEDIATE_MODE(1);
	
	private Integer intMode;

	private Mode(Integer mode) {
		this.intMode = mode;
	}
	
	private static Map<Integer, Mode> modesByIntMode;
	
	private static void fillModesByIntModeValue() {
		modesByIntMode = new HashMap<>();
		for (Mode mode : Mode.values()) {
			modesByIntMode.put(mode.intMode, mode);
		}
	}

	public static Mode toMode(Integer intMode) {
		if (modesByIntMode == null) {
			fillModesByIntModeValue();
		}
		return modesByIntMode.get(intMode);
	}
}
