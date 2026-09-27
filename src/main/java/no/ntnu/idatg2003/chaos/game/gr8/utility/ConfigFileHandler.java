package no.ntnu.idatg2003.chaos.game.gr8.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Utility class for handling configuration file operations. The configuration file is used to store
 * the user's preferences for the fractal, number of steps, and the center point of the fractal.
 * The configuration file is stored in the resources folder and is named config.properties.
 * This class contains methods for loading and saving the configuration properties.
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * Properties config = ConfigFileHandler.loadConfig();
 * config.setProperty("steps", "100");
 * ConfigFileHandler.saveConfig(config);
 * }
 * </pre>
 *
 *
 * @version 1.0.0
 * @author Audun Wetter
 */
public class ConfigFileHandler {

  private static final String CONFIG_FILE_PATH = "src/main/resources/files/config.properties";
  private static final String DEFAULT_FRACTAL = "sierpinski.txt";
  private static final int DEFAULT_STEPS = 0;
  private static final double DEFAULT_CX = 0.0;
  private static final double DEFAULT_CY = 0.0;

  private ConfigFileHandler() {
    // Prevent instantiation
  }

  /**
   * Loads configuration properties from the config file.
   *
   * @return Properties object containing the configuration.
   */
  public static Properties loadConfig() {
    Properties config = new Properties();
    File configFile = new File(CONFIG_FILE_PATH);

    if (configFile.exists()) {
      try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
        config.load(input);
      } catch (IOException e) {
        ErrorLogger.getLogger().logError(e.getMessage());
      }
    } else {
      createDefaultConfigFile(configFile);
    }

    return config;
  }

  /**
   * Creates a default configuration file if none exists.
   *
   * @param configFile The configuration file to create.
   */
  private static void createDefaultConfigFile(File configFile) {
    Properties defaultConfig = new Properties();
    defaultConfig.setProperty("fractal", DEFAULT_FRACTAL);
    defaultConfig.setProperty("steps", String.valueOf(DEFAULT_STEPS));
    defaultConfig.setProperty("cx", String.valueOf(DEFAULT_CX));
    defaultConfig.setProperty("cy", String.valueOf(DEFAULT_CY));
    defaultConfig.setProperty("color", "Grey");
    defaultConfig.setProperty("mode", "Dark");

    try (OutputStream output = new FileOutputStream(configFile)) {
      defaultConfig.store(output, null);
    } catch (IOException e) {
      ErrorLogger.getLogger().logError(e.getMessage());
    }
  }

  /**
   * Saves configuration properties to the config file.
   *
   * @param config The properties to save.
   */
  public static void saveConfig(Properties config) {
    try (OutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
      config.store(output, null);
    } catch (IOException e) {
      ErrorLogger.getLogger().logError(e.getMessage());
    }
  }
}
