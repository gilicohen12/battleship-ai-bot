package cs3500.pa03.model;

import java.util.List;

/**
 * The ManualShotsInterface interface defines the contract for a manual shot sequence.
 * It provides methods to get shots, add shots, and clear the shot sequence.
 */
public interface ManualShotsInterface {

  /**
   * Returns the list of shots in the manual shot sequence.
   *
   * @return the list of shots
   */
  List<Coord> getShots();

  /**
   * Adds shots to the manual shot sequence based on the given array of coordinates.
   * The coordinates are specified as pairs of integers [x1, y1, x2, y2, ...].
   *
   * @param shots the array of coordinates representing the shots
   */
  void addShots(int[] shots);

  /**
   * Clears the manual shot sequence by removing all shots.
   */
  void clearShots();

}
