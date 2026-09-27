package no.ntnu.idatg2003.chaos.game.gr8.utility;

import static java.util.logging.Level.SEVERE;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The {@code ErrorLogger} class logs errors to the file "error_log".
 *
 * <p>The {@code ErrorLogger} class contains methods for logging errors to a file. The class uses
 * the {@code Logger} class from the {@code java.util.logging} package.
 *
 * <p>The {@code ErrorLogger} class is a singleton class. This means that only one instance of the
 * class can exist at a time. The class has a private constructor and a static method for getting
 * the instance of the class.
 *
 * @author Kim Hejer
 * @version 1.1.1
 * @see Logger
 * @see SimpleFormatter
 * @see FileHandler
 * @see RuntimeException
 * @since 1.1.0
 */
public class ErrorLogger {
  private static ErrorLogger errorLogger;
  private Logger logger;

  private ErrorLogger() {
    try {
      initializeLogger();
    } catch (LoggerInitializationException e) {
      // Handle initialization exception internally
      System.err.println("Failed to initialize logger: " + e.getMessage());
    }
  }

  /**
   * The {@code getLogger} method returns the instance of the {@code ErrorLogger} class.
   *
   * <p>If the instance does not exist, the method creates a new instance of the {@code ErrorLogger}
   * class and returns it.
   *
   * @return The instance of the {@code ErrorLogger} class.
   * @since 1.0.0
   */
  public static synchronized ErrorLogger getLogger() {
    if (errorLogger == null) {
      errorLogger = new ErrorLogger();
    }
    return errorLogger;
  }

  /**
   * The {@code logError} method logs an error to the file "error_log.txt".
   *
   * <p>The method takes a {@code String} as input and logs it to the file "error_log.txt".
   *
   * @param message The error message to be logged.
   * @since 1.0.0
   */
  public void logError(String message) {
    logError(SEVERE, message);
  }

  /**
   * The {@code logError} method logs an error to the file "error_log.txt".
   *
   * <p>The method takes a {@code Level} and a {@code String} as input and logs the message with
   * the given level to the file "error_log.txt".
   *
   * @param level   The level of the error.
   * @param message The error message to be logged.
   * @since 1.1.0
   */
  public void logError(Level level, String message) {
    logger.log(level, message);
  }

  private void initializeLogger() throws LoggerInitializationException {
    logger = Logger.getLogger(ErrorLogger.class.getName());
    logger.setUseParentHandlers(false);
    SimpleFormatter formatter = new SimpleFormatter();
    FileHandler handler;

    try {
      handler = new FileHandler("error_log.txt");
    } catch (IOException e) {
      throw new LoggerInitializationException("Unable to initialize logger", e);
    }
    handler.setFormatter(formatter);
    logger.addHandler(handler);
  }


}
