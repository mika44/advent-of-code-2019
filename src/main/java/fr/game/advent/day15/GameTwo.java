package fr.game.advent.day15;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
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
	
	private static final String INPUT_FILENAME = "day15/input-day15-1";
	
	private Logger log;
	private ProgramServicesI programServices = new ProgramServices();
	private Thread repairDroid;
	private BufferedWriter writerToRepairDroid;
	private BufferedReader readerFromRepairDroid;

	private Map<Point, PointStatus> space;
	private Long minX, maxX;
	private Long minY, maxY;

	private Point positionRepairDroid;
	private Deque<MovementCommand> pathFromOrigin;
	private MovementCommand currentMovementCommand;
	private PointStatus currentMovementResult;
	private boolean backOnPath;
	private long maxPathLength;
	
	public GameTwo() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Long::new);
		this.log = LoggerUtils.getLogger();
	}


	@Override
	public Long play(List<Long> intcodeProgram) {
		try {
			space = new HashMap<>();
			runRepairDroid(intcodeProgram);
			log.info("Repair Droid launched !");
			findOS();
			System.out.println("OS found - fill with oxygen");
			exploreAll();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return maxPathLength;
	}


	public void findOS() throws IOException {
		positionRepairDroid = new Point(0, 0);
		space.put(new Point(0, 0), PointStatus.VOID);
		pathFromOrigin = new LinkedList<>();
		currentMovementCommand = null;
		currentMovementResult = null;
		backOnPath = false;
		boolean endOfSearch = false;
		while (!endOfSearch) {
			currentMovementCommand = nextMove();
			sendMessageToRepairDroid(currentMovementCommand);
			treatResponseRepairDroid();
			//printSpace();
			endOfSearch = currentMovementResult.equals(PointStatus.OS);
		}
	}

	public void exploreAll() throws IOException {
		// positionRepairDroid est sur OS
		space = new HashMap<>();
		space.put(new Point(positionRepairDroid.getX(), positionRepairDroid.getY()), PointStatus.OS);
		pathFromOrigin = new LinkedList<>();
		currentMovementCommand = null;
		currentMovementResult = null;
		backOnPath = false;
		maxPathLength = -1L;
		boolean endOfSearch = false;
		while (!endOfSearch) {
			currentMovementCommand = nextMove();
			sendMessageToRepairDroid(currentMovementCommand);
			treatResponseRepairDroid();
			printSpace();
			if (pathFromOrigin.size() > maxPathLength) maxPathLength = pathFromOrigin.size();
			endOfSearch = pathFromOrigin.isEmpty() && backOnPath;
		}
	}

	private void treatResponseRepairDroid() {
		StatusCode statusCodeReturned = StatusCode.toStatusCode(readMessageFromRepairDroid().get(0));
		Point cible = getCible(positionRepairDroid, currentMovementCommand);
		if (!statusCodeReturned.equals(StatusCode.HIT_A_WALL)) {
			positionRepairDroid.setX(cible.getX());
			positionRepairDroid.setY(cible.getY());
		}
		currentMovementResult = statusCodeReturned.getPointStatus();
		space.put(cible, currentMovementResult);
	}


	private Point getCible(Point point, MovementCommand movementCommand) {
		return new Point(point.getX() + movementCommand.getMove().getX(), point.getY() + movementCommand.getMove().getY());
	}


	private MovementCommand nextMove() {
		if (currentMovementResult == null) return MovementCommand.NORTH;

		if (!backOnPath && (currentMovementResult.equals(PointStatus.VOID) || currentMovementResult.equals(PointStatus.OS))) {
			pathFromOrigin.push(currentMovementCommand);
			return currentMovementCommand;
		}
		
		for (MovementCommand movementCommand : MovementCommand.values()) {
			PointStatus neighbourStatus = space.getOrDefault(getCible(positionRepairDroid, movementCommand), PointStatus.UNKNOWN);
			if (neighbourStatus.equals(PointStatus.UNKNOWN)) {
				backOnPath = false;
				return movementCommand;
			}
		}
	
		backOnPath = true;
		return MovementCommand.opposite(pathFromOrigin.pop());
	}

	
	private void getMinAndMAx() {
		minX = space.keySet().stream().mapToLong(Point::getX).min().orElse(0L);
		maxX = space.keySet().stream().mapToLong(Point::getX).max().orElse(0L);
		minY = space.keySet().stream().mapToLong(Point::getY).min().orElse(0L);
		maxY = space.keySet().stream().mapToLong(Point::getY).max().orElse(0L);
	}
	
	
	private void printSpace() {
		getMinAndMAx();
		System.out.println();
		System.out.println("------------------");
		System.out.println(String.format("min = (%d, %d) - max = (%d, %d)", minX, minY, maxX, maxY));
		System.out.println(String.format("last move = (%s) - last status = (%s)", currentMovementCommand, currentMovementResult));
		System.out.println(String.format("pos droid = (%s) - path size = (%d)", positionRepairDroid, pathFromOrigin.size()));
		for (long y = maxY + 1; y >= minY - 1; y--) {
			for (long x = minX - 1; x <= maxX + 1; x++) {
				Point currentPoint = new Point(x, y);
				if (currentPoint.equals(positionRepairDroid)) {
					System.out.print("D");
				} else {
					System.out.print(space.getOrDefault(currentPoint, PointStatus.UNKNOWN).getPrintStatus());
				}
			}
			System.out.println();
		}
	}

	
	private void runRepairDroid(List<Long> intcodeProgram) throws IOException {
		PipedOutputStream outputStreamToPipeRepairDroidInputStream = new PipedOutputStream();
		writerToRepairDroid = new BufferedWriter(new OutputStreamWriter(outputStreamToPipeRepairDroidInputStream));
		PipedInputStream inputStreamToPipeRepairDroidOutputStream = new PipedInputStream(8192);
		readerFromRepairDroid = new BufferedReader(new InputStreamReader(inputStreamToPipeRepairDroidOutputStream));
		
		Program repairDroidProgram = new Program(intcodeProgram, new PipedInputStream(outputStreamToPipeRepairDroidInputStream), new PipedOutputStream(inputStreamToPipeRepairDroidOutputStream));
		repairDroid = new Thread(() -> programServices.execute(repairDroidProgram));
		repairDroid.start();
	}
	
	
	private List<Long> readMessageFromRepairDroid() {
		List<Long> output = new ArrayList<>();
		try {
			while (!readerFromRepairDroid.ready()) {
				
			}
			while (readerFromRepairDroid.ready()) {
				String inputString = readerFromRepairDroid.readLine();
				output.add(Long.valueOf(inputString));
			}
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	
	private void sendMessageToRepairDroid(MovementCommand movementCommand) throws IOException {
		sendMessageToRepairDroid("" + movementCommand.getMovementCommandValue());
	}
	
	
	private void sendMessageToRepairDroid(String inputMessage) throws IOException {
		writerToRepairDroid.write(inputMessage);
		writerToRepairDroid.newLine();
		writerToRepairDroid.flush();
	}
}
