package fr.game.advent.day11.program.model;

import java.util.HashMap;
import java.util.Map;

public enum Mode {

	POSITION_MODE(0),
	IMMEDIATE_MODE(1),
	RELATIVE_MODE(2)
	;
	
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

	public static Mode toMode(Long longMode) {
		if (modesByIntMode == null) {
			fillModesByIntModeValue();
		}
		return modesByIntMode.get(longMode.intValue());
	}
}
