package no.ntnu.idatg2003.chaos.game.gr8.transformations;

import no.ntnu.idatg2003.chaos.game.gr8.math.Complex;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.utility.Validator;

/**
 * The {@code JuliaTransform} class extends the abstract class {@code Transform2D} and represents
 * a complex transformation in the form z -> +/- * sqrt(z - c), where c is a complex constant
 * represented by the field "point" and the square root is complex.
 *
 * <p>This class is utilized for operations involving complex transformations in a game context.
 * The constructor takes in the complex constant c in form of a {@code Vector2D} and a sign,
 * which is either 1 or -1. The sign determines whether the transformation is a positive or
 * negative transformation.
 * </p>
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * Vector2D point = new Vector2D(0.355, 0.355);
 * int sign = 1;
 * JuliaTransform juliaTransform = new JuliaTransform(point, sign);
 * Vector2D result = juliaTransform.transform(new Vector2D(0.4, 0.6));
 * }
 * </pre>
 *
 *
 * @author Kim Hejer
 * @version 1.0.0
 * @see Transform2D
 * @see Complex
 * @since 1.0.0
 */
public class JuliaTransform extends Transform2D {

  private Complex point;
  private int sign;

  /**
   * Constructs a new {@code JuliaTransform} instance with specified complex point and sign.
   *
   * @param point The complex point of the transformation.
   * @param sign  The sign of the transformation.
   */
  public JuliaTransform(Vector2D point, int sign) {
    setPoint(point);
    setSign(sign);
  }

  // Sets the complex point of the transformation.
  private void setPoint(Vector2D vector) {
    Validator.validateVector2D(vector);
    if (vector instanceof Complex vectorPoint) {
      point = vectorPoint;
    } else {
      this.point = new Complex(vector.getX0(), vector.getX1());
    }
  }

  //  Sets the sign of the transformation.
  private void setSign(int sign) {
    if (Validator.checkIfNegative(sign)) {
      this.sign = -1;
    } else {
      this.sign = 1;
    }
  }

  /**
   * Overrides transform method from the abstract class {@code Transform2D}.
   * Generates a new {@code Vector2D} that is the result of the transformation of the given vector.
   *
   * @param z The {@code Vector2D} to transform.
   * @return A new {@code Vector2D} with a real part and an imaginary part.
   */
  @Override
  public Vector2D transform(Vector2D z) {
    Validator.validateVector2D(z);
    Complex zc = new Complex(z.subtract(point));
    Complex root = zc.sqrt();
    return new Vector2D(sign * root.getX0(), sign * root.getX1());
  }

  /**
   * Method to return the {@code String} representation of the point.
   *
   * @return The {@code String} representation of the point.
   */
  public String toFileString() {
    return point.getX0() + ", " + point.getX1() + ", " + sign;
  }
}
