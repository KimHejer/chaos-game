package no.ntnu.idatg2003.chaos.game.gr8.gui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*; // Using *, since we are importing 10+ classes from the package
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.chaos.game.gr8.utility.Toast;

/**
 * The {@code GameView} class provides the graphical user interface for the chaos game application.
 * <p>
 * This class is responsible for initializing and managing the UI components, such as buttons,
 * sliders, canvas, and menus.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * GameView gameView = new GameView();
 * Parent root = gameView.getRoot();
 * }
 * </pre>
 *
 * @see Toast
 * @since 1.0.0
 */
public class GameView {

  // Constants
  private static final int SLIDER_MIN = 0;
  private static final int SLIDER_MAX = 300000;
  private static final int SLIDER_INITIAL = 0;
  private static final int SLIDER_MAJOR_TICK_UNIT = 25000;
  private static final int SLIDER_BLOCK_INCREMENT = 1000;
  private static final double CX_VALUE_MIN = -1.5;
  private static final double CX_VALUE_MAX = 0.47;
  private static final double CY_VALUE_MIN = -1.12;
  private static final double CY_VALUE_MAX = 1.12;
  private static final double C_VALUE_MAJOR_TICK_UNIT = 0.1;
  private static final double C_VALUE_BLOCK_INCREMENT = 0.01;
  private static final int AFFINE_TRANSFORMATION = 1;
  private static final int JULIA_TRANSFORMATION = 2;
  private static final String DARK_MODE = "/css/DarkMode.css";
  private static final String LIGHT_MODE = "/css/LightMode.css";

  // UI Components
  private final BorderPane root;
  private final Canvas canvas;
  private final StackPane stackPane;
  private final GridPane controlPane;
  private final HBox toggleButtonContainer;

  private final Slider stepSlider;
  private final Slider cxValueSlider;
  private final Slider cyValueSlider;

  private final ChoiceBox<String> colorBox;

  private final Button sierpinskiButton;
  private final Button barnsleyButton;
  private final Button newAffineButton;
  private final Button juliaButton;
  private final Button randomButton;
  private final Button changeCordButton;

  private final Label controlLabel;
  private final Label stepLabel;
  private final Label colorLabel;
  private final Label cxValueLabel;
  private final Label cyValueLabel;
  private final Label maxCordLabel;
  private final Label minCordLabel;
  private final Label maxCordValueLabel;
  private final Label minCordValueLabel;

  private final ToggleButton toggleButton;

  private final MenuBar menuBar;
  private final MenuItem newTransformation;
  private final MenuItem loadMenuItem;
  private final MenuItem exitMenuItem;
  private final MenuItem darkModeMenuItem;
  private final MenuItem lightModeMenuItem;
  private final MenuItem saveMenuItem;
  private final MenuItem saveAsMenuItem;


  // State variables
  private int transformationType;
  private Color fractalColor;
  private Color defaultFractionColor;
  private int colCount;
  private int rowCount;

  /**
   * Constructs a new {@code GameView} and initializes the user interface.
   */
  public GameView() {
    this.root = new BorderPane();
    this.transformationType = 0;
    this.stepSlider = new Slider(SLIDER_MIN, SLIDER_MAX, SLIDER_INITIAL);
    this.cxValueSlider = new Slider(CX_VALUE_MIN, CX_VALUE_MAX, SLIDER_INITIAL);
    this.cyValueSlider = new Slider(CY_VALUE_MIN, CY_VALUE_MAX, SLIDER_INITIAL);
    this.colorBox = new ChoiceBox<>();
    this.controlPane = new GridPane();
    this.canvas = new Canvas();
    this.sierpinskiButton = new Button("Sierpinski");
    this.barnsleyButton = new Button("Barnsley");
    this.newAffineButton = new Button("New affine");
    this.juliaButton = new Button("Julia");
    this.randomButton = new Button("Random");
    this.changeCordButton = new Button("Change coordinates");
    this.controlLabel = new Label("Controls");
    this.stepLabel = new Label("Steps");
    this.colorLabel = new Label("Color");
    this.cxValueLabel = new Label("real value");
    this.cyValueLabel = new Label("imag value");
    this.maxCordLabel = new Label("Max coordinates:");
    this.minCordLabel = new Label("Min coordinates:");
    this.maxCordValueLabel = new Label();
    this.minCordValueLabel = new Label();
    this.menuBar = new MenuBar();
    this.newTransformation = new MenuItem("New transformation...");
    this.loadMenuItem = new MenuItem("Load new fractal...");
    this.saveMenuItem = new MenuItem("Save fractal");
    this.saveAsMenuItem = new MenuItem("Save fractal as...");
    this.darkModeMenuItem = new MenuItem("Dark mode");
    this.lightModeMenuItem = new MenuItem("Light mode");
    this.exitMenuItem = new MenuItem("Exit");
    this.toggleButton = new ToggleButton();
    this.toggleButtonContainer = new HBox();
    this.stackPane = new StackPane(canvas, controlPane);
    this.colCount = 0;
    this.rowCount = 0;
    initUi();
  }

