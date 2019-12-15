package fr.game.advent.day13;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import fr.game.advent.day13.program.model.Program;
import fr.game.advent.day13.program.services.ProgramServicesI;
import fr.game.advent.day13.program.services.implementation.ProgramServices;
import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameOne extends AbstractGame<Long, Long> {
	
	private static final String INPUT_FILENAME = "day13/input-day13-1";
	
	private Logger log;
	private ProgramServicesI programServices = new ProgramServices();

	public GameOne() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Long::new);
		this.log = LoggerUtils.getLogger();
	}


	private Map<Point, TileId> tiles;
	private Long minX, maxX;
	private Long minY, maxY;
	
	@Override
	public Long play(List<Long> intcodeProgram) {
		try {
			String output = runArcadeCabinet(intcodeProgram);
			log.info("Arcade cabinet launched !");
			loadTiles(output);
			log.info("Tiles read !");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return tiles.values().stream().filter(TileId.BLOCK::equals).count();
	}


	public void loadTiles(String output) {
		tiles = new HashMap<>();
		String[] outputs = output.split("\n");
		int index = 0;
		while (index < outputs.length) {
			Long x = Long.valueOf(outputs[index]);
			Long y = Long.valueOf(outputs[index + 1]);
			TileId tileId = TileId.values()[Integer.valueOf(outputs[index + 2])];
			Point tile = new Point(x, y);
			if (tiles.containsKey(tile)) printTiles();
			tiles.put(tile, tileId);
			log.info(String.format("Tile %s -> %s", tile, tileId));
			index = index + 3;
		}
		printTiles();
	}


	private void printTiles() {
		if (minX == null) getMinAndMAx();
		System.out.println("");
		System.out.println("WIDTH  : " + (maxX - minX + 1));
		System.out.println("HEIGHT : " + (maxY - minY + 1));
		System.out.println("");
		for (long y = maxY; y >= minY; y--) {
			for (long x = minX; x <= maxX; x++) {
				Point currentPoint = new Point(x, y);
				TileId tileId = tiles.getOrDefault(currentPoint, TileId.EMPTY);
				System.out.print(tileId.getRepresentation());
			}
			System.out.println();
		}
	}


	private void getMinAndMAx() {
		minX = tiles.keySet().stream().mapToLong(Point::getX).min().getAsLong();
		maxX = tiles.keySet().stream().mapToLong(Point::getX).max().getAsLong();
		
		minY = tiles.keySet().stream().mapToLong(Point::getY).min().getAsLong();
		maxY = tiles.keySet().stream().mapToLong(Point::getY).max().getAsLong();
	}


	private String runArcadeCabinet(List<Long> intcodeProgram) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Program arcadeCabinetProgram = new Program(intcodeProgram, System.in, outputStream);
		programServices.execute(arcadeCabinetProgram);
		return outputStream.toString();
	}
}
