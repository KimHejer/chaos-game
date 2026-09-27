package no.ntnu.idatg2003.chaos.game.gr8.gui.models;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import no.ntnu.idatg2003.chaos.game.gr8.chaosgame.ChaosGame;
import no.ntnu.idatg2003.chaos.game.gr8.chaosgame.ChaosGameDescription;
import no.ntnu.idatg2003.chaos.game.gr8.gui.observer.Observer;
import no.ntnu.idatg2003.chaos.game.gr8.math.Complex;
import no.ntnu.idatg2003.chaos.game.gr8.math.Matrix2x2;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.AffineTransform2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.JuliaTransform;
import no.ntnu.idatg2003.chaos.game.gr8.utility.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.chaos.game.gr8.utility.ChaosGameFileHandler;
import no.ntnu.idatg2003.chaos.game.gr8.utility.ConfigFileHandler;

/**
 * The {@code GameModel} class represents the model component in the
 * MVC architecture for the chaos game.
 * <p>
 * This class is responsible for handling the logic and data related to the chaos game,
 * including setting up fractals, running steps of the chaos game,
 * and providing the matrix for the view to draw.
 * </p>
 *
 * @see ChaosGame
 * @see ChaosGameDescriptionFactory
 * @since 1.0.0
 */
public class GameModel implements Observer {
  private static final int DEFAULT_CHAOSCANVAS_WIDTH = 1000;
  private static final int DEFAULT_CHAOSCANVAS_HEIGHT = 1000;

  private final ChaosGame chaosGame;
  private final ChaosGameDescription chaosGameDescription;
  private int steps;
  private int oldSteps;
  private double cx;
  private double cy;
  private String color;
  private int sceneWidth;
  private int sceneHeight;
  private String currentFile;

  /**
   * Constructs a new {@code GameModel} and initializes the chaos game and description factory.
   *
   * @throws IOException if an I/O error occurs during initialization
   * @throws AssertionError if the chaos game description is null
   */
  public GameModel() throws IOException, AssertionError {
    this.chaosGame = new ChaosGame(null, null);
    this.chaosGameDescription = ChaosGameDescriptionFactory.sierpinskiChaosGameDescription();
    // Instead of try catch, will send the exception to the caller instead
    assert this.chaosGameDescription != null;
    this.chaosGameDescription.addObserver(this);
    loadConfig();
  }

  /**
   * Sets a new fractal for the chaos game based on the provided fractal name.
   *
   * @param fractal the name of the fractal to set
   * @throws IOException              if an I/O error occurs while setting the new fractal
   * @throws IllegalArgumentException if the provided fractal name is invalid
   */
  public void setNewFractal(String fractal) throws IOException, IllegalArgumentException{
    switch (fractal) {
      case "sierpinski.txt" -> chaosGameDescription.changeChaosGameDescription(
          Objects.requireNonNull(ChaosGameDescriptionFactory.sierpinskiChaosGameDescription()));
      case "barnsley.txt" -> chaosGameDescription.changeChaosGameDescription(
          Objects.requireNonNull(ChaosGameDescriptionFactory.barnsleyFernChaosGameDescription()));
      case "julia.txt" -> chaosGameDescription.changeChaosGameDescription(
          Objects.requireNonNull(ChaosGameDescriptionFactory.juliaSetChaosGameDescription()));
      case "random" -> createRandomJulia();
      default -> throw new IllegalArgumentException("Invalid fractal");
    }
    if (fractal.equals("random")) {
      currentFile = "julia.txt";
      saveConfig();
    } else {
      currentFile = fractal;
      saveConfig();
    }
  }

  /**
   * Sets the number of steps for the chaos game and runs the steps.
   * <p>
   * If the new step count is less than the current step count,
   * the canvas is cleared before running the steps.
   * </p>
   *
   * @param steps the number of steps to set
   */
  public void setSteps(int steps) {
    this.oldSteps = this.steps;
    this.steps = steps;
    runSteps();
    saveConfig();
  }

