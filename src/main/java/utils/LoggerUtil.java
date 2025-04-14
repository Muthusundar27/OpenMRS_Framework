package utils;

import org.apache.log4j.Logger;

public class LoggerUtil {

	private static Logger log = Logger.getLogger(LoggerUtil.class);

	// Log info messages to console and file
	public static void logInfo(String message) {
		log.info(message);
	}

	// Log error messages to console and file
	public static void logError(String message) {
		log.error(message);
	}

	// Log warnings to console and file
	public static void logWarn(String message) {
		log.warn(message);
	}
}
