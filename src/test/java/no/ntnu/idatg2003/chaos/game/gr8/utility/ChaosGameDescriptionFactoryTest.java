package no.ntnu.idatg2003.chaos.game.gr8.utility;

import no.ntnu.idatg2003.chaos.game.gr8.chaosgame.ChaosGameDescription;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.Transform2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChaosGameDescriptionFactoryTest {

  ChaosGameDescription chaosGameDescription;
  List<Transform2D> transformations;

  @BeforeEach
  void setUp() {;
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void sierpinskiChaosGameDescription() {
    chaosGameDescription = ChaosGameDescriptionFactory.sierpinskiChaosGameDescription();
    transformations = chaosGameDescription.getTransforms();
    assertEquals(3, transformations.size());
    assertNotNull(chaosGameDescription);
  }

  @Test
  void barnsleyFernChaosGameDescription() {
    chaosGameDescription = ChaosGameDescriptionFactory.barnsleyFernChaosGameDescription();
    transformations = chaosGameDescription.getTransforms();
    assertEquals(4, transformations.size());
    assertNotNull(chaosGameDescription);
  }

  @Test
  void juliaSetChaosGameDescription() {
    chaosGameDescription = ChaosGameDescriptionFactory.juliaSetChaosGameDescription();
    transformations = chaosGameDescription.getTransforms();
    assertEquals(2, transformations.size());
    assertNotNull(chaosGameDescription);
  }
}