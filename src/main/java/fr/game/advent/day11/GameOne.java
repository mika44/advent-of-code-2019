package fr.game.advent.day11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import fr.game.advent.day11.program.Program;
import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameOne extends AbstractGame<Long, Integer> {
	
	private static final String INPUT_FILENAME = "day11/input-day11-1";
	
	private Logger log;
	private Set<ColoredPoint> visitedPoints;

	private Thread emergencyHullPaintingRobot;

	private BufferedWriter writerToRobot;

	private BufferedReader readerFromRobot;

	public GameOne() {
		super(FileUtils::getListFromOneLineFileCommaSeparated, INPUT_FILENAME, Long::new);
		this.log = LoggerUtils.getLogger();
	}


	@Override
	public Integer play(List<Long> intcodeProgram) {
		try {
			PipedOutputStream outputStreamToPipeRobotInputStream = new PipedOutputStream();
			PipedInputStream inputStreamToPipeRobotOutputStream = new PipedInputStream();
			writerToRobot = new BufferedWriter(new OutputStreamWriter(outputStreamToPipeRobotInputStream));
			readerFromRobot = new BufferedReader(new InputStreamReader(inputStreamToPipeRobotOutputStream));
			Program robotBrain = new Program(intcodeProgram, new PipedInputStream(outputStreamToPipeRobotInputStream), new PipedOutputStream(inputStreamToPipeRobotOutputStream));
			emergencyHullPaintingRobot = new Thread(() -> robotBrain.execute());

			visitedPoints = new HashSet<>();
			ColoredPoint currentRobotPosition = new ColoredPoint(0, 0, ColoredPoint.BLACK);
			Direction currentRobotDirection = Direction.UP;
			emergencyHullPaintingRobot.start();

			while (emergencyHullPaintingRobot.isAlive()) {
				log.info("Visite du point " + currentRobotPosition);
				visitedPoints.add(currentRobotPosition);
				sendMessageToRobot("" + currentRobotPosition.getColor());
				int colorToApply = readMessageFromRobot().intValue();
				if (!emergencyHullPaintingRobot.isAlive()) break;
				log.info("Coloration du point en " + colorToApply);
				currentRobotPosition.setColor(colorToApply);
				int turnDirectionToApply = readMessageFromRobot().intValue();
				if (!emergencyHullPaintingRobot.isAlive()) break;
				log.info("Changement direction en " + turnDirectionToApply);
				currentRobotDirection = Direction.turn(currentRobotDirection, turnDirectionToApply);
				currentRobotPosition = moveDirection(currentRobotPosition, currentRobotDirection);
				visitedPoints.add(currentRobotPosition);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return visitedPoints.size();
	}


	private ColoredPoint moveDirection(ColoredPoint currentRobotPosition, Direction currentRobotDirection) {
		int dx = currentRobotDirection.getMove().getX();
		int dy = currentRobotDirection.getMove().getY();
		final ColoredPoint cible = new ColoredPoint(currentRobotPosition.getX() + dx, currentRobotPosition.getY() + dy, ColoredPoint.BLACK);
		return visitedPoints.stream().filter(v -> v.equals(cible)).findFirst().orElse(cible);
	}


	private Long readMessageFromRobot() {
		try {
			String inputString = null;
			while (inputString == null && emergencyHullPaintingRobot.isAlive()) {
				inputString = readerFromRobot.readLine();
				log.info("waiting for robot output");
			}
			if (emergencyHullPaintingRobot.isAlive()) return Long.valueOf(inputString);
			return 0L;
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
	}


	private void sendMessageToRobot(String inputRobotMessage) throws IOException {
		writerToRobot.write(inputRobotMessage);
		writerToRobot.newLine();
		writerToRobot.flush();
	}
}
