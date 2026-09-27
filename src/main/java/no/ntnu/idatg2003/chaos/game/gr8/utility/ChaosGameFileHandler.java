package no.ntnu.idatg2003.chaos.game.gr8.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import no.ntnu.idatg2003.chaos.game.gr8.chaosgame.ChaosGameDescription;
import no.ntnu.idatg2003.chaos.game.gr8.math.Matrix2x2;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.AffineTransform2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.JuliaTransform;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.Transform2D;

/**
 * Handles reading from and writing to files for ChaosGameDescription objects.
 * This class encapsulates the functionality required to serialize and deserialize
 * ChaosGameDescription instances, allowing for persistent storage and retrieval of
 * fractal configurations.
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * ChaosGameDescription description = ChaosGameFileHandler.readFromFile("sierpinski.txt");
 * ChaosGameFileHandler.writeToFile(description, "output.txt");
 * }
 * </pre>
 *
 *
 * @author Audun Wetter
 * @version 1.0.0
 * @see ChaosGameDescription
 * @since 1.0.0
 */
public class ChaosGameFileHandler {
  private static final String REGEX_PATTERN = ",\\s*";
  private static final String REGEX_PATTERN2 = "#";
  private static final String DEFAULT_FILE_PATH = "src/main/resources/files/";

  private ChaosGameFileHandler() {
    // Private constructor to prevent instantiation
  }

  /**
   * Writes the configuration of a ChaosGameDescription to a specified file. This method captures
   * all essential details of the game's fractal configuration, including types of transformations,
   * bounding coordinates, and individual transformation parameters.
   *
   * @param description The configuration of the fractal game to write to the file.
   * @param fileName    The file path where the configuration is saved. If the file exists, it's
   *                    overwritten; otherwise, a new file is created.
   * @throws IOException If there's an issue accessing or writing to the file.
   */

  public static void writeToFile(ChaosGameDescription description, String fileName)
      throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEFAULT_FILE_PATH
        + fileName))) {
      // Write the transformation type (assuming all transformations are of the same type).
      if (description.getTransforms().getFirst() instanceof AffineTransform2D) {
        writer.write("Affine2D     # Type of transformation");
      } else if (description.getTransforms().getFirst() instanceof JuliaTransform) {
        writer.write("Julia2D      # Type of transformation");
      }
      writer.newLine();

      // Write minCoords and maxCoords
      Vector2D minCoords = description.getMinCoords();
      Vector2D maxCoords = description.getMaxCoords();
      writer.write(minCoords.getX0() + ", " + minCoords.getX1() + "    # Lower Left");
      writer.newLine();
      writer.write(maxCoords.getX0() + ", " + maxCoords.getX1() + "    # Upper Right");
      writer.newLine();

      // Sequentially write each transformation with a comment for identification.
      int i = 1;
      Map<Integer, String> transIterationMap = Map.of(
          1, "1st",
          2, "2nd",
          3, "3rd",
          4, "4th",
          5, "5th",
          6, "6th"
      );

      for (Transform2D transform : description.getTransforms()) {
        if (transform instanceof AffineTransform2D affineTransform) {
          writer.write(affineTransform.toFileString()
              + "     # "
              + transIterationMap.get(i)
              + " Affine transformation");
        } else if (transform instanceof JuliaTransform juliaTransform) {
          writer.write(juliaTransform.toFileString()
              + "    # Real and imaginary parts of the constant c");
        }
        i++;
        writer.newLine();
      }
    }
  }

  /**
   * Reads a {@code ChaosGameDescription} from a file. This method reads the configuration of a
   * fractal game from a file and reconstructs the {@code ChaosGameDescription} object.
   * The method assumes that the file is formatted correctly and contains all necessary
   * information to reconstruct the fractal game. The method reads the transformation type,
   * bounding coordinates, and individual transformation parameters.
   *
   * @param fileName The file path from which to read the fractal configuration.
   * @return A ChaosGameDescription object representing the fractal configuration.
   * @throws IOException If there's an issue accessing or reading from the file.
   */
  public static ChaosGameDescription readFromFile(String fileName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_FILE_PATH
        + fileName))) {
      // Skip the first line as it is just a comment about the type of transformation
      reader.readLine();

      // Read minCoords and maxCoords, assume they're always the second and third lines
      // Split to ignore comments
      String minCoordsLine = reader.readLine().split(REGEX_PATTERN2)[0].trim();
      String maxCoordsLine = reader.readLine().split(REGEX_PATTERN2)[0].trim();
      String[] minCoordsParts = minCoordsLine.split(REGEX_PATTERN);
      String[] maxCoordsParts = maxCoordsLine.split(REGEX_PATTERN);
      Vector2D minCoords = new Vector2D(Double.parseDouble(minCoordsParts[0]),
          Double.parseDouble(minCoordsParts[1]));
      Vector2D maxCoords = new Vector2D(Double.parseDouble(maxCoordsParts[0]),
          Double.parseDouble(maxCoordsParts[1]));

      List<Transform2D> transforms = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        // Consider text before the '#' as the transformation
        line = line.split(REGEX_PATTERN2)[0].trim();
        if (!line.isEmpty()) {
          String[] transformParts = line.split(REGEX_PATTERN);
          double[] params = new double[transformParts.length];
          for (int i = 0; i < transformParts.length; i++) {
            params[i] = Double.parseDouble(transformParts[i]);
          }

          if (transformParts.length == 6) {
            // Affine transformation (matrix and vector)
            AffineTransform2D transform = new AffineTransform2D(
                new Matrix2x2(params[0], params[1], params[2], params[3]),
                new Vector2D(params[4], params[5])
            );
            transforms.add(transform);
          } else if (transformParts.length == 3) {
            // Julia-transformation (complex point and sign)
            JuliaTransform transform = new JuliaTransform(
                new Vector2D(params[0], params[1]),
                (int) params[2]
            );
            transforms.add(transform);
          }
        }
      }
      return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }
  }

}
