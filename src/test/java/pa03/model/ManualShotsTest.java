package pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualShots;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the ManualShots class.
 */
class ManualShotsTest {
  private ManualShots manualShots;

  /**
   * Sets up the test environment
   */
  @BeforeEach
  public void setUp() {
    manualShots = new ManualShots();
  }

  /**
   * Tests the getShots method of the ManualShots class.
   */
  @Test
  public void testGetShots() {
    ArrayList<Coord> empty = new ArrayList<>();
    assertEquals(empty, manualShots.getShots());
  }

  /**
   * Tests the addShots method of the ManualShots class.
   */
  @Test
  public void testAddShots() {
    int[] shots = {1, 2, 3, 4, 5, 6};
    manualShots.addShots(shots);

    List<Coord> actualShots = manualShots.getShots();
    assertEquals(3, actualShots.size());
    assertEquals(1, actualShots.get(0).getX());
    assertEquals(2, actualShots.get(0).getY());
    assertEquals(3, actualShots.get(1).getX());
    assertEquals(4, actualShots.get(1).getY());
    assertEquals(5, actualShots.get(2).getX());
    assertEquals(6, actualShots.get(2).getY());
  }

  /**
   * Tests the clearShots method of the ManualShots class.
   */
  @Test
  public void testClearShots() {
    int[] shots = {1, 2, 3, 4};
    manualShots.addShots(shots);
    assertEquals(2, manualShots.getShots().size());

    manualShots.clearShots();

    assertEquals(0, manualShots.getShots().size());
  }

}