package no.ntnu.idatg2003.chaos.game.gr8.math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {

  Complex c1;
  Complex c2;
  Complex c3;
  Complex c4;
  @BeforeEach
  void setUp() {
    c1 = new Complex(0.1, -0.4);
    c2 = new Complex(-0.2, -0.3);
    c3 = new Complex(0.4, 0.2);
    c4 = new Complex(0.3, 0.6);

  }

  @Test
  void sqrtPositiveTestForPositiveValues() {
    // Checked results with calculator
    assertEquals(0.5061178532, c1.sqrt().getX0(), 0.0000000001);
    assertEquals(-0.3951648786, c1.sqrt().getX1(), 0.0000000001);
    Complex c5 = new Complex(c3.subtract(c4));
    assertEquals(0.5061178532, c5.sqrt().getX0(), 0.0000000001);
    assertEquals(-0.3951648786, c5.sqrt().getX1(), 0.0000000001);
  }

  @Test
  void sqrtPositiveTestForNegativeValues() {
    // Checked results with calculator
    assertEquals(0.2833329557, c2.sqrt().getX0(), 0.0000000001);
    assertEquals(-0.5294124704, c2.sqrt().getX1(), 0.0000000001);
  }


  @Test
  void sqrtNegativeTestForPositiveValues() {
    assertNotEquals(0.1, c1.sqrt().getX0(), 0.0000000001);
    assertNotEquals(0.4, c1.sqrt().getX1(), 0.0000000001);
  }
  @Test
  void sqrtNegativeTestForNegativeValues() {
    assertNotEquals(-0.2, c2.sqrt().getX0(), 0.0000000001);
    assertNotEquals(-0.3, c2.sqrt().getX1(), 0.0000000001);
  }

  @AfterEach
  void tearDown() {
  }
}
