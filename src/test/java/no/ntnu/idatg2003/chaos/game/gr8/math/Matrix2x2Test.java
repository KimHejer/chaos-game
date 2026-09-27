package no.ntnu.idatg2003.chaos.game.gr8.math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix2x2Test {

  Vector2D v1;
  Vector2D v2;
  Matrix2x2 m1;
  @BeforeEach
  void setUp() {
    // Arrange
    v1 = new Vector2D(1, 4);
    v2 = new Vector2D(-3, -6);

    m1 = new Matrix2x2(1, 2, 3, 4);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void multiplyPositiveTestForPositiveValues() {
    // Act
    Vector2D result = m1.multiply(v1);
    // Assert
    assertEquals(9, result.getX0());
    assertEquals(19, result.getX1());
  }

  @Test
  void multiplyPositiveTestForNegativeValues() {
    // Act
    Vector2D result = m1.multiply(v2);
    // Assert
    assertEquals(-15, result.getX0());
    assertEquals(-33, result.getX1());
  }

  @Test
  void multiplyNegativeTestForNullVector() {
    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> m1.multiply(null));
  }

  @Test
  void positiveTestForGetters() {
    // Assert
    assertEquals(1, m1.getA00());
    assertEquals(2, m1.getA01());
    assertEquals(3, m1.getA10());
    assertEquals(4, m1.getA11());
  }

}
