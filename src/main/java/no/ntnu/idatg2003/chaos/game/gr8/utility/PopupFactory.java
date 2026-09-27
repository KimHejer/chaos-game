package no.ntnu.idatg2003.chaos.game.gr8.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatg2003.chaos.game.gr8.gui.controllers.GameController;

/**
 * Static Factory class for creating different types of popups.
 * This class should not be instantiated.
 *
 * <p>Example usage:
 * PopupFactory.createTransformationPopup(controller);
 * </p>
 *
 * @author Audun Wetter
 * @version 1.0.0
 * @since 1.0.0
 */
public class PopupFactory {

  private static final int SCENE_WIDTH = 600;
  private static final int SCENE_HEIGHT = 400;
  private static final int TEXT_FIELD_MAX_WIDTH = 100;
  private static final int TEXT_FIELD_PROMPT_WIDTH = 100;

  private PopupFactory() {
    // Prevent instantiation
  }

  /**
   * Creates a popup for transformation selection.
   *
   * @param controller The controller to handle actions.
   * @return The stage containing the popup.
   */
  public static Stage createTransformationPopup(GameController controller) {
    Stage stage = new Stage();
    stage.setTitle("New Transformation");
    stage.initModality(Modality.APPLICATION_MODAL);

    GridPane layout = new GridPaneBuilder()
        .addLabel("Choose transformation", 1, 0)
        .addButton("Affine Transformation", "affineButton", 0, 1)
        .addButton("Julia Transformation", "juliaButton", 1, 1)
        .getLayout();

    Scene scene = new Scene(layout);
    stage.setScene(scene);

    Button affineButton = (Button) layout.lookup("#affineButton");
    Button juliaButton = (Button) layout.lookup("#juliaButton");

    affineButton.setOnAction(event -> {
      controller.handleAffineTransformation();
      stage.close();
    });
    juliaButton.setOnAction(event -> {
      controller.handleJuliaTransformation();
      stage.close();
    });

    return stage;
  }

  /**
   * Creates a popup for adding multiple custom affine transformations.
   *
   * @param controller The controller to handle actions.
   * @return The stage containing the popup.
   */
  public static Stage createCustomAffinePopup(GameController controller) {
    Stage stage = new Stage();
    stage.setTitle("Custom Affine Transformations");
    stage.initModality(Modality.APPLICATION_MODAL);

    VBox mainLayout = new VBox(10);
    mainLayout.setPadding(new Insets(10));
    mainLayout.setAlignment(Pos.TOP_CENTER);

    Button addTransformationButton = new Button("Add Transformation");
    addTransformationButton.setAlignment(Pos.CENTER);

    VBox transformationsBox = new VBox(10);
    transformationsBox.setPadding(new Insets(10));
    transformationsBox.setAlignment(Pos.TOP_CENTER);

    ScrollPane scrollPane = new ScrollPane(transformationsBox);
    scrollPane.setFitToWidth(true);

    Button applyButton = new Button("Apply All");
    applyButton.setAlignment(Pos.CENTER);

    GridPane headerPane = new GridPaneBuilder()
        .addLabel("Enter affine transformations", 0, 0)
        .getLayout();

    mainLayout.getChildren().addAll(headerPane, addTransformationButton, scrollPane, applyButton);

    Scene scene = new Scene(mainLayout, SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);

    // List to keep track of transformation input fields
    List<HBox> transformationPanes = new ArrayList<>();

    addTransformationButton.setOnAction(event -> {
      HBox transformationPane = createTransformationPane(
          transformationPanes.size() + 1, transformationsBox, transformationPanes);
      transformationPanes.add(transformationPane);
      transformationsBox.getChildren().add(transformationPane);
    });

    applyButton.setOnAction(event -> {
      controller.clearTransformations();
      boolean allInputsValid = true;
      for (HBox transformationPane : transformationPanes) {
        try {
          double a = Validator.validateAndGetDouble(((TextField)
              transformationPane.lookup("#a00Field")).getText());
          double b = Validator.validateAndGetDouble(((TextField)
              transformationPane.lookup("#a01Field")).getText());
          double c = Validator.validateAndGetDouble(((TextField)
              transformationPane.lookup("#a10Field")).getText());
          double d = Validator.validateAndGetDouble(((TextField)
              transformationPane.lookup("#a11Field")).getText());
          double e = Validator.validateAndGetDouble(((TextField)
              transformationPane.lookup("#x0Field")).getText());
          double f = Validator.validateAndGetDouble(((TextField)
              transformationPane.lookup("#x1Field")).getText());
          controller.handleCustomAffineTransformation(a, b, c, d, e, f);
        } catch (IllegalArgumentException e) {
          allInputsValid = false;
          controller.showErrorToast(e.getMessage());
          break;
        }
      }
      if (allInputsValid) {
        stage.close();
      }
    });
    stage.show();
    return stage;
  }

  /**
   * Creates a popup for loading a custom fractal.
   *
   * @param controller The controller to handle actions.
   * @return The stage containing the popup.
   */
  public static Stage createLoadFractalPopup(GameController controller) {
    Stage stage = new Stage();
    stage.setTitle("Load Fractal");
    stage.initModality(Modality.APPLICATION_MODAL);

    ListView<String> fileListView = new ListView<>();
    TextField fileNameField = new TextField();
    loadFileNames(fileListView);
    fileNameField.setId("fileNameField");
    GridPane layout = new GridPaneBuilder()
        .addLabel("Load fractal from file", 0, 0)
        .addListView(fileListView, 1, 1)
        .addButton("Load", "loadButton", 1, 2)
        .getLayout();

    Scene scene = new Scene(layout);
    stage.setScene(scene);

    Button loadButton = (Button) layout.lookup("#loadButton");
    loadButton.setOnAction(event -> {
      String selectedFile = fileListView.getSelectionModel().getSelectedItem();
      if (selectedFile != null) {
        controller.handleCustomFractalSubmit(selectedFile);
        stage.close();
      } else {
        controller.showErrorToast("No file selected");
      }
    });

    return stage;
  }

  /**
   * Creates a popup for saving the current fractal.
   *
   * @param controller The controller to handle actions.
   * @return The stage containing the popup.
   */
  public static Stage createSavePopup(GameController controller) {
    Stage stage = new Stage();
    stage.setTitle("Save Fractal");
    stage.initModality(Modality.APPLICATION_MODAL);

    TextField fileNameField = createConfiguredTextField("fileNameField", "filename.txt");
    GridPane layout = new GridPaneBuilder()
        .addLabel("Save Fractal", 0, 0)
        .addLabel("Enter file name:", 0, 1)
        .addTextField(fileNameField, 1, 1)
        .addButton("Save", "saveButton", 1, 2)
        .getLayout();

    Scene scene = new Scene(layout);
    stage.setScene(scene);

    Button saveButton = (Button) layout.lookup("#saveButton");
    saveButton.setOnAction(event -> {
      try {
        Validator.validateSaveAsFilename(fileNameField.getText());
      } catch (IllegalArgumentException e) {
        controller.showErrorToast(e.getMessage());
        return;
      }
      String fileName = fileNameField.getText();
      controller.handleSaveFractal(fileName);
      stage.close();
    });

    return stage;
  }

  /**
   * Creates a horizontal box (HBox) for a single affine transformation input.
   *
   * @param transformationNumber the number of the transformation to display in the label.
   * @param transformationsBox   the VBox containing all transformation panes.
   * @param transformationPanes  the list of transformation panes.
   * @return an HBox containing text fields for affine transformation parameters and a delete
   *         button.
   */
  private static HBox createTransformationPane(int transformationNumber,
                                               VBox transformationsBox,
                                               List<HBox> transformationPanes) {
    HBox transformationPane = new HBox(10);
    transformationPane.setPadding(new Insets(10));
    transformationPane.setAlignment(Pos.CENTER_LEFT);

    Label transformationLabel = new Label("Transformation " + transformationNumber + ":");

    TextField a00Field = createConfiguredTextField("a00Field", "a00");
    TextField a01Field = createConfiguredTextField("a01Field", "a01");
    TextField a10Field = createConfiguredTextField("a10Field", "a10");
    TextField a11Field = createConfiguredTextField("a11Field", "a11");
    TextField x0Field = createConfiguredTextField("x0Field", "x0");
    TextField x1Field = createConfiguredTextField("x1Field", "x1");

    Button deleteButton = new Button("Delete");
    deleteButton.setOnAction(event -> {
      transformationsBox.getChildren().remove(transformationPane);
      transformationPanes.remove(transformationPane);
      updateTransformationLabels(transformationsBox);
    });

    transformationPane.getChildren().addAll(transformationLabel,
        a00Field, a01Field, a10Field, a11Field,
        x0Field, x1Field,
        deleteButton);

    return transformationPane;
  }