  private void initUi() {
    initializeControlPane();
    initializeStackPane();
    createMenuBar();
    placeContent();
    switchToDarkMode();
    setStyle();
    root.setMinSize(0, 0); //makes resizing possible
  }

  // Initializes the control pane with buttons, slider, and other UI components.
  private void initializeControlPane() {
    initializeControlButtons();

    controlPane.setAlignment(Pos.CENTER_LEFT);
    controlPane.setPadding(new Insets(10));
    controlPane.setMaxWidth(0);

    toggleButtonContainer.setAlignment(Pos.CENTER_RIGHT);
    toggleButtonContainer.getChildren().add(toggleButton);
    controlPane.add(toggleButtonContainer, 1, 2);
  }

  // Initializes the control buttons and slider, and sets up their actions.
  private void initializeControlButtons() {
    toggleButton.setOnAction(event -> {
      if (transformationType != 0 && controlPane.getMaxWidth() == 0) {
        controlPane.setMaxWidth(175);
        controlPane.setVgap(5);
        addSharedControls();
        if (transformationType == AFFINE_TRANSFORMATION) {
          addAffineControls();
        } else if (transformationType == JULIA_TRANSFORMATION) {
          addJuliaControls();
        }
      } else {
        removeControls();
      }
    });

    configureSlider(stepSlider, SLIDER_MAJOR_TICK_UNIT, SLIDER_BLOCK_INCREMENT);
    configureSlider(cxValueSlider, C_VALUE_MAJOR_TICK_UNIT, C_VALUE_BLOCK_INCREMENT);
    configureSlider(cyValueSlider, C_VALUE_MAJOR_TICK_UNIT, C_VALUE_BLOCK_INCREMENT);

    colorBox.getItems().addAll("Black", "Blue", "Red",
        "Green", "Yellow", "Purple",
        "Orange", "Pink", "Cyan",
        "Magenta", "Brown", "Gray",
        "White");
  }

  // Configures the slider with the given major tick unit and block increment.
  private void configureSlider(Slider slider, double majorTickUnit, double blockIncrement) {
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(majorTickUnit);
    slider.setBlockIncrement(blockIncrement);
  }

  // Creates the menu bar with file and settings menus.
  private void createMenuBar() {
    Menu fileMenu = new Menu("File");
    Menu settingsMenu = new Menu("Settings");
    fileMenu.getItems().addAll(newTransformation,
        loadMenuItem,
        saveMenuItem,
        saveAsMenuItem,
        exitMenuItem);
    settingsMenu.getItems().addAll(darkModeMenuItem, lightModeMenuItem);
    menuBar.getMenus().addAll(fileMenu, settingsMenu);
  }

  // Adds shared controls (label, slider, and buttons) to the control pane.
  private void addSharedControls() {
    controlPane.add(controlLabel, colCount, rowCount++);
    rowCount++;
    controlPane.add(stepLabel, colCount, rowCount++);
    controlPane.add(stepSlider, colCount, rowCount++);
    controlPane.add(minCordLabel, colCount, rowCount++);
    controlPane.add(minCordValueLabel, colCount, rowCount++);
    controlPane.add(maxCordLabel, colCount, rowCount++);
    controlPane.add(maxCordValueLabel, colCount, rowCount++);
    controlPane.add(changeCordButton, colCount, rowCount++);
    controlPane.add(colorLabel, colCount, rowCount++);
    controlPane.add(colorBox, colCount, rowCount++);
  }


  // Removes all controls from the control pane.
  private void removeControls() {
    controlPane.getChildren().clear();
    controlPane.setMaxWidth(0);
    controlPane.add(toggleButtonContainer, 1, 2);
    rowCount = 0;
    colCount = 0;
  }

