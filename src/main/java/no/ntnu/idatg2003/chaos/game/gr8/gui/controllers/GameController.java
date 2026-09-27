package no.ntnu.idatg2003.chaos.game.gr8.gui.controllers;

import java.io.IOException;
import java.util.Properties;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.chaos.game.gr8.gui.models.GameModel;
import no.ntnu.idatg2003.chaos.game.gr8.gui.view.GameView;
import no.ntnu.idatg2003.chaos.game.gr8.utility.GlobalVariables;
import no.ntnu.idatg2003.chaos.game.gr8.utility.PopupFactory;
import no.ntnu.idatg2003.chaos.game.gr8.utility.Validator;

/**
 * The {@code GameController} class acts as the controller in the MVC architecture
 * for the chaos game application.
 * <p>
 * This class handles user interactions, updates the model, and refreshes the view
 * accordingly.
 * </p>
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * GameController gameController = new GameController();
 * Parent root = gameController.getView();
 * }
 * </pre>
 *
 * @author Kim Hejer and Audun Wetter
 * @version 1.0.0
 * @see GameModel
 * @see GameView
 * @since 1.0.0
 */
public class GameController {
  private static final int CANVAS_PADDING = 150;
  private final GameModel gameModel;
  private final GameView gameView;
  private Stage currentPopupStage;



  /**
   * Constructs a new {@code GameController} and initializes the model and view.
   *
   * @throws IOException if an I/O error occurs during initialization
   */
  public GameController() throws IOException {
    gameModel = new GameModel();
    gameView = new GameView();
    initializeViewFromModel();
    setUpHandlers();
    setupDynamicResizing();
  }

  /**
   * Sets up the event handlers for the view components.
   */
  private void setUpHandlers() {
    gameView.getSierpinskiButton().setOnAction(event -> handleFractalLoad(GlobalVariables.SIERPINSKI_FILE));
    gameView.getBarnsleyButton().setOnAction(event -> handleFractalLoad(GlobalVariables.BARNSLEY_FILE));
    gameView.getJuliaButton().setOnAction(event -> handleFractalLoad(GlobalVariables.JULIA_FILE));
    gameView.getRandomButton().setOnAction(event -> handleFractalLoad(GlobalVariables.RANDOM_STRING));
    gameView.getNewAffineButton().setOnAction(event -> showPopup("affine"));
    gameView.getStepSlider().valueProperty()
        .addListener((observable, oldValue, newValue) -> updateCanvas(newValue.intValue()));
    gameView.getCxValueSlider().valueProperty()
        .addListener((observable, oldValue, newValue) -> updateCx(newValue.doubleValue()));
    gameView.getCyValueSlider().valueProperty()
        .addListener((observable, oldValue, newValue) -> updateCy(newValue.doubleValue()));

    gameView.getColorBox().setOnAction(event -> updateColor());

    gameView.getDarkModeMenuItem().setOnAction(event -> gameView.switchToDarkMode());
    gameView.getLightModeMenuItem().setOnAction(event -> gameView.switchToLightMode());
    gameView.getExitMenuItem().setOnAction(event -> saveConfigAndExit());

    gameView.getNewTransformation().setOnAction(event -> showPopup("transformation"));
    gameView.getLoadMenuItem().setOnAction(event -> showPopup("customFractal"));
    gameView.getSaveAsMenuItems().setOnAction(event -> showPopup("save"));
    gameView.getSaveMenuItem().setOnAction(event -> saveFractal());
    gameView.getChangeCordButton().setOnAction(event -> showPopup("coordinates"));
  }

  /**
   * Saves the current fractal. If no file name is set or if it's a built-in fractal,
   * it shows a save popup or an error toast respectively.
   */
  private void saveFractal() {
    String currentFile = gameModel.getCurrentFile();
    try {
      Validator.validateSaveFilename(currentFile);
    } catch (IllegalArgumentException e) {
      showPopup("save"); // sends user to saveAs popup
      return;
    }
      handleSaveFractal(currentFile);
  }

  /**
   * Saves the configuration and exits the application.
   */
  private void saveConfigAndExit() {
    gameModel.saveConfig();
    System.exit(0);
  }

  /**
   * Updates the canvas based on the new value from the step slider.
   *
   * @param newValue the new value from the step slider
   */
  private void updateCanvas(int newValue) {
    gameModel.setSteps(newValue);
    refreshView();
  }

  /**
   * Handles loading a new fractal into the model and updating the view.
   *
   * @param fractal the name of the fractal to load
   */
  private void handleFractalLoad(String fractal) {
    try {
      gameView.clearCanvas();
      gameModel.setNewFractal(fractal);
      refreshView();
    } catch (IOException e) {
      showErrorToast("Failed to load fractal: " + fractal);
    }
  }

  /**
   * Sets up dynamic resizing of the canvas when the scene size changes.
   */
  private void setupDynamicResizing() {
    gameView.getRoot().sceneProperty().addListener((obs, oldScene, newScene) -> {
      if (newScene != null) {
        newScene.widthProperty().addListener((obs2, oldVal, newVal) -> updateCanvasSize(newScene));
        newScene.heightProperty().addListener((obs3, oldVal, newVal) -> updateCanvasSize(newScene));
      }
    });
  }

  /**
   * Updates the size of the canvas based on the new scene dimensions.
   *
   * @param newScene the new scene to update the canvas size based on.
   */
  private void updateCanvasSize(Scene newScene) {
    double newWidth = newScene.getWidth() - CANVAS_PADDING;
    double newHeight = newScene.getHeight() - CANVAS_PADDING;
    gameModel.changeApplicationSize(newScene.getWidth(), newScene.getHeight());
    gameView.updateCanvasSize(newWidth, newHeight);
    refreshView();
  }

  /**
   * Updates the real part of the complex number used in the Julia set transformation.
   *
   * @param newValue the new real value of the complex vector.
   */
  private void updateCx(double newValue) {
    gameModel.changeCx(newValue);
    refreshView();
  }

  /**
   * Updates the imaginary part of the complex number used in the Julia set transformation.
   *
   * @param newValue the new imaginary value of the complex vector.
   */
  private void updateCy(double newValue) {
    gameModel.changeCy(newValue);
    refreshView();
  }

  /**
   * Shows the appropriate popup based on the provided type. If a popup is already open,
   * it closes the current popup before showing the new one. The popup types are:
   * <ul>
   *   <li>transformation</li>
   *   <li>affine</li>
   *   <li>customFractal</li>
   *   <li>save</li>
   *   <li>coordinates</li>
   * </ul>
   *
   * @param type the type of popup to show.
   */
  private void showPopup(String type) {
    closePopup();
    switch (type) {
      case "transformation":
        currentPopupStage = PopupFactory.createTransformationPopup(this);
        break;
      case "affine":
        currentPopupStage = PopupFactory.createCustomAffinePopup(this);
        break;
      case "customFractal":
        currentPopupStage = PopupFactory.createLoadFractalPopup(this);
        break;
      case "save":
        currentPopupStage = PopupFactory.createSavePopup(this);
        break;
      case "coordinates":
        currentPopupStage = PopupFactory.createChangeCordPopup(this);
        break;
      default:
        throw new IllegalArgumentException("Unknown popup type: " + type);
    }
    currentPopupStage.show();
  }


  /**
   * Handles the affine transformation option, loading the Sierpinski fractal.
   */
  public void handleAffineTransformation() {
    gameView.switchToAffine();
    try {
      gameModel.setNewFractal(GlobalVariables.SIERPINSKI_FILE);
      refreshView();
    } catch (IOException e) {
      showErrorToast("Failed to load fractal: Sierpinski");
    }
    gameView.clearCanvas();
  }


  /**
   * Handles the Julia transformation option, loading a random Julia fractal.
   */
  public void handleJuliaTransformation() {
    gameView.switchToJulia();
    try {
      gameModel.setNewFractal(GlobalVariables.JULIA_FILE);
    } catch (IOException e) {
      showErrorToast("Failed to load fractal: Random");
    }
    refreshView();
  }


  /**
   * Handles the submission of a custom fractal file.
   *
   * @param fileName the name of the fractal file to load
   */
  public void handleCustomFractalSubmit(String fileName) {
    try {
      gameModel.loadFractal(fileName);
      getFractalContext();
      refreshView();
    } catch (IOException e) {
      showErrorToast("Failed to load fractal: " + fileName);
    }
  }

  /**
   * Closes the current popup if any is open.
   */
  private void closePopup() {
    if (currentPopupStage != null) {
      currentPopupStage.close();
    }
  }

  /**
   * Updates the fractal color based on the selected color in the view.
   */
  private void updateColor() {
    gameModel.changeColor(gameView.getColorBox().getValue());
    gameView.switchFractalColor(gameView.getColorBox().getValue());
    refreshView();
  }

  /**
   * Initializes the view from the model's configuration.
   */
  private void initializeViewFromModel() {
    try {
      Properties config = gameModel.loadConfig();
      double canvasWidth = Double.parseDouble(config.getProperty("canvasWidth", "1000.0"));
      double canvasHeight = Double.parseDouble(config.getProperty("canvasHeight", "1000.0"));
      gameView.updateCanvasSize(canvasWidth, canvasHeight);
      gameView.switchFractalColor(config.getProperty("color", "Gray"));
      gameView.getMinCordValueLabel().setText(gameModel.getMinCordStringValue());
      gameView.getMaxCordValueLabel().setText(gameModel.getMaxCordStringValue());
      getFractalContext();
      refreshView();
    } catch (IOException e) {
      showErrorToast("Failed to load configuration: " + e.getMessage());
    }
  }

  /**
   * Displays an error toast message.
   *
   * @param message the message to be displayed in the toast
   */
  public void showErrorToast(String message) {
    gameView.showToast(message);
  }

  /**
   * Returns the root view of the game.
   *
   * @return the root view
   */
  public Parent getView() {
    return gameView.getRoot();
  }

  /**
   * Handles the saving of a fractal to a specified file.
   *
   * @param fileName the name of the file to save the fractal to
   */
  public void handleSaveFractal(String fileName) {
    try {
      gameModel.saveFractal(fileName);
    } catch (IOException e) {
      showErrorToast("Failed to save fractal: " + fileName);
    }
  }

  /**
   * Refreshes the view by running the steps in the model and updating the canvas.
   */
  private void refreshView() {
    gameModel.runSteps();
    gameView.drawMatrix(gameModel.getMatrix());
    gameView.getMaxCordValueLabel().setText(gameModel.getMaxCordStringValue());
    gameView.getMinCordValueLabel().setText(gameModel.getMinCordStringValue());
    gameView.getStepSlider().setValue(gameModel.getSteps());
    gameView.getCyValueSlider().setValue(gameModel.getCy());
    gameView.getCxValueSlider().setValue(gameModel.getCx());
  }

  /**
   * Sets the appropriate context (Affine or Julia) for the fractal being displayed.
   */
  private void getFractalContext() {
    if (gameModel.isJulia()) {
      gameView.switchToJulia();
    } else {
      gameView.switchToAffine();
    }
  }

  /**
   * Handles the submission of a custom affine transformation based on the provided values
   * from the user.
   *
   * @param a00 the value of the first row, first column of the affine matrix
   * @param a01 the value of the first row, second column of the affine matrix
   * @param a10 the value of the second row, first column of the affine matrix
   * @param a11 the value of the second row, second column of the affine matrix
   * @param x0  the value of the first column of the translation vector
   * @param x1  the value of the second column of the translation vector
   */
  public void handleCustomAffineTransformation(double a00, double a01, double a10, double a11,
                                               double x0, double x1) {
    gameModel.addAffineTransformation(a00, a01, a10, a11, x0, x1);
    refreshView();
  }

  /**
   * Clears all affine transformations from the model.
   */
  public void clearTransformations() {
    gameModel.clearTransformations();
  }

  /**
   * Updates the min and max coordinates of the canvas based on the new values from the user.
   *
   * @param minX0 the new minimum x coordinate
   * @param minX1 the new minimum y coordinate
   * @param maxX0 the new maximum x coordinate
   * @param maxX1 the new maximum y coordinate
   */
  public void updateMinMaxCords(double minX0, double minX1, double maxX0, double maxX1) {
    gameModel.updateMinMaxCords(minX0, minX1, maxX0, maxX1);
    gameView.getMinCordValueLabel().setText(gameModel.getMinCordStringValue());
    gameView.getMaxCordValueLabel().setText(gameModel.getMaxCordStringValue());
    refreshView();
  }
}