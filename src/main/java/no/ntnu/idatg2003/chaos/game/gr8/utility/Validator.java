package no.ntnu.idatg2003.chaos.game.gr8.utility;

import no.ntnu.idatg2003.chaos.game.gr8.math.Matrix2x2;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;

/**
 * The {@code Validator} class provides methods for validating input.
 * The class provides methods for checking if a number is negative and for checking if a number is
 * a double. The class also provides a method for validating if a {@code Vector2D} is null, or if
 * the coordinates are out of bounds. The class also provides a method for validating if a
 * {@code Matrix2x2} is null.
 *
 * <p>Example usage:
 * Validator.checkIfNegative(-1);
 * Validator.checkIfDouble(1.0);
 * Validator.validateVector2D(new Vector2D(1, 1));
 * }
 * </pre>
 *
 *
 * @author Audun Wetter
 * @author Kim Hejer
 * @version 1.0.0
 * @see Vector2D
 * @since 1.0.0
 */
public class Validator {

  // Private constructor to prevent instantiation
  private Validator() {
  }

  /**
   * Checks if a number is negative.
   *
   * @param number The number to check.
   * @return true if the number is negative, false otherwise.
   */
  public static boolean checkIfNegative(int number) {
    return number < 0;
  }

  /**
   * Checks if a number is a double.
   *
   * @param number The number to check.
   * @throws IllegalArgumentException if the number is not a double.
   */
  public static void checkIfDouble(double number) throws IllegalArgumentException {
    if (Double.isNaN(number)) {
      throw new IllegalArgumentException("The input has to be a number");
    }
  }

  /**
   * Validates that the input text is a valid double.
   *
   * @param text The input text.
   * @return The parsed double value.
   * @throws IllegalArgumentException if the input is not a valid double.
   */
  public static double validateAndGetDouble(String text) {
    try {
      return Double.parseDouble(text);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid input: " + text);
    }
  }

  /**
   * Validates a {@code Vector2D}.
   *
   * @param vector The vector to validate.
   * @throws IllegalArgumentException if the vector is null.
   */
  public static void validateVector2D(Vector2D vector) throws IllegalArgumentException {
    if (vector == null) {
      throw new IllegalArgumentException("Vector cannot be null");
    }
  }

  /**
   * Validates a {@code Matrix2x2}.
   *
   * @param matrix The vector to validate.
   * @throws IllegalArgumentException if the matrix is null.
   */
  public static void validateMatrix2x2(Matrix2x2 matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix2x2 cannot be null");
    }
  }

  /**
   * Validates a {@code Vector2D} with a minimum and maximum value.
   *
   * @param coords    The coordinates to validate.
   * @param minCoords The minimum coordinates.
   * @param maxCoords The maximum coordinates.
   * @throws IllegalArgumentException if the coordinates are out of bounds.
   */
  public static void validateCoords(Vector2D coords, Vector2D minCoords, Vector2D maxCoords)
      throws IllegalArgumentException {
    validateVector2D(coords);
    if (coords.getX0() < minCoords.getX0() || coords.getX0() > maxCoords.getX0()) {
      throw new IllegalArgumentException("X0 coordinate is out of bounds");
    }
    if (coords.getX1() < minCoords.getX1() || coords.getX1() > maxCoords.getX1()) {
      throw new IllegalArgumentException("X1 coordinate is out of bounds");
    }
  }

  /**
   * Validates a filename for the "Save As" functionality.
   *
   * @param filename the filename to validate.
   * @throws IllegalArgumentException if the filename is null, blank, contains spaces,
 *                                    is a reserved name, or does not end with ".txt"
   */
  public static void validateSaveAsFilename(String filename) {
    if (filename == null || filename.isBlank()) {
      throw new IllegalArgumentException("File name cannot be null or empty");
    } else if (filename.contains(" ")) {
      throw new IllegalArgumentException("File name cannot contain spaces");
    } else if (filename.equals(GlobalVariables.SIERPINSKI_FILE)
        || filename.equals(GlobalVariables.BARNSLEY_FILE)
        || filename.equals(GlobalVariables.JULIA_FILE)
        || filename.equals(GlobalVariables.RANDOM_STRING)) {
      throw new IllegalArgumentException("File name cannot be a reserved name");
    } else if (!filename.endsWith(".txt")) {
      throw new IllegalArgumentException("File name has to end with .txt");
    }
  }

  /**
   * Validates a filename for the "Save" functionality.
   *
   * @param filename the filename to validate.
   * @throws IllegalArgumentException if the filename is null, blank,
*                                     or is a reserved name
   */
  public static void validateSaveFilename(String filename) {
    if (filename == null || filename.isBlank()) {
      throw new IllegalArgumentException("File name cannot be null or empty");
    } else if (filename.equals(GlobalVariables.SIERPINSKI_FILE)
        || filename.equals(GlobalVariables.BARNSLEY_FILE)
        || filename.equals(GlobalVariables.JULIA_FILE)
        || filename.equals(GlobalVariables.RANDOM_STRING)) {
      throw new IllegalArgumentException("File name cannot be a reserved name");
    }
  }
}
