package no.ntnu.idatg2003.chaos.game.gr8.gui.observer;

/**
 * Interface for observable objects.
 *
 * @author Audun Wetter
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Observable {

  /**
   * Adds an observer to the list of observers.
   *
   * @param observer The observer to add.
   */
  void addObserver(Observer observer);

  /**
   * Removes an observer from the list of observers.
   *
   * @param observer The observer to remove.
   */
  void removeObserver(Observer observer);

  /**
   * Notifies all observers in the list of observers.
   */
  void notifyObservers();
}