  /**
   * Gets the current number of steps.
   *
   * @return the number of steps
   */
  public int getSteps() {
    return steps;
  }

  /**
   * Runs the chaos game steps based on the current number of steps. Stacking upwards
   * to reduce excessive calculations.
   */
  public void runSteps() {
    if (oldSteps >= steps) {
      chaosGame.getChaosCanvas().clear();
      chaosGame.runSteps(this.steps);
    } else {
      chaosGame.runSteps(this.steps - oldSteps);
    }
    saveConfig();
  }

  /**
   * Returns the matrix representation of the chaos game canvas.
   *
   * @return the matrix representation of the canvas
   */
  public int[][] getMatrix() {
    return chaosGame.getChaosCanvas().getCanvasArray();
  }

  /**
   * Changes the real part of the complex number used for Julia transformation.
   *
   * @param newCx the new real value
   */
  public void changeCx(double newCx) {
    this.cx = newCx;
    updateJuliaComplex();
  }

  /**
   * Changes the imaginary part of the complex number used for Julia transformation.
   *
   * @param newCy the new imaginary value
   */
  public void changeCy(double newCy) {
    this.cy = newCy;
    updateJuliaComplex();
  }

  /**
   * Gets the real part of the complex number used for Julia transformation.
   *
   * @return the real value
   */
  public double getCx() {
    return cx;
  }

  /**
   * Gets the imaginary part of the complex number used for Julia transformation.
   *
   * @return the imaginary value
   */
  public double getCy() {
    return cy;
  }

  /**
   * Changes the color of the fractal.
   *
   * @param newColor the new color
   */
  public void changeColor(String newColor) {
    this.color = newColor;
    saveConfig();
  }

  /**
   * Changes the size of the image.
   *
   * @param width  the new width
   * @param height the new height
   */
  public void changeApplicationSize(double width, double height) {
    this.sceneWidth = (int) width;
    this.sceneHeight = (int) height;
    saveConfig();
  }

  /**
   * Saves the current fractal to a file.
   *
   * @param fileName the name of the file to save the fractal to
   * @throws IOException if an I/O error occurs during saving
   */
  public void saveFractal(String fileName) throws IOException {
    ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
    currentFile = fileName;
    saveConfig();
  }

  /**
   * Loads a fractal from a file.
   *
   * @param fileName the name of the file to load the fractal from
   * @throws IOException if an I/O error occurs during loading
   */
  public void loadFractal(String fileName) throws IOException {
    chaosGameDescription.changeChaosGameDescription(ChaosGameFileHandler.readFromFile(fileName));
    currentFile = fileName;
    saveConfig();
  }

  /**
   * Checks if the current fractal is a Julia set.
   *
   * @return {@code true} if the fractal is a Julia set, {@code false} otherwise
   */
  public boolean isJulia() {
    return chaosGameDescription.getTransforms().getFirst() instanceof JuliaTransform;
  }

  /**
   * Updates the model when notified by the observable.
   */
  @Override
  public void update() {
    chaosGame.changeDescriptionAndCanvas(chaosGameDescription,
        DEFAULT_CHAOSCANVAS_WIDTH,
        DEFAULT_CHAOSCANVAS_HEIGHT);
  }

  /**
   * Loads the configuration from the configuration file.
   *
   * @return the loaded properties
   * @throws IOException if an I/O error occurs during loading
   */
  public Properties loadConfig() throws IOException {
    Properties config = ConfigFileHandler.loadConfig();
    steps = Integer.parseInt(config.getProperty("steps", "0"));
    cx = Double.parseDouble(config.getProperty("cx", "0.0"));
    cy = Double.parseDouble(config.getProperty("cy", "0.0"));
    color = config.getProperty("color", "Grey");
    sceneHeight = Integer.parseInt(config.getProperty("canvasHeight", "1000"));
    sceneWidth = Integer.parseInt(config.getProperty("canvasWidth", "1000"));
    String fractal = config.getProperty("fractal", "sierpinski.txt");
    loadFractal(fractal);
    if (isJulia()) {
      chaosGameDescription.changeChaosGameDescription(
          ChaosGameDescriptionFactory.juliaDescriptionGivenValues(new Complex(cx, cy)));
    }
    return config;
  }

