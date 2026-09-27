package no.ntnu.idatg2003.chaos.game.gr8.utility;

/**
 * The {@code LoggerInitializationException} class is an exception that is thrown when an error
 * occurs during the initialization of the {@code ErrorLogger}.
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * try {
 *     // Code that may throw an exception during logger initialization
 * } catch (IOException e) {
 *     throw new LoggerInitializationException("Failed to initialize logger", e);
 * }
 * }
 * </pre>
 *
 *
 * @author Kim Hejer
 * @version 1.1.0
 * @see Exception
 * @since 1.1.0
 */
public class LoggerInitializationException extends Exception {

  /**
   * Constructs a new {@code LoggerInitializationException} with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a call to
   * {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   */
  public LoggerInitializationException(String message, Throwable cause) {
    super(message, cause);
  }
}

