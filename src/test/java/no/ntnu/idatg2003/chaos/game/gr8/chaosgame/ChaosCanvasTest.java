package no.ntnu.idatg2003.chaos.game.gr8.chaosgame;

import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChaosCanvasTest {

  Vector2D minCoords;
  Vector2D maxCoords;
  Vector2D testVectorPositive1;
  Vector2D testVectorPositive2;
  Vector2D testVectorPositive3;
  Vector2D testVectorNegative1;
  Vector2D testVectorNegative2;
  Vector2D testVectorNegative3;
  Vector2D testVectorOutsideCanvas1;
  Vector2D testVectorOutsideCanvas2;
  Vector2D testVectorOutsideCanvas3;
  ChaosCanvas chaosCanvas;

  @BeforeEach
  void setUp() {
    // Arrange
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(10, 10);
    testVectorPositive1 = new Vector2D(5, 5);
    testVectorPositive2 = new Vector2D(0, 3);
    testVectorPositive3 = new Vector2D(8, 4);
    testVectorNegative1 = new Vector2D(-5, -5);
    testVectorNegative2 = new Vector2D(1, -3);
    testVectorNegative3 = new Vector2D(-8, 4);
    testVectorOutsideCanvas1 = new Vector2D(15, 15);
    testVectorOutsideCanvas2 = new Vector2D(1, 13);
    testVectorOutsideCanvas3 = new Vector2D(18, 4);
    chaosCanvas = new ChaosCanvas(10, 10, minCoords, maxCoords);
  }

  @AfterEach
  void tearDown() {
  }


  @Test
  void putPixelPositiveTestForPositiveValues() {
    // Act
    chaosCanvas.putPixel(testVectorPositive1);
    chaosCanvas.putPixel(testVectorPositive2);
    chaosCanvas.putPixel(testVectorPositive3);
    // Assert
    assertEquals(1, chaosCanvas.getPixel(testVectorPositive1));
    assertEquals(1, chaosCanvas.getPixel(testVectorPositive2));
    assertEquals(1, chaosCanvas.getPixel(testVectorPositive3));
  }

  @Test
  void clearPositiveTest() {
    // Act
    chaosCanvas.putPixel(testVectorPositive1);
    chaosCanvas.putPixel(testVectorPositive2);
    chaosCanvas.putPixel(testVectorPositive3);
    chaosCanvas.clear();
    // Assert
    assertNotEquals(1, chaosCanvas.getPixel(testVectorPositive1));
    assertNotEquals(1, chaosCanvas.getPixel(testVectorPositive2));
    assertNotEquals(1, chaosCanvas.getPixel(testVectorPositive3));
  }

  @Test
  void putPixelNegativeTestForNegativeValues () {
    // Act
    chaosCanvas.putPixel(testVectorNegative1);
    chaosCanvas.putPixel(testVectorNegative2);
    chaosCanvas.putPixel(testVectorNegative3);
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> chaosCanvas.getPixel(testVectorNegative1));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> chaosCanvas.getPixel(testVectorNegative2));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> chaosCanvas.getPixel(testVectorNegative3));
  }

  @Test
  void putPixelNegativeTestForValuesOutsideCanvas () {
    // Act
    chaosCanvas.putPixel(testVectorOutsideCanvas1);
    chaosCanvas.putPixel(testVectorOutsideCanvas2);
    chaosCanvas.putPixel(testVectorOutsideCanvas3);
    // Assert
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> chaosCanvas.getPixel(testVectorOutsideCanvas1));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> chaosCanvas.getPixel(testVectorOutsideCanvas2));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> chaosCanvas.getPixel(testVectorOutsideCanvas3));
  }

}
