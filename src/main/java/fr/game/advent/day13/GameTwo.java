package fr.game.advent.day13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
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

public class GameTwo extends AbstractGame<Long, Long> {
	
	private static final String INPUT_FILENAME = "day13/input-day13-1";
	
	private Logger log;
	private ProgramServicesI programServices = new ProgramServices();
	private Thread arcadeCabinet;
	private BufferedWriter writerToArcadeCabinet;
	private BufferedReader readerFromArcadeCabinet;

	public GameTwo() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Long::new);
		this.log = LoggerUtils.getLogger();
	}


	private Map<Point, TileId> tiles;
	private Long minX, maxX;
	private Long minY, maxY;
	private Long score;
	private Point positionPaddle;
	private Point positionBall;
	private Point previousPositionBall;
	
	@Override
	public Long play(List<Long> intcodeProgram) {
		// play for free
		intcodeProgram.set(0, 2L);
		
		try {
			tiles = new HashMap<>();
			runArcadeCabinet(intcodeProgram);
			log.info("Arcade cabinet launched !");
			loadTiles();
			log.info("Tiles read !");
		
			// loop until end of game
			boolean endOfGame = false;
			while (!endOfGame) {
				sendMessageToArcadeCabinet(moveJostick());
				loadTiles();
				log.info("Tiles read !");
				endOfGame = !arcadeCabinet.isAlive();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return score;
	}


	private String moveJostick() {
		if (previousPositionBall == null) return "" + JoystickOrder.NEUTRAL.getJoystickOrder();
		
		// si la balle descend, on se contente de se mettre en dessous
		if (previousPositionBall.getY() > positionBall.getY()) {
			System.out.println(" xPaddle = " + positionPaddle.getX() + " -> x Ball " + positionBall.getX());
			return "" + getJoystickOrder(positionPaddle.getX() - positionBall.getX());
		}
		
		// Sinon on estime le point de croisement avec le paddle
		long dxMoveBall = previousPositionBall.getX() - positionBall.getX();
		long xEstimatedForBallAtPaddleLevel = positionBall.getX();
		long y = positionBall.getY();
		while (y > positionPaddle.getY()) {
			xEstimatedForBallAtPaddleLevel = xEstimatedForBallAtPaddleLevel + dxMoveBall;
			if (xEstimatedForBallAtPaddleLevel == minX) {
				xEstimatedForBallAtPaddleLevel = xEstimatedForBallAtPaddleLevel + 1;
				dxMoveBall = +1L;
			}
			if (xEstimatedForBallAtPaddleLevel == maxX) {
				xEstimatedForBallAtPaddleLevel = xEstimatedForBallAtPaddleLevel - 1;
				dxMoveBall = -1L;
			}
		}
		System.out.println(" xPaddle = " + positionBall.getX() + " -> x Ball estimé " + xEstimatedForBallAtPaddleLevel);
		return "" + getJoystickOrder(positionPaddle.getX() - xEstimatedForBallAtPaddleLevel);
	}


	private Long getJoystickOrder(long dx) {
		if (dx == 0) return JoystickOrder.NEUTRAL.getJoystickOrder();
		if (dx > 0) return JoystickOrder.TO_LEFT.getJoystickOrder();
		return JoystickOrder.TO_RIGHT.getJoystickOrder();
	}


	public void loadTiles() {
		List<Long> output = readMessageFromArcadeCabinet();
		int index = 0;
		while (index < output.size() - 1) {
			Point tile = new Point(output.get(index), output.get(index + 1));
			if (tile.equals(new Point(-1L, 0))) {
				score = output.get(index + 2);
			} else {
				TileId tileId = TileId.toTileId(output.get(index + 2));
				tiles.put(tile, tileId);
				switch (tileId) {
				case BALL: 
					previousPositionBall = positionBall;
					positionBall = tile;
					break;
				case HORIZONTAL_PADDLE: 
					positionPaddle = tile;
				default:
					break;
				}
			}
			index = index + 3;
		}
		printTiles();
	}


	private void printTiles() {
		if (minX == null) getMinAndMAx();
		System.out.println("");
		System.out.println("SCORE : " + score);
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
		minX = tiles.keySet().stream().mapToLong(Point::getX).filter(x -> x >= 0).min().orElse(0L);
		maxX = tiles.keySet().stream().mapToLong(Point::getX).max().orElse(0L);
		minY = tiles.keySet().stream().mapToLong(Point::getY).min().orElse(0L);
		maxY = tiles.keySet().stream().mapToLong(Point::getY).max().orElse(0L);
	}


	private void runArcadeCabinet(List<Long> intcodeProgram) throws IOException {
		PipedOutputStream outputStreamToPipeArcadeCabinetInputStream = new PipedOutputStream();
		writerToArcadeCabinet = new BufferedWriter(new OutputStreamWriter(outputStreamToPipeArcadeCabinetInputStream));
		PipedInputStream inputStreamToPipeArcadeCabinetOutputStream = new PipedInputStream(8192);
		readerFromArcadeCabinet = new BufferedReader(new InputStreamReader(inputStreamToPipeArcadeCabinetOutputStream));
		
		Program arcadeCabinetProgram = new Program(intcodeProgram, new PipedInputStream(outputStreamToPipeArcadeCabinetInputStream), new PipedOutputStream(inputStreamToPipeArcadeCabinetOutputStream));
		arcadeCabinet = new Thread(() -> programServices.execute(arcadeCabinetProgram));
		arcadeCabinet.start();
		try {
			arcadeCabinet.join(1_000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<Long> readMessageFromArcadeCabinet() {
		List<Long> output = new ArrayList<>();
		try {
			try {
				arcadeCabinet.join(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			while (readerFromArcadeCabinet.ready()) {
				String inputString = readerFromArcadeCabinet.readLine();
				output.add(Long.valueOf(inputString));
			}
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	private void sendMessageToArcadeCabinet(String inputMessage) throws IOException {
		System.out.println("Message envoyé : " + inputMessage);
		writerToArcadeCabinet.write(inputMessage);
		writerToArcadeCabinet.newLine();
		writerToArcadeCabinet.flush();
	}
}