  /**
   * Saves the current configuration to the configuration file.
   */
  public void saveConfig() {
    Properties config = new Properties();
    config.setProperty("fractal", currentFile);
    config.setProperty("steps", String.valueOf(steps));
    config.setProperty("cx", String.valueOf(cx));
    config.setProperty("cy", String.valueOf(cy));
    config.setProperty("color", color);
    config.setProperty("canvasWidth", String.valueOf(sceneWidth));
    config.setProperty("canvasHeight", String.valueOf(sceneHeight));
    ConfigFileHandler.saveConfig(config);
  }

  /**
   * Updates the Julia transformation complex number.
   */
  private void updateJuliaComplex() {
    chaosGameDescription.changeChaosGameDescription(
        ChaosGameDescriptionFactory.juliaDescriptionGivenValues(new Complex(cx, cy)));
    saveConfig();
  }

  /**
   * Creates a random Julia set transformation.
   */
  private void createRandomJulia() {
    cx = Math.random() * 1.97 - 1.5;
    cy = Math.random() * 2.24 - 1.12;
    chaosGameDescription.changeChaosGameDescription(
        ChaosGameDescriptionFactory.juliaDescriptionGivenValues(new Complex(cx, cy)));
    saveConfig();
  }

  /**
   * Gets the current file name.
   *
   * @return the current file name
   */
  public String getCurrentFile() {
    return currentFile;
  }

  /**
   * Adds an {@code AffineTransform2D} to the list of transformations in
   * {@code ChaosGameDescription} based on the provided parameters.
   *
   * @param a00 The value at row 0, column 0 in the transformation matrix
   * @param a01 The value at row 0, column 1 in the transformation matrix
   * @param a10 The value at row 1, column 0 in the transformation matrix
   * @param a11 The value at row 1, column 1 in the transformation matrix
   * @param x0  The value at row 0 in the translation vector
   * @param x1  The value at row 1 in the translation vector
   */
  public void addAffineTransformation(double a00, double a01, double a10, double a11,
                                      double x0, double x1) {
    AffineTransform2D transform = new AffineTransform2D(
        new Matrix2x2(a00, a01, a10, a11),
        new Vector2D(x0, x1));
    chaosGameDescription.addToTransforms(transform);
    saveConfig();
  }

  /**
   * Clears all transformations in the {@code ChaosGameDescription}.
   */
  public void clearTransformations() {
    chaosGameDescription.getTransforms().clear();
    saveConfig();
  }

  /**
   * Gives the {@code String} representation of the minimum coordinates.
   *
   * @return the minimum coordinates as a {@code String}.
   */
  public String getMinCordStringValue() {
    return chaosGameDescription.getMinCoords().toString();
  }

  /**
   * Gives the {@code String} representation of the maximum coordinates.
   *
   * @return the maximum coordinates as a {@code String}.
   */
  public String getMaxCordStringValue() {
    return chaosGameDescription.getMaxCoords().toString();
  }

  /**
   * Updates the minimum and maximum coordinates of the {@code ChaosGameDescription}.
   *
   * @param minX0 the new minimum x-coordinate
   * @param minX1 the new minimum y-coordinate
   * @param maxX0 the new maximum x-coordinate
   * @param maxX1 the new maximum y-coordinate
   */
  public void updateMinMaxCords(double minX0, double minX1, double maxX0, double maxX1) {
    chaosGameDescription.setMinCoords(new Vector2D(minX0, minX1));
    chaosGameDescription.setMaxCoords(new Vector2D(maxX0, maxX1));
  }
}
