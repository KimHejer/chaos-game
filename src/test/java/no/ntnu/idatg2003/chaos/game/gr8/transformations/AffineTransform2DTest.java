package no.ntnu.idatg2003.chaos.game.gr8.transformations;

import no.ntnu.idatg2003.chaos.game.gr8.math.Matrix2x2;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AffineTransform2DTest {

    Matrix2x2 matrix1;
    Matrix2x2 matrix2;
    Vector2D vectorPositive;
    Vector2D vectorNegative;
    AffineTransform2D affineTransform2DPositive;
    AffineTransform2D affineTransform2DNegative;

    Vector2D vector2;
    Vector2D vector3;

    @BeforeEach
    void setUp() {
        // Arrange
        matrix1 = new Matrix2x2(5, 3, 1, 4);
        matrix2 = new Matrix2x2(-2, 1, -3, 4);
        vectorPositive = new Vector2D(5, 3);
        vectorNegative = new Vector2D(-2, 1);
        affineTransform2DPositive = new AffineTransform2D(matrix1, vectorPositive);
        affineTransform2DNegative = new AffineTransform2D(matrix2, vectorNegative);
        vector2 = new Vector2D(4, 12);
        vector3 = new Vector2D(3, 7);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void positiveTransformPositiveValues() {
        // Act
        Vector2D result = affineTransform2DPositive.transform(vector2);

        // Assert
        assertEquals(61, result.getX0());
        assertEquals(55, result.getX1());
    }

    @Test
    void positiveTransformNegativeValues() {
        // Act
        Vector2D result = affineTransform2DNegative.transform(vector3);

        // Assert
        assertEquals(-1.0, result.getX0());
        assertEquals(20.0, result.getX1());
    }

    @Test
    void positiveTestForGetters() {
        // Act
        Matrix2x2 resultMatrix = affineTransform2DPositive.getMatrix();
        Vector2D resultVector = affineTransform2DPositive.getVector();

        // Assert
        assertEquals(matrix1, resultMatrix);
        assertEquals(vectorPositive, resultVector);
    }

    @Test
    void positiveTestForToFileString() {
        // Act
        String result = affineTransform2DPositive.toFileString();

        // Assert
        assertEquals("5.0, 3.0, 1.0, 4.0, 5.0, 3.0", result);
    }
}