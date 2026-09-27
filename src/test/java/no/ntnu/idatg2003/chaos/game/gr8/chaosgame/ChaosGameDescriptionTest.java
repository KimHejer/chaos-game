package no.ntnu.idatg2003.chaos.game.gr8.chaosgame;

import no.ntnu.idatg2003.chaos.game.gr8.math.Matrix2x2;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.AffineTransform2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.JuliaTransform;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChaosGameDescriptionTest {

  Vector2D v1;
  Vector2D minCords;
  Vector2D maksCords;
  Vector2D nullMinCoords;
  Matrix2x2 m1;
  List<Transform2D> transforms;
  ChaosGameDescription chaosGameDescription;

  @BeforeEach
  void setUp() {
    // Arrange
    nullMinCoords = null;
    v1 = new Vector2D(1, 4);
    minCords = new Vector2D(-3, -6);
    maksCords = new Vector2D(1, 4);
    m1 = new Matrix2x2(1, 2, 3, 4);
    transforms = List.of(new JuliaTransform(v1, 2), new AffineTransform2D(m1, v1));
    chaosGameDescription = new ChaosGameDescription(minCords, maksCords, transforms);
  }

  @Test
  void positiveChaosGameDescriptionTest() {
    // Assert
    assertEquals(chaosGameDescription.getMinCoords(), minCords);
    assertEquals(chaosGameDescription.getMaxCoords(), maksCords);
    assertEquals(chaosGameDescription.getTransforms(), transforms);
  }

  @Test
  void testConstructorWithNullMinCoords() {
    // Assert
    assertThrows(NullPointerException.class, () -> new ChaosGameDescription(nullMinCoords, maksCords, transforms));
  }

  @Test
  void positiveTestForGetters() {
    // Assert
    assertEquals(chaosGameDescription.getMinCoords(), minCords);
    assertEquals(chaosGameDescription.getMaxCoords(), maksCords);
    assertEquals(chaosGameDescription.getTransforms(), transforms);
  }

}
