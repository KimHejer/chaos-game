package no.ntnu.idatg2003.chaos.game.gr8.chaosgame;

import no.ntnu.idatg2003.chaos.game.gr8.math.Matrix2x2;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.AffineTransform2D;
import no.ntnu.idatg2003.chaos.game.gr8.utility.ErrorLogger;
import no.ntnu.idatg2003.chaos.game.gr8.utility.Validator;

/**
 * The {@code ChaosCanvas} class provides methods for creating and manipulating a
 * {@code ChaosCanvas}.
 * <p>
 * The class provides methods for putting and for getting a pixel on/from the {@code ChaosCanvas}
 * in the given coordinates.
 * </p>
 * <p>
 * The class also provides a method for clearing the {@code ChaosCanvas} and for getting
 * the canvas array.
 * </p>
 *
 * @author Kim Hejer
 * @version 1.0.0
 * @see Vector2D
 * @see AffineTransform2D
 * @see Validator
 * @since 1.0.0
 */

public class ChaosCanvas {

  private final int[][] canvas;
  private int width;
  private int height;
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private final AffineTransform2D transformCoordsToIndices;
  private final ErrorLogger errorLogger;

  /**
   * Constructs a new {@code ChaosCanvas} with the given width, height, minimum coordinates and
   * maximum coordinates. The coordinates have to be valid non-null {@code Vector2D} objects.
   *
   * @param width     The width of the canvas.
   * @param height    The height of the canvas.
   * @param minCoords The minimum coordinates of the fractal.
   * @param maxCoords The maximum coordinates of the fractal.
   */
  public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords)
      throws IllegalArgumentException {
    errorLogger = ErrorLogger.getLogger();
    setWidth(width);
    setHeight(height);
    setMinCoords(minCoords);
    setMaxCoords(maxCoords);
    this.canvas = new int[width][height];
    this.transformCoordsToIndices = new AffineTransform2D(new Matrix2x2(0,
        (height - 1) / (minCoords.getX1() - maxCoords.getX1()),
        (width - 1) / (maxCoords.getX0() - minCoords.getX0()),
        0),
        new Vector2D(((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1()),
            ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0())));

  }


  private void setMinCoords(Vector2D minCoords) throws IllegalArgumentException {
    Validator.validateVector2D(minCoords);
    this.minCoords = minCoords;
  }

  private void setMaxCoords(Vector2D maxCoords) throws IllegalArgumentException {
    Validator.validateVector2D(maxCoords);
    this.maxCoords = maxCoords;
  }

  private void setWidth(int width) throws IllegalArgumentException {
    if (Validator.checkIfNegative(width)) {
      throw new IllegalArgumentException("Width cannot be negative");
    }
    this.width = width;
  }

  private void setHeight(int height) throws IllegalArgumentException {
    if (Validator.checkIfNegative(height)) {
      throw new IllegalArgumentException("Height cannot be negative");
    }
    this.height = height;
  }

  /**
   * Checks if a pixel is on the canvas in the given coordinates.
   *
   * @param point The coordinates to check.
   * @return 1 if the pixel is on the canvas, 0 if it is not.
   */
  public int getPixel(Vector2D point) throws ArrayIndexOutOfBoundsException,
      IllegalArgumentException {
    Validator.validateVector2D(point);
    Vector2D indices = transformCoordsToIndices.transform(point);
    int x = (int) indices.getX0();
    int y = (int) indices.getX1();
    return canvas[x][y];
  }

  /**
   * Puts a pixel on the canvas in the given coordinates.
   *
   * @param point The coordinates to put the pixel.
   *              The coordinates have to be within the canvas bounds. (minCoords, maxCoords)
   */
  public void putPixel(Vector2D point) {
    try {
      Validator.validateCoords(point, minCoords, maxCoords);
      Vector2D indices = transformCoordsToIndices.transform(point);
      int x = (int) indices.getX0();
      int y = (int) indices.getX1();
      canvas[x][y] = 1;
    } catch (ArrayIndexOutOfBoundsException e) {
      errorLogger.logError(e.getMessage());
      errorLogger.logError("The point is outside the canvas bounds");
    } catch (IllegalArgumentException e) {
      errorLogger.logError(e.getMessage());
    }
  }

  /**
   * Gets the canvas array as a 2D integer array.
   *
   * @return The canvas array.
   */
  public int[][] getCanvasArray() {
    return canvas;
  }

  /**
   * Clears the {@code ChaosCanvas} by setting all pixels back to 0.
   */
  public void clear() {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        canvas[i][j] = 0;
      }
    }
  }
}