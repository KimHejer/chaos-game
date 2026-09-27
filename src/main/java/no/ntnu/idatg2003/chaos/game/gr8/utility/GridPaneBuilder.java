package no.ntnu.idatg2003.chaos.game.gr8.utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * The {@code GridPaneBuilder} class is a utility class for building {@code GridPane}s in the chaos
 * game application. The class provides methods for adding labels, buttons,
 * text fields, and list views to the {@code GridPane} layout.
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * GridPaneBuilder builder = new GridPaneBuilder();
 * builder.addLabel("Name", 0, 0)
 *        .addTextField(new TextField(), 1, 0)
 *        .addButton("Submit", "submitBtn", 0, 1);
 * GridPane layout = builder.getLayout();
 * }
 * </pre>
 *
 *
 * @author Kim Hejer
 * @version 1.1.0
 * @see GridPane
 * @see Label
 * @see Button
 * @see TextField
 * @since 1.1.0
 */
public class GridPaneBuilder {
  private final GridPane layout;

  /**
   * Constructs a new {@code PopupBuilder} instance and initializes the layout.
   */
  public GridPaneBuilder() {
    layout = new GridPane();
    layout.setPadding(new Insets(10));
    layout.setVgap(30);
    layout.setHgap(50);
    layout.setAlignment(Pos.CENTER);
  }

  /**
   * Adds a label to the popup layout at the specified column and row.
   *
   * @param text The text of the label.
   * @param col  The column index of the label.
   * @param row  The row index of the label.
   * @return The {@code PopupBuilder} instance.
   */
  public GridPaneBuilder addLabel(String text, int col, int row) {
    layout.add(new Label(text), col, row);
    return this;
  }

  /**
   * Adds a button to the popup layout at the specified column and row.
   *
   * @param name     The name of the button.
   * @param buttonId The ID of the button.
   * @param col      The column index of the button.
   * @param row      The row index of the button.
   * @return The {@code PopupBuilder} instance.
   */
  public GridPaneBuilder addButton(String name, String buttonId, int col, int row) {
    Button button = new Button(name);
    button.setId(buttonId);
    layout.add(button, col, row);
    return this;
  }

  /**
   * Adds a text field to the popup layout at the specified column and row.
   *
   * @param textField The text field to add.
   * @param col       The column index of the text field.
   * @param row       The row index of the text field.
   * @return The {@code PopupBuilder} instance.
   */
  public GridPaneBuilder addTextField(TextField textField, int col, int row) {
    layout.add(textField, col, row);
    return this;
  }

  /**
   * Adds a list view to the popup layout at the specified column and row.
   *
   * @param listView The list view to add.
   * @param col      The column index of the list view.
   * @param row      The row index of the list view.
   * @return The {@code PopupBuilder} instance.
   */
  public GridPaneBuilder addListView(ListView<String> listView, int col, int row) {
    layout.add(listView, col, row);
    return this;
  }


  /**
   * Returns the layout of the popup.
   *
   * @return The {@code GridPane} layout of the popup.
   */
  public GridPane getLayout() {
    return layout;
  }

}
