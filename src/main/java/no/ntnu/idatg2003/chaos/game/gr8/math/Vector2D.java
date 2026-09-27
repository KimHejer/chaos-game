package no.ntnu.idatg2003.chaos.game.gr8.math;

import no.ntnu.idatg2003.chaos.game.gr8.utility.Validator;

/**
 * Represents a 2-dimensional vector and provides basic vector operations.
 * This class is utilized for operations involving 2D vectors in a game context.
 * The class provides methods for basic vector operations such as addition and subtraction.
 *
 * <p>Example usage:
 * Vector2D v1 = new Vector2D(1, 1);
 * Vector2D v2 = new Vector2D(2, 2);
 * Vector2D v3 = v1.add(v2);
 * </p>
 *
 * <p>Example usage:
 * Vector2D v1 = new Vector2D(1, 1);
 * Vector2D v2 = new Vector2D(2, 2);
 * Vector2D v3 = v1.subtract(v2);
 * }
 * </pre>
 *
 * @author Audun Wetter
 * @version 1.0.0
 * @since 1.0.0
 */
public class Vector2D {
  // The x component of the vector
  private double x0;
  // The y component of the vector
  private double x1;

  /**
   * Constructs a new {@code Vector2D} instance with specified x and y components.
   *
   * @param x0 The x component of the vector.
   * @param x1 The y component of the vector.
   */
  public Vector2D(double x0, double x1) {
    setX0(x0);
    setX1(x1);
  }

  /**
   * Gets the x component of the vector.
   *
   * @return The x component.
   */
  public double getX0() {
    return this.x0;
  }

  /**
   * Gets the y component of the vector.
   *
   * @return The y component.
   */
  public double getX1() {
    return this.x1;
  }

  /**
   * Sets the x component of the vector.
   *
   * @param x0 The new x component value.
   */
  private void setX0(double x0) {
    Validator.checkIfDouble(x0);
    this.x0 = x0;
  }

  /**
   * Sets the y component of the vector.
   *
   * @param x1 The new y component value.
   */
  private void setX1(double x1) {
    Validator.checkIfDouble(x1);
    this.x1 = x1;
  }

  /**
   * Adds the provided vector to the current vector and returns the result as a new vector.
   *
   * @param otherVector The vector to add.
   * @return The resulting vector after addition.
   */
  public Vector2D add(Vector2D otherVector) {
    Validator.validateVector2D(otherVector);
    return new Vector2D(this.x0 + otherVector.x0, this.x1 + otherVector.x1);
  }

  /**
   * Subtracts the provided vector from the current vector and returns the result as a new vector.
   *
   * @param otherVector The vector to subtract.
   * @return The resulting vector after subtraction.
   */
  public Vector2D subtract(Vector2D otherVector) {
    Validator.validateVector2D(otherVector);
    return new Vector2D(this.x0 - otherVector.x0, this.x1 - otherVector.x1);
  }

  /**
   * Overrides the default {@code toString} method to provide a custom string representation of the
   * vector. The format is as follows: (x0, x1)
   *
   * @return the string representation of the vector.
   */
  @Override
  public String toString() {
    return "(" + x0 + ", " + x1 + ")";
  }
}
