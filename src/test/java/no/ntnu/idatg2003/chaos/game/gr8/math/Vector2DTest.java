package no.ntnu.idatg2003.chaos.game.gr8.math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {

    Vector2D testVector1;
    Vector2D testVector2;



    @BeforeEach
    void setUp() {
        testVector1 = new Vector2D(1, 2);
        testVector2 = new Vector2D(-2, 5);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetX0Positive() {
        assertEquals(1, testVector1.getX0(), "getX0 should return the correct X coordinate.");
    }


    @Test
    void testGetX1Positive() {
        assertEquals(5, testVector2.getX1(), "getX1 should return the correct Y coordinate.");
    }



    @Test
    void testVectorAddition() {
        Vector2D resultVector = testVector1.add(testVector2);
        assertEquals(-1, resultVector.getX0(), "add should correctly calculate the X coordinate of the resulting vector.");
        assertEquals(7, resultVector.getX1(), "add should correctly calculate the Y coordinate of the resulting vector.");
    }


    @Test
    void testVectorSubtraction() {
        Vector2D resultVector = testVector1.subtract(testVector2);
        assertEquals(3, resultVector.getX0(), "subtract should correctly calculate the X coordinate of the resulting vector.");
        assertEquals(-3, resultVector.getX1(), "subtract should correctly calculate the Y coordinate of the resulting vector.");
    }
}

