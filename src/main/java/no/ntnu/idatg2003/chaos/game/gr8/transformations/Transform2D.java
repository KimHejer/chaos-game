package no.ntnu.idatg2003.chaos.game.gr8.transformations;

import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;

/**
 * The {@code Transform2D} class is an abstract class for collecting common methods and fields
 * for 2D transformations.
 *
 * @author Kim Hejer and Audun Wetter
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Transform2D {

  /**
   * Constructs a new {@code Transform2D} instance. Protected to prevent instantiation.
   */
  protected Transform2D() {
  }

  /**
   * Abstract method for transforming a 2D vector.
   *
   * @param vector The vector to be transformed.
   * @return The transformed vector.
   */
  public abstract Vector2D transform(Vector2D vector);
}
