package pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualShotsInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the ManualShotsInterface interface used for testing purposes.
 * This class allows tracking of method invocations by appending the method names to a
 * StringBuilder object.
 */
public class MockManualShots implements ManualShotsInterface {
  private StringBuilder output;

  /**
   * Instantiates a MockManualShots object with the specified output StringBuilder.
   *
   * @param output the StringBuilder object to store the output messages
   */
  MockManualShots(StringBuilder output) {
    this.output = output;
  }

  /**
   * Appends getShots
   *
   * @return an empty ArrayList
   */
  public List<Coord> getShots() {
    output.append("getShots\n");
    return new ArrayList<>();
  }

  /**
   * Appends addShots
   *
   * @param shots the array of integers representing the shots to be added
   */
  public void addShots(int[] shots) {
    output.append("addShots\n");
  }

  /**
   * Appends clearShots
   */
  public void clearShots() {
    output.append("clearShots\n");
  }
}
