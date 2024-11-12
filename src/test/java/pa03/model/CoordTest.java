package pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the Coord class.
 */
class CoordTest {

  /**
   * Tests the getters of the Coord class.
   */
  @Test
  public void testGetters() {
    Coord coord = new Coord(3, 5);

    assertEquals(3, coord.getX());
    assertEquals(5, coord.getY());
  }

}