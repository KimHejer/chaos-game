package no.ntnu.idatg2003.chaos.game.gr8.transformations;

import no.ntnu.idatg2003.chaos.game.gr8.math.Matrix2x2;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.utility.Validator;

/**
 * The {@code AffineTransform2D} class extends {@code Transform2D} to implement
 * a 2-dimensional affine transformation. This transformation is represented
 * by a combination of a linear transformation (matrix) and a translation (vector).
 *
 * <p>Affine transformations can perform various geometric operations such as
 * rotation, scaling, translation, and shearing on 2D vectors.
 *
 *
 * @author Audun Wetter
 * @version 1.0.0
 * @see Transform2D
 * @see Matrix2x2
 * @see Vector2D
 * @since 1.0.0
 */
public class AffineTransform2D extends Transform2D {

  // The matrix representing the linear part of the affine transformation.
  private Matrix2x2 matrix;

  // The vector representing the translation part of the affine transformation.
  private Vector2D vector;

  /**
   * Constructs a new {@code AffineTransform2D} with the specified linear
   * transformation matrix and translation vector.
   *
   * @param matrix the linear transformation matrix
   * @param vector the translation vector
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    setMatrix(matrix);
    setVector(vector);
  }

  private void setMatrix(Matrix2x2 matrix) {
    Validator.validateMatrix2x2(matrix);
    this.matrix = matrix;
  }

  private void setVector(Vector2D vector) {
    Validator.validateVector2D(vector);
    this.vector = vector;
  }

  /**
   * Returns the linear transformation matrix of the affine transformation.
   *
   * @return the linear transformation matrix
   */
  public Matrix2x2 getMatrix() {
    return matrix;
  }

  /**
   * Returns the translation vector of the affine transformation.
   *
   * @return the translation vector
   */
  public Vector2D getVector() {
    return vector;
  }

  /**
   * Applies the affine transformation to a given {@code Vector2D}.
   * <p>
   * This method first applies the linear transformation (matrix multiplication)
   * to the input vector, and then adds the translation vector to the result,
   * effectively performing the affine transformation.
   * </p>
   *
   * @param vector the {@code Vector2D} to be transformed
   * @return the transformed {@code Vector2D} instance
   */
  @Override
  public Vector2D transform(Vector2D vector) {
    Validator.validateVector2D(vector);
    return matrix.multiply(vector).add(this.vector);
  }

  /**
   * Returns a string representation {@code Matrix2x2} and {@code Vector2D} in
   * the format "a00, a01, a10, a11, x0, x1".
   *
   * @return a string representation of the components of the affine transformation
   */
  public String toFileString() {
    // Assuming your Transform2D class has a way to access its parameters:
    return matrix.getA00() + ", "
        + matrix.getA01() + ", "
        + matrix.getA10() + ", "
        + matrix.getA11() + ", "
        + vector.getX0() + ", "
        + vector.getX1();
  }
}