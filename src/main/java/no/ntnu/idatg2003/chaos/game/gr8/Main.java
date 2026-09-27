package no.ntnu.idatg2003.chaos.game.gr8;

import javafx.application.Application;

/**
 * The {@code Main} class is the entry point of the program.
 */
public class Main {

  private Main() {
    // Empty constructor to prevent instantiation
  }

  /**
   * The main method of the program.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    Application.launch(ChaosGameApp.class, args);
  }
}