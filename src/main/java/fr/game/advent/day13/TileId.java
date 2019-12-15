package fr.game.advent.day13;

import java.util.HashMap;
import java.util.Map;

public enum TileId {
	EMPTY(" "),
	WALL("#"),
	BLOCK("$"),
	HORIZONTAL_PADDLE("="),
	BALL("*");
	
	private String representation;
	
	private TileId(String representation) {
		this.representation = representation;
	}

	public String getRepresentation() {
		return representation;
	}
	
	private static Map<Long, TileId> tileIds;
	
	public static TileId toTileId(Long tileId) {
		if (tileIds == null) {
			fillTileIds();
		}
		return tileIds.get(tileId);
	}

	private static void fillTileIds() {
		tileIds = new HashMap<>();
		for (int i = 0; i < TileId.values().length; i++) {
			tileIds.put(new Long(i),  TileId.values()[i]);
		}
		
	}
}