  /**
   * Creates a popup for changing the values of min and max cords.
   *
   * @param controller The controller to handle actions.
   * @return The stage containing the popup.
   */
  public static Stage createChangeCordPopup(GameController controller) {
    Stage stage = new Stage();
    stage.setTitle("Change Min/Max Cords");
    stage.initModality(Modality.APPLICATION_MODAL);

    GridPane layout = new GridPaneBuilder()
        .addLabel("Min X:", 0, 0)
        .addTextField(createConfiguredTextField("minXField"), 1, 0)
        .addLabel("Min Y:", 0, 1)
        .addTextField(createConfiguredTextField("minYField"), 1, 1)
        .addLabel("Max X:", 0, 2)
        .addTextField(createConfiguredTextField("maxXField"), 1, 2)
        .addLabel("Max Y:", 0, 3)
        .addTextField(createConfiguredTextField("maxYField"), 1, 3)
        .addButton("Apply", "applyButton", 1, 4)
        .getLayout();

    Scene scene = new Scene(layout); // Set preferred size for the scene
    stage.setScene(scene);

    Button applyButton = (Button) layout.lookup("#applyButton");
    applyButton.setOnAction(event -> {
      try {
        double minX0 = Validator.validateAndGetDouble(((TextField)
            layout.lookup("#minXField")).getText());
        double minX1 = Validator.validateAndGetDouble(((TextField)
            layout.lookup("#minYField")).getText());
        double maxX0 = Validator.validateAndGetDouble(((TextField)
            layout.lookup("#maxXField")).getText());
        double maxX1 = Validator.validateAndGetDouble(((TextField)
            layout.lookup("#maxYField")).getText());
        controller.updateMinMaxCords(minX0, minX1, maxX0, maxX1);
        stage.close();
      } catch (IllegalArgumentException e) {
        controller.showErrorToast(e.getMessage());
      }
    });

    stage.show();
    return stage;
  }

  /**
   * Creates and configures a TextField with the specified ID.
   *
   * @param id the ID to set for the TextField.
   * @return the configured TextField.
   */
  private static TextField createConfiguredTextField(String id) {
    TextField textField = new TextField();
    textField.setId(id);
    textField.setMaxWidth(TEXT_FIELD_MAX_WIDTH); // Set max width to ensure proper layout
    return textField;
  }

  /**
   * Creates and configures a TextField with the specified ID and prompt text.
   *
   * @param id         the ID to set for the TextField.
   * @param promptText the prompt text to display in the TextField.
   * @return the configured TextField.
   */
  private static TextField createConfiguredTextField(String id, String promptText) {
    TextField textField = new TextField();
    textField.setId(id);
    textField.setPromptText(promptText);
    textField.setMaxWidth(TEXT_FIELD_PROMPT_WIDTH);
    return textField;
  }

  /**
   * Updates the labels of the transformations to reflect their current order.
   *
   * @param transformationsBox the VBox containing all transformation panes.
   */
  private static void updateTransformationLabels(VBox transformationsBox) {
    for (int i = 0; i < transformationsBox.getChildren().size(); i++) {
      HBox transformationPane = (HBox) transformationsBox.getChildren().get(i);
      Label label = (Label) transformationPane.getChildren().getFirst();
      label.setText("Transformation " + (i + 1) + ":");
    }
  }

  /**
   * Loads file names from the specified directory and adds them to the list view.
   *
   * @param listView the ListView to populate with file names.
   */
  private static void loadFileNames(ListView<String> listView) {
    Path dirPath = Paths.get(GlobalVariables.FILE_PATH);
    try (var paths = Files.list(dirPath)) {
      listView.setItems(FXCollections.observableArrayList(
          paths
              .filter(Files::isRegularFile)
              .map(path -> path.getFileName().toString())
              .filter(name -> name.endsWith(".txt"))
              .toList()
      ));
    } catch (IOException e) {
      listView.setItems(FXCollections.observableArrayList("Failed to load files"));
      ErrorLogger.getLogger().logError(Level.SEVERE, e.getMessage());
    }
  }
}
