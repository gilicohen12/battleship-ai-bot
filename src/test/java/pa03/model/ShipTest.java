package pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the Ship class.
 */
class ShipTest {

  /**
   * Tests the getLocations method of the Ship class.
   */
  @Test
  public void testGetLocations() {
    List<Coord> locations = new ArrayList<>();
    locations.add(new Coord(0, 0));
    locations.add(new Coord(0, 1));
    locations.add(new Coord(0, 2));

    Ship ship = new Ship(ShipType.SUBMARINE, locations, Direction.VERTICAL);

    assertEquals(locations, ship.getLocations());
  }

  /**
   * Tests the isSunk method of the Ship class.
   */
  @Test
  public void testIsSunk() {
    Ship ship = new Ship(ShipType.CARRIER, new ArrayList<>(), Direction.VERTICAL);
    assertFalse(ship.isSunk());

    ship.setSunk();
    assertTrue(ship.isSunk());
  }

}