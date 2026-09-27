package no.ntnu.idatg2003.chaos.game.gr8.utility;

/**
 * The {@code GlobalVariables} class holds global constant values used across the application.
 * <p>
 * This class is designed for scalability, allowing changes to variable values in one place
 * rather than throughout the codebase. This promotes low coupling and makes maintenance easier.
 * </p>
 * <p>
 * The class contains constants for file paths and commonly used filenames.
 * </p>
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * String sierpinskiFilename = GlobalVariables.SIERPINSKI_FILE;
 * String cssPath = GlobalVariables.CSS_PATH;
 * }
 * </pre>
 *
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class GlobalVariables {
  public static final String SIERPINSKI_FILE = "sierpinski.txt";
  public static final String BARNSLEY_FILE = "barnsley.txt";
  public static final String JULIA_FILE = "julia.txt";
  public static final String RANDOM_STRING = "random";
  public static final String FILE_PATH = "src/main/resources/files/";
  public static final String CSS_PATH = "src/main/resources/css/";



  private GlobalVariables() {
    //private constructor to prevent instantiation
  }
}
