package no.ntnu.idatg2003.chaos.game.gr8.utility;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Static class for creating and displaying toast messages.
 * <p>
 * This class provides a method to display temporary popup messages (toasts) on the screen.
 * The toasts are automatically dismissed after a specified timeout.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * Toast.showToast("Hello, World!", scene, stylesheet);
 * }</pre>
 *
 *
 * @author Audun Wetter
 * @version 1.0.0
 * @since 1.0.0
 */
public class Toast {

  private Toast() {
    // Prevent instantiation
  }

  private static Popup createPopup(final String message,ObservableList<String> stylesheet) {
    final Popup popup = new Popup();
    popup.setAutoFix(true);
    Label label = new Label(message);
    label.getStylesheets().add(stylesheet.getFirst());
    label.getStyleClass().add("popup");
    popup.getContent().add(label);
    return popup;
  }

  /**
   * Shows a toast message.
   *
   * @param message The message to display.
   * @param context The scene to display the toast on.
   */
  public static void showToast(final String message, final Scene context, ObservableList<String> stylesheet) {
    Stage stage = (Stage) context.getWindow();
    final Popup popup = createPopup(message,stylesheet);
    popup.setOnShown(e -> {
      popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
      popup.setY(stage.getY() + stage.getHeight() / 1.2 - popup.getHeight());
    });
    popup.show(stage);

    int toastTimeout = 2000;
    new Timeline(new KeyFrame(
        Duration.millis(toastTimeout),
        ae -> popup.hide())).play();
  }
}

