package no.ntnu.idatg2003.chaos.game.gr8.transformations;

import no.ntnu.idatg2003.chaos.game.gr8.math.Complex;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JuliaTransformTest {

  Complex point = new Complex(1, 1); // 1.098684113, 0.4550898606
  JuliaTransform jt1 = new JuliaTransform(point, 1);
  JuliaTransform jt2 = new JuliaTransform(point, -1);
  Vector2D z = new Vector2D(1, 1);

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void testTransformComplexPointPositiveValues() {
    Vector2D result = jt1.transform(z);
    assertEquals(0, result.getX0());
    assertEquals(0, result.getX1());
  }

  @Test
  void testTransformComplexPointNegativeValues() {
    Vector2D result = jt2.transform(z);
    assertEquals(0, Math.abs(result.getX0()));
    assertEquals(0, Math.abs(result.getX1()));
  }

  @Test
  void toFileString() {
    assertEquals("1.0, 1.0, 1", jt1.toFileString());
    assertEquals("1.0, 1.0, -1", jt2.toFileString());
  }

}