package no.ntnu.idatg2003.chaos.game.gr8.math;

/**
 * The {@code Complex} class extends the class {@code Vector2D} and represents a complex vector.
 * The class behaves as a {@code Vector2D} with the addition of a method to calculate the square
 * root of the complex number.
 *
 * <p>Example usage:
 * Complex point = new Complex(1, 1);
 * Complex root = point.sqrt();
 *
 * @author Kim Hejer
 * @version 1.0.0
 * @see Vector2D
 * @since 1.0.0
 */
public class Complex extends Vector2D {


  /**
   * Constructs a new Complex instance with specified real and imaginary parts.
   *
   * @param realPart      The real part of the complex number.
   * @param imaginaryPart The imaginary part of the complex number.
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   * Constructs a new Complex instance with specified vector coordinates.
   *
   * @param vector The vector to be converted to a complex number.
   */
  public Complex(Vector2D vector) {
    super(vector.getX0(), vector.getX1());
  }

  /**
   * Generates a new complex number that is the square root of the given vector coordinates.
   *
   * <p>This method calculates the square root of a complex number using the formula:
   * <pre>
   * {@code
   * root = sqrt(x^2 + y^2)
   * realPart = sqrt((root + x) / 2)
   * imaginaryPart = sign(y) * sqrt((root - x) / 2)
   * }
   * </pre>
   * where {@code x} is the real part and {@code y} is the imaginary part of the complex number.
   *
   *
   * @return A new complex number with a real part and an imaginary part.
   */
  public Complex sqrt() {
    int sgn = 1;
    if (this.getX1() < 0) {
      sgn = -1;
    }
    double root = Math.sqrt(Math.pow(this.getX0(), 2) + Math.pow(this.getX1(), 2));
    return new Complex(Math.sqrt((root + this.getX0()) / 2),
        sgn * Math.sqrt((root - this.getX0()) / 2));
  }
}