  // Adds controls for affine transformation to the control pane.
  private void addAffineControls() {
    controlPane.add(sierpinskiButton, colCount, rowCount++);
    controlPane.add(barnsleyButton, colCount, rowCount++);
    controlPane.add(newAffineButton, colCount, rowCount++);
    controlPane.getChildren().remove(toggleButtonContainer);
    controlPane.add(toggleButtonContainer, 1, rowCount / 2);
  }

  // Adds controls for Julia transformation to the control pane.
  private void addJuliaControls() {
    controlPane.add(cxValueLabel, colCount, rowCount++);
    controlPane.add(cxValueSlider, colCount, rowCount++);
    controlPane.add(cyValueLabel, colCount, rowCount++);
    controlPane.add(cyValueSlider, colCount, rowCount++);
    controlPane.add(juliaButton, colCount, rowCount++);
    controlPane.add(randomButton, colCount, rowCount++);
    controlPane.getChildren().remove(toggleButtonContainer);
    controlPane.add(toggleButtonContainer, 1, rowCount / 2);
  }


  /**
   * Updates the size of the canvas.
   *
   * @param width  the new width of the canvas
   * @param height the new height of the canvas
   */
  public void updateCanvasSize(double width, double height) {
    canvas.setWidth(width);
    canvas.setHeight(height);
  }


  // Initializes the stack pane with the control pane.
  private void initializeStackPane() {
    StackPane.setAlignment(controlPane, Pos.CENTER_LEFT);
  }

  // Places the menu bar, canvas, and stack pane in the root pane.
  private void placeContent() {
    root.setTop(menuBar);
    root.setCenter(canvas);
    root.setLeft(stackPane);
  }

  /**
   * Draws the matrix on the canvas. Each cell in the matrix is represented by a rectangle on the
   * canvas.
   *
   * @param matrix the {@code int[][]} matrix to be drawn
   */
  public void drawMatrix(int[][] matrix) {
    clearCanvas();

    int rows = matrix.length;
    int cols = matrix[0].length;

    GraphicsContext gc = canvas.getGraphicsContext2D();
    double cellWidth = gc.getCanvas().getWidth() / cols;
    double cellHeight = gc.getCanvas().getHeight() / rows;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        double x = j * cellWidth;
        double y = i * cellHeight;
        if (matrix[i][j] == 1) {
          gc.setFill(fractalColor);
          gc.fillRect(x, y, cellWidth, cellHeight);
        }
      }
    }
  }

  /**
   * Clears the canvas.
   */
  public void clearCanvas() {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  /**
   * Displays an error toast message.
   *
   * @param message the message to be displayed in the toast
   */
  public void showToast(String message) {
    Toast.showToast(message, root.getScene(),root.getStylesheets());
  }

  /**
   * Gets the button for Sierpinski fractal.
   *
   * @return the button for Sierpinski fractal
   */
  public Button getSierpinskiButton() {
    return sierpinskiButton;
  }

  /**
   * Gets the button for Barnsley fractal.
   *
   * @return the button for Barnsley fractal
   */
  public Button getBarnsleyButton() {
    return barnsleyButton;
  }

  /**
   * Gets the button for new affine transformation.
   *
   * @return the button for new affine transformation
   */
  public Button getNewAffineButton() {
    return newAffineButton;
  }

  /**
   * Gets the button for Julia fractal.
   *
   * @return the button for Julia fractal
   */
  public Button getJuliaButton() {
    return juliaButton;
  }

  /**
   * Gets the button for random fractal.
   *
   * @return the button for random fractal
   */
  public Button getRandomButton() {
    return randomButton;
  }

  /**
   * Gets the slider for adjusting steps.
   *
   * @return the slider for adjusting steps
   */
  public Slider getStepSlider() {
    return stepSlider;
  }

  /**
   * Gets the slider for adjusting the real value of C.
   *
   * @return the slider for adjusting the real value of C
   */
  public Slider getCxValueSlider() {
    return cxValueSlider;
  }

  /**
   * Gets the slider for adjusting the imaginary value of C.
   *
   * @return the slider for adjusting the imaginary value of C
   */
  public Slider getCyValueSlider() {
    return cyValueSlider;
  }

  /**
   * Gets the menu item for new transformation.
   *
   * @return the menu item for new transformation
   */
  public MenuItem getNewTransformation() {
    return newTransformation;
  }

  /**
   * Gets the load menu item.
   *
   * @return the load menu item.
   */
  public MenuItem getLoadMenuItem() {
    return loadMenuItem;
  }

  /**
   * Gets the save menu item.
   *
   * @return the save menu item
   */
  public MenuItem getSaveMenuItem() {
    return saveMenuItem;
  }

  /**
   * Gets the save as menu item.
   *
   * @return the save as menu item
   */
  public MenuItem getSaveAsMenuItems() {
    return saveAsMenuItem;
  }

  /**
   * Gets the dark mode menu item.
   *
   * @return the dark mode menu item
   */
  public MenuItem getDarkModeMenuItem() {
    return darkModeMenuItem;
  }

  /**
   * Gets the light mode menu item.
   *
   * @return the light mode menu item
   */
  public MenuItem getLightModeMenuItem() {
    return lightModeMenuItem;
  }

  /**
   * Gets the button for changing coordinates.
   *
   * @return the button for changing coordinates
   */
  public Button getChangeCordButton() {
    return changeCordButton;
  }

  /**
   * Gets the label for the minimum coordinates.
   *
   * @return the label for the minimum coordinates
   */
  public Label getMinCordValueLabel() {
    return minCordValueLabel;
  }

  /**
   * Gets the label for the maximum coordinates.
   *
   * @return the label for the maximum coordinates
   */
  public Label getMaxCordValueLabel() {
    return maxCordValueLabel;
  }

  /**
   * Gets the exit menu item.
   *
   * @return the exit menu item
   */
  public MenuItem getExitMenuItem() {
    return exitMenuItem;
  }

  /**
   * Gets the root pane of the view.
   *
   * @return the root pane
   */
  public BorderPane getRoot() {
    return root;
  }

  /**
   * Changes the view to affine transformation.
   */
  public void switchToAffine() {
    this.transformationType = AFFINE_TRANSFORMATION;
    removeControls();
    toggleButton.fire();
  }

  /**
   * Changes the view to Julia transformation.
   */
  public void switchToJulia() {
    this.transformationType = JULIA_TRANSFORMATION;
    removeControls();
    toggleButton.fire();
  }

  /**
   * Sets the dark mode for the view.
   */
  public void switchToDarkMode() {
    root.getStylesheets().clear();
    root.getStylesheets().add(DARK_MODE);
    fractalColor = Color.BLUE;
    defaultFractionColor = Color.BLUE;
  }

  /**
   * Sets the light mode for the view.
   */
  public void switchToLightMode() {
    root.getStylesheets().clear();
    root.getStylesheets().add(LIGHT_MODE);
    fractalColor = Color.BLACK;
    defaultFractionColor = Color.BLACK;
  }

  // Sets the style for the UI components.
  private void setStyle() {
    String subLabel = "sub-label";
    toggleButton.getStyleClass().add("open-close-toggle-button");
    controlPane.getStyleClass().add("control-pane");
    controlLabel.getStyleClass().add("control-label");
    stepLabel.getStyleClass().add(subLabel);
    colorLabel.getStyleClass().add(subLabel);
    cxValueLabel.getStyleClass().add(subLabel);
    cyValueLabel.getStyleClass().add(subLabel);
    maxCordLabel.getStyleClass().add(subLabel);
    minCordLabel.getStyleClass().add(subLabel);
    maxCordValueLabel.getStyleClass().add(subLabel);
    minCordValueLabel.getStyleClass().add(subLabel);

  }

  /**
   * Switches the fractal color based on the selected color.
   *
   * @param color the {@code String} name of the selected color
   */
  public void switchFractalColor(String color) {
    switch (color) {
      case "Black" -> fractalColor = Color.BLACK;
      case "Blue" -> fractalColor = Color.BLUE;
      case "Red" -> fractalColor = Color.RED;
      case "Green" -> fractalColor = Color.GREEN;
      case "Yellow" -> fractalColor = Color.YELLOW;
      case "Purple" -> fractalColor = Color.PURPLE;
      case "Orange" -> fractalColor = Color.ORANGE;
      case "Pink" -> fractalColor = Color.PINK;
      case "Cyan" -> fractalColor = Color.CYAN;
      case "Magenta" -> fractalColor = Color.MAGENTA;
      case "Brown" -> fractalColor = Color.BROWN;
      case "Gray" -> fractalColor = Color.GRAY;
      case "White" -> fractalColor = Color.WHITE;
      default -> fractalColor = defaultFractionColor;
    }
  }

  /**
   * Gets the {@code ChoiceBox} for choosing between fractal colors.
   *
   * @return the {@code ChoiceBox} for choosing between fractal colors
   */
  public ChoiceBox<String> getColorBox() {
    return colorBox;
  }
}
