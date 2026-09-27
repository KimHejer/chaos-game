package no.ntnu.idatg2003.chaos.game.gr8.utility;

import no.ntnu.idatg2003.chaos.game.gr8.chaosgame.ChaosGameDescription;
import no.ntnu.idatg2003.chaos.game.gr8.math.Complex;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.JuliaTransform;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.Transform2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * The {@code ChaosGameDescriptionFactory} is a factory class for creating ChaosGameDescriptions.
 * <p>
 * The class provides methods for creating {@code ChaosGameDescriptions} for the Sierpinski
 * triangle, the Barnsley fern, and the Julia set. The class also provides a method for
 * creating a {@code ChaosGameDescriptions} from any given file.
 * </p>
 *
 * <p>The class is a static factory class, meaning that it cannot be instantiated. The class
 * provides static factory methods for creating
 * {@code ChaosGameDescription} instances from files.</p>
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * ChaosGameDescription sierpinskiDescription =
 * ChaosGameDescriptionFactory.sierpinskiChaosGameDescription();
 * ChaosGameDescription barnsleyDescription =
 * ChaosGameDescriptionFactory.barnsleyFernChaosGameDescription();
 * ChaosGameDescription juliaDescription =
 * ChaosGameDescriptionFactory.juliaSetChaosGameDescription();
 * }
 * </pre>
 *
 *
 * @author Kim Hejer
 * @version 1.0.0
 * @see ChaosGameDescription
 * @see ChaosGameFileHandler
 * @since 1.0.0
 */
public class ChaosGameDescriptionFactory {

  // Private constructor to prevent instantiation
  private ChaosGameDescriptionFactory() {
  }

  /**
   * Creates a {@code ChaosGameDescription} for the Sierpinski triangle.
   *
   * @return A {@code ChaosGameDescription} for the Sierpinski triangle.
   * @see ChaosGameFileHandler
   */
  public static ChaosGameDescription sierpinskiChaosGameDescription() {
    try {
      return ChaosGameFileHandler.readFromFile("sierpinski.txt");
    } catch (IOException e) {
      ErrorLogger.getLogger().logError(Level.SEVERE, e.getMessage());
    }
    return null;
  }

  /**
   * Creates a {@code ChaosGameDescription} for the Barnsley fern.
   *
   * @return A {@code ChaosGameDescription} for the Barnsley fern.
   * @see ChaosGameFileHandler
   */
  public static ChaosGameDescription barnsleyFernChaosGameDescription() {
    try {
      return ChaosGameFileHandler.readFromFile("barnsley.txt");
    } catch (IOException e) {
      ErrorLogger.getLogger().logError(Level.SEVERE, e.getMessage());
    }
    return null;
  }

  /**
   * Creates a {@code ChaosGameDescription} for the Julia set.
   *
   * @return A {@code ChaosGameDescription} for the Julia set.
   * @see ChaosGameFileHandler
   */
  public static ChaosGameDescription juliaSetChaosGameDescription() {
    try {
      return ChaosGameFileHandler.readFromFile("julia.txt");
    } catch (IOException e) {
      ErrorLogger.getLogger().logError(Level.SEVERE, e.getMessage());
    }
    return null;
  }

  /**
   * Creates a {@code ChaosGameDescription} for the Julia set with a given complex number.
   *
   * @param c The complex number to use in the Julia set.
   * @return A {@code ChaosGameDescription} for the Julia set with the given complex number.
   */
  public static ChaosGameDescription juliaDescriptionGivenValues(Complex c) {
    JuliaTransform juliaTransform = new JuliaTransform(c, 1);
    JuliaTransform juliaTransform2 = new JuliaTransform(c, -1);
    ArrayList<Transform2D> transforms = new ArrayList<>();
    transforms.add(juliaTransform);
    transforms.add(juliaTransform2);
    return new ChaosGameDescription(new Vector2D(-1.6, -1), new Vector2D(1.6, 1), transforms);
  }
}
