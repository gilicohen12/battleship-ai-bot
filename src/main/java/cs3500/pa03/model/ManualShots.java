package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The ManualShots class represents a manual shot sequence for a player.
 * It implements the ManualShotsInterface interface.
 */
public class ManualShots implements ManualShotsInterface {
  private List<Coord> shots = new ArrayList<>();

  /**
   * Returns the list of shots.
   *
   * @return the list of shots
   */
  public List<Coord> getShots() {
    return shots;
  }

  /**
   * Adds shots to the manual shot sequence based on the given array of coordinates.
   * The coordinates are specified as pairs of integers [x1, y1, x2, y2, ...].
   *
   * @param shots the array of coordinates representing the shots
   */
  public void addShots(int[] shots) {
    for (int i = 0; i < shots.length; i += 2) {
      int x = shots[i];
      int y = shots[i + 1];
      this.shots.add(new Coord(x, y));
    }
  }

  /**
   * Clears the manual shot sequence by removing all shots.
   */
  public void clearShots() {
    this.shots = new ArrayList<>();
  }

}
