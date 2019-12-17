package fr.game.advent.day15;

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
import java.util.Map.Entry;
import java.util.logging.Logger;

import fr.game.advent.day13.program.model.Program;
import fr.game.advent.day13.program.services.ProgramServicesI;
import fr.game.advent.day13.program.services.implementation.ProgramServices;
import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameOne extends AbstractGame<Long, Long> {
	
	private static final String INPUT_FILENAME = "day15/input-day15-1";
	
	private Logger log;
	private ProgramServicesI programServices = new ProgramServices();
	private Thread repairDroid;
	private BufferedWriter writerToRepairDroid;
	private BufferedReader readerFromRepairDroid;

	public GameOne() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Long::new);
		this.log = LoggerUtils.getLogger();
	}


	private Map<Point, PointStatus> space;
	private Long minX, maxX;
	private Long minY, maxY;
	private Point positionRepairDroid;
	private MovementCommand lastMovementCommand;
	private MovementCommand currentMovementCommand;
	private PointStatus currentMovementResult;
	
	@Override
	public Long play(List<Long> intcodeProgram) {
		try {
			space = new HashMap<>();
			runRepairDroid(intcodeProgram);
			log.info("Repair Droid launched !");
		
			// loop until end of search
			positionRepairDroid = new Point(0, 0);
			space.put(new Point(0, 0), PointStatus.VOID);
			lastMovementCommand = null;
			currentMovementCommand = null;
			currentMovementResult = null;
			boolean endOfSearch = false;
			while (!endOfSearch) {
				currentMovementCommand = nextMove();
				sendMessageToRepairDroid(currentMovementCommand);
				treatResponseRepairDroid();
				printSpace();
				endOfSearch = !repairDroid.isAlive();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return fewestNumberOfMovementCommandsToReachOS();
	}


	private Long fewestNumberOfMovementCommandsToReachOS() {
		Point os = space.entrySet().stream()
					.filter(e -> e.getValue().equals(PointStatus.OS))
					.map(Entry::getKey)
					.findFirst()
					.get();
		System.out.println("OS -> " + os);
		return 0L;
	}


	private void treatResponseRepairDroid() {
		StatusCode statusCodeReturned = StatusCode.toStatusCode(readMessageFromRepairDroid().get(0));
		long xCible = positionRepairDroid.getX() + currentMovementCommand.getMove().getX();
		long yCible = positionRepairDroid.getY() + currentMovementCommand.getMove().getY();
		if (!statusCodeReturned.equals(StatusCode.HIT_A_WALL)) {
			positionRepairDroid.setX(xCible);
			positionRepairDroid.setY(yCible);
		}
		currentMovementResult = statusCodeReturned.getPointStatus();
		System.out.println("Point analysed : " + new Point(xCible, yCible) + " -> " + currentMovementResult);
		space.put(new Point(xCible, yCible), currentMovementResult);
	}


	private MovementCommand nextMove() {
		if (currentMovementCommand == null) return MovementCommand.NORTH;
		if (currentMovementResult.equals(PointStatus.VOID)) return currentMovementCommand;
		return MovementCommand.turnToLeft(currentMovementCommand);
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
		try {
			repairDroid.join(1_000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private List<Long> readMessageFromRepairDroid() {
		List<Long> output = new ArrayList<>();
		try {
			try {
				repairDroid.join(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
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
