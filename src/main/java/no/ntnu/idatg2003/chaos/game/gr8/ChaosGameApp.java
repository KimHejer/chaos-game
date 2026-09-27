package no.ntnu.idatg2003.chaos.game.gr8;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.chaos.game.gr8.gui.controllers.GameController;
import no.ntnu.idatg2003.chaos.game.gr8.utility.ErrorLogger;

/**
 * The main class of the application. This class is responsible for starting the application and
 * setting up the primary stage.
 *
 * <p>The class is a subclass of {@code Application} and overrides the {@code start} method to
 * initialize the primary stage and set the scene to the game view.
 *
 * @author Kim Hejer and Audun Wetter
 * @version 1.0.0
 * @see Application
 * @see GameController
 * @since 1.0.0
 */
public class ChaosGameApp extends Application {

  /**
   * The default constructor of the class.
   */
  public ChaosGameApp() {
    // Empty constructor to override the default constructor.
  }


  /**
   * The main entry point of the application. This method is called when the application is started.
   *
   * @param stage The primary stage of the application.
   */
  @Override
  public void start(Stage stage) {
    try {
      GameController gameController = new GameController();
      Parent root = gameController.getView();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Chaos Game");
      stage.show();
    } catch (Exception e) {
      ErrorLogger.getLogger().logError(e.getMessage());
    }
  }

  /**
   * The main method of the application. This method is the entry point of the application and starts
   * the application.
   *
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
