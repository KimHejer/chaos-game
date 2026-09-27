package no.ntnu.idatg2003.chaos.game.gr8.chaosgame;

import java.util.Random;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;

/**
 * The {@code ChaosGame} class provides methods for running a chaos game.
 *
 * <p>The class provides methods for running a fractal for a given number of steps.
 *
 * <p>The class also provides a method for getting the canvas used in the chaos game.
 *
 * @author Kim Hejer
 * @version 1.0.0
 * @see ChaosCanvas
 * @see ChaosGameDescription
 * @see Vector2D
 * @since 1.0.0
 */
public class ChaosGame {

  private ChaosCanvas chaosCanvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private final Random random;

  /**
   * Constructs a new {@code ChaosGame} with the given {@code ChaosCanvas} and
   * {@code ChaosGameDescription}.
   *
   * @param chaosCanvas The {@code ChaosCanvas} to draw on.
   * @param description The {@code ChaosGameDescription} of the chaos game.
   */
  public ChaosGame(ChaosCanvas chaosCanvas, ChaosGameDescription description) {
    this(chaosCanvas, description, new Vector2D(0, 0));
  }

  /**
   * Constructs a new {@code ChaosGame} with the given {@code ChaosCanvas} and
   * {@code ChaosGameDescription}.
   *
   * @param chaosCanvas  The {@code ChaosCanvas} to draw on.
   * @param description  The {@code ChaosGameDescription} of the chaos game.
   * @param currentPoint The {@code Vector2D} point to start from.
   */
  public ChaosGame(ChaosCanvas chaosCanvas,
                   ChaosGameDescription description,
                   Vector2D currentPoint) {
    setChaosCanvas(chaosCanvas);
    this.description = description;
    this.currentPoint = currentPoint;
    this.random = new Random();
  }

  /**
   * Gets the {@code ChaosCanvas} used in the chaos game.
   *
   * @return The {@code ChaosCanvas} used in the chaos game.
   */
  public ChaosCanvas getChaosCanvas() {
    return chaosCanvas;
  }

  /**
   * Runs the chaos game for the given number of steps. Takes a random transformation from the
   * description and applies it to the current point. The new point is then drawn on the canvas as
   * a 1.
   *
   * @param steps The number of steps to run the chaos game for.
   */
  public void runSteps(int steps) {
    if (description.getTransforms().isEmpty()) {
      throw new IllegalStateException(
          "Cannot run chaos game without transformations"
      );
    }

    int randomIndex;
    for (int i = 0; i < steps; i++) {
      randomIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(randomIndex).transform(currentPoint);
      chaosCanvas.putPixel(currentPoint);
    }
  }

  /**
   * Changes the description and canvas of the chaos game.
   *
   * @param description The new {@code ChaosGameDescription} to use.
   * @param width       The width of the new canvas.
   * @param height      The height of the new canvas.
   */
  public void changeDescriptionAndCanvas(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.chaosCanvas = new ChaosCanvas(width, height,
        description.getMinCoords(),
        description.getMaxCoords());
  }

  /**
   * Sets the {@code ChaosCanvas} used in the chaos game.
   *
   * @param chaosCanvas The {@code ChaosCanvas} to use in the chaos game.
   */
  public void setChaosCanvas(ChaosCanvas chaosCanvas) {
    this.chaosCanvas = chaosCanvas;
  }

}