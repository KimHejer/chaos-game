package no.ntnu.idatg2003.chaos.game.gr8.math;

import no.ntnu.idatg2003.chaos.game.gr8.utility.Validator;

/**
 * The {@code Matrix2x2} class represents a 2x2 matrix and provides methods for
 * multiplying the matrix with a vector.
 *
 * <p>The matrix is represented by the following formula:
 *
 * <p>| a00 a01 |
 *
 * <p>| a10 a11 |
 * </p>
 * <p>
 * The matrix is multiplied with a vector by the formula:
 * M*v = (a00 * v0 + a01 * v1, a10 * v0 + a11 * v1).
 * </p>
 *
 * <p>Example usage:
 * Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
 * Vector2D vector = new Vector2D(1, 1);
 * Vector2D result = matrix.multiply(vector);
 *
 * @author Kim Hejer
 * @version 1.0.0
 * @see Vector2D
 * @since 1.0.0
 */
public class Matrix2x2 {


  private double a00;
  private double a01;
  private double a10;
  private double a11;

  /**
   * Constructs a new {@code Matrix2x2} instance with specified matrix elements.
   *
   * @param a00 The element in the first row and first column.
   * @param a01 The element in the first row and second column.
   * @param a10 The element in the second row and first column.
   * @param a11 The element in the second row and second column.
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) {
    setA00(a00);
    setA01(a01);
    setA10(a10);
    setA11(a11);
  }

  /**
   * Sets the element in the first row and first column.
   *
   * @param a00 The element in the first row and first column.
   */
  private void setA00(double a00) {
    Validator.checkIfDouble(a00);
    this.a00 = a00;
  }

  /**
   * Sets the element in the first row and second column.
   *
   * @param a01 The element in the first row and second column.
   */
  private void setA01(double a01) {
    Validator.checkIfDouble(a01);
    this.a01 = a01;
  }

  /**
   * Sets the element in the second row and first column.
   *
   * @param a10 The element in the second row and first column.
   */
  private void setA10(double a10) {
    Validator.checkIfDouble(a10);
    this.a10 = a10;
  }

  /**
   * Sets the element in the second row and second column.
   *
   * @param a11 The element in the second row and second column.
   */
  private void setA11(double a11) {
    Validator.checkIfDouble(a11);
    this.a11 = a11;
  }

  /**
   * Returns the element in the first row and first column.
   *
   * @return The element in the first row and first column.
   */
  public double getA00() {
    return a00;
  }

  /**
   * Returns the element in the first row and second column.
   *
   * @return The element in the first row and second column.
   */
  public double getA01() {
    return a01;
  }

  /**
   * Returns the element in the second row and first column.
   *
   * @return The element in the second row and first column.
   */
  public double getA10() {
    return a10;
  }

  /**
   * Returns the element in the second row and second column.
   *
   * @return The element in the second row and second column.
   */
  public double getA11() {
    return a11;
  }

  /**
   * Multiplies the matrix with a vector by the formula:
   * M*v = (a00 * v0 + a01 * v1, a10 * v0 + a11 * v1).
   *
   * @param v the vector to multiply with
   * @return the resulting vector
   */
  public Vector2D multiply(Vector2D v) {
    Validator.validateVector2D(v);
    return new Vector2D(a00 * v.getX0() + a01 * v.getX1(), a10 * v.getX0() + a11 * v.getX1());
  }

}