package no.ntnu.idatg2003.chaos.game.gr8.chaosgame;

import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.Transform2D;
import no.ntnu.idatg2003.chaos.game.gr8.utility.ChaosGameDescriptionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ChaosGameTest {

  Vector2D mincoords1;
  Vector2D maxcoords1;
  Vector2D mincoords2;
  Vector2D maxcoords2;
  ChaosCanvas sierpinskiCanvas;
  ChaosCanvas juliaCanvas;
  ChaosGame sierpinskiGame;
  ChaosGame juliaGame;
  ChaosGame gameWithEmpty;
  ChaosGameDescription juliaChaosGameDescription;
  ChaosGameDescription sierpinskiChaosGameDescription;
  ChaosGameDescription descriptionWithEmptyList;
  ArrayList<Transform2D> emptyList;

  @BeforeEach
  void setUp() {
    // Arrange
    mincoords1 = new Vector2D(0, 0);
    maxcoords1 = new Vector2D(1, 1);
    mincoords2 = new Vector2D(-1.6, -1);
    maxcoords2 = new Vector2D(1.6, 1);
    emptyList = new ArrayList<>();

    sierpinskiCanvas = new ChaosCanvas(100, 100, mincoords1, maxcoords1);
    juliaCanvas = new ChaosCanvas(100, 100, mincoords2, maxcoords2);
    sierpinskiChaosGameDescription =
      ChaosGameDescriptionFactory.sierpinskiChaosGameDescription();

    juliaChaosGameDescription =
      ChaosGameDescriptionFactory.juliaSetChaosGameDescription();
      descriptionWithEmptyList = new ChaosGameDescription(mincoords1, maxcoords1, emptyList);


    sierpinskiGame = new ChaosGame(sierpinskiCanvas, sierpinskiChaosGameDescription);
    juliaGame = new ChaosGame(juliaCanvas, juliaChaosGameDescription);
    gameWithEmpty = new ChaosGame(sierpinskiCanvas, descriptionWithEmptyList);


  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void runStepsPositiveTestForSierpinski() {
    // Act
    int steps = 10000;
    sierpinskiGame.runSteps(steps);

    int count = 0;
    int length = sierpinskiCanvas.getCanvasArray().length;
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        if (sierpinskiCanvas.getCanvasArray()[i][j] == 1) {
          //System.out.print("X");
          count++;
        } else {
          //System.out.print(" ");
        }
      }
      //System.out.println();
    }

    // Assert
    // check if the number of pixels is close to the expected number of pixels
    assertTrue(count > (steps / 10));
    //System.out.println(count);
  }

  @Test
  void runStepsPositiveTestForJulia() {
    // Act
    int steps = 1000;
    juliaGame.runSteps(steps);

    int count = 0;
    int length = juliaCanvas.getCanvasArray().length;
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        if (juliaCanvas.getCanvasArray()[i][j] == 1) {
          //System.out.print("X");
          count++;
        } else {
          //System.out.print(" ");
        }
      }
      //System.out.println();
    }

    // Assert
    // check if the number of pixels is close to the expected number of pixels
    assertTrue(count > (steps / 10));
    //System.out.println(count);
  }

  @Test
  void changeChaosCanvasPositiveTest() {
    // Act
    ChaosCanvas newCanvas = new ChaosCanvas(100, 100, mincoords2, maxcoords2);
    sierpinskiGame.setChaosCanvas(newCanvas);
    // Assert
    assertEquals(newCanvas, sierpinskiGame.getChaosCanvas());
  }

  @Test
  void getChaosCanvasPositiveTest() {
    // Act
    ChaosCanvas chaosCanvas = sierpinskiGame.getChaosCanvas();
    // Assert
    assertEquals(sierpinskiCanvas, chaosCanvas);
  }

  @Test
  void runStepsNegativeTestForEmptyList() {
    // Act
    int steps = 1000;
    // Assert
    assertThrows(IllegalStateException.class, () -> gameWithEmpty.runSteps(steps));
  }
}