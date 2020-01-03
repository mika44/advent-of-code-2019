package fr.game.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtils {
	
	private static Logger logger;
	private static ConsoleHandler consoleHandler;

	private static void initLogger() {
		logger = Logger.getLogger("Game");
		logger.setUseParentHandlers(false);
		consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.WARNING);
		consoleHandler.setFormatter(new SimpleFormatter() {
	          private static final String format = "[%1$tF %1$tT] [%2$s] %6$s %n";

	          @Override
	          public synchronized String format(LogRecord lr) {
	              return String.format(format,
	            		  lr.getMillis(),
	            		  lr.getLoggerName(),
	            		  lr.getSourceClassName(),
	            		  lr.getSourceMethodName(),
	                      lr.getLevel(),
	                      lr.getMessage()
	              );
	          }
	      });
		logger.addHandler(consoleHandler);
		logger.setLevel(Level.WARNING);
	}

	public static void setLevel(Level level) {
		getLogger().setLevel(level);
		consoleHandler.setLevel(level);
	}
	
	public static Logger getLogger() {
		if (logger == null) {
			initLogger();
		}
		return logger;
	}
}
