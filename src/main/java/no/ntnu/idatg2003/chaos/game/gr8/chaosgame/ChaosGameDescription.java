package no.ntnu.idatg2003.chaos.game.gr8.chaosgame;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2003.chaos.game.gr8.gui.observer.Observable;
import no.ntnu.idatg2003.chaos.game.gr8.gui.observer.Observer;
import no.ntnu.idatg2003.chaos.game.gr8.math.Vector2D;
import no.ntnu.idatg2003.chaos.game.gr8.transformations.Transform2D;
import no.ntnu.idatg2003.chaos.game.gr8.utility.Validator;

/**
 * The {@code ChaosGameDescription} class describes everything needed to calculate the fractal.
 * It contains the minimum and maximum coordinates of the fractal, and a list of transformations.
 * </p>
 *
 * <h2>Overview</h2>
 *
 * <p>This class provides methods for:
 * <ul>
 *   <li>Constructing a new {@code ChaosGameDescription}</li>
 *   <li>Getting and setting the minimum and maximum coordinates</li>
 *   <li>Managing the list of transformations</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * {@code
 * Vector2D minCoords = new Vector2D(0, 0);
 * Vector2D maxCoords = new Vector2D(10, 10);
 * List<Transform2D> transforms = new ArrayList<>();
 * ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);
 * description.addToTransforms(new Transform2D());
 * }
 * </pre>
 *
 * @version 1.0.0
 * @see Vector2D
 * @see Transform2D
 * @since 1.0.0
 */
public class ChaosGameDescription implements Observable {
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;
  private final List<Observer> observers = new ArrayList<>();

  /**
   * Creates a new {@code ChaosGameDescription} with the given minimum and maximum coordinates and
   * a list of transformations. The minimum and maximum coordinates have to be valid non-null
   * {@code Vector2D} objects.
   *
   * @param minCoords  The minimum coordinates of the fractal.
   * @param maxCoords  The maximum coordinates of the fractal.
   * @param transforms The list of transformations to be used in the fractal.
   * @throws IllegalArgumentException if any of the coordinates are invalid
   */
  public ChaosGameDescription(Vector2D minCoords,
                              Vector2D maxCoords,
                              List<Transform2D> transforms) {
    setMinCoords(minCoords);
    setMaxCoords(maxCoords);
    addToTransforms(transforms);
  }

  /**
   * Sets the maximum coordinates of the fractal as a {@code Vector2D}.
   *
   * @param maxCoords The maximum coordinates of the fractal.
   */
  public void setMaxCoords(Vector2D maxCoords) {
    Validator.checkIfDouble(maxCoords.getX0());
    Validator.checkIfDouble(maxCoords.getX1());
    this.maxCoords = maxCoords;
    notifyObservers();
  }

  /**
   * Sets the minimum coordinates of the fractal as a {@code Vector2D}.
   *
   * @param minCoords The minimum coordinates of the fractal.
   */
  public void setMinCoords(Vector2D minCoords) {
    Validator.checkIfDouble(minCoords.getX0());
    Validator.checkIfDouble(minCoords.getX1());
    this.minCoords = minCoords;
    notifyObservers();
  }

  /**
   * Sets the list of transformations to be used in the fractal.
   *
   * @param transforms The list of transformations to be used in the fractal.
   */
  public void addToTransforms(List<Transform2D> transforms) {
    this.transforms = transforms;
    notifyObservers();
  }

  /**
   * Changes the list of transformations to be used in the fractal.
   *
   * @param transform The transformation to be added to the list of transformations.
   */
  public void addToTransforms(Transform2D transform) {
    transforms.add(transform);
  }

  /**
   * Gets the list of transformations to be used in the fractal.
   *
   * @return The list of transformations to be used in the fractal.
   */
  public List<Transform2D> getTransforms() {
    return transforms;
  }

  /**
   * Gets the maximum coordinates of the fractal.
   *
   * @return The maximum coordinates of the fractal.
   */
  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  /**
   * Gets the minimum coordinates of the fractal.
   *
   * @return The minimum coordinates of the fractal.
   */
  public Vector2D getMinCoords() {
    return minCoords;
  }

  /**
   * Adds an observer to the list of observers.
   *
   * @param observer The observer to be added.
   */
  @Override
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  /**
   * Removes an observer from the list of observers.
   *
   * @param observer The observer to be removed.
   */
  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  /**
   * Changes the {@code ChaosGameDescription} to the given {@code ChaosGameDescription}.
   *
   * @param chaosGameDescription The {@code ChaosGameDescription} to be set.
   */
  public void changeChaosGameDescription(ChaosGameDescription chaosGameDescription) {
    this.minCoords = chaosGameDescription.getMinCoords();
    this.maxCoords = chaosGameDescription.getMaxCoords();
    this.transforms = chaosGameDescription.getTransforms();
    notifyObservers();
  }

  /**
   * Notifies all observers about state changes in the {@code ChaosGameDescription}.
   */
  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }


}
