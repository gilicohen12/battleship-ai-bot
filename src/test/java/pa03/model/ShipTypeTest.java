package pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ShipType;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the ShipType class.
 */
class ShipTypeTest {

  /**
   * Tests the getSize method of the ShipType enum for the CARRIER ship.
   * The test verifies that the getSize method returns the correct size for the CARRIER ship.
   */
  @Test
  public void testCarrierSize() {
    ShipType carrier = ShipType.CARRIER;
    int expectedSize = 6;
    assertEquals(expectedSize, carrier.getSize());
  }

  /**
   * Tests the getSize method of the ShipType enum for the BATTLESHIP ship.
   * The test verifies that the getSize method returns the correct size for the BATTLESHIP ship.
   */
  @Test
  public void testBattleshipSize() {
    ShipType battleship = ShipType.BATTLESHIP;
    int expectedSize = 5;
    assertEquals(expectedSize, battleship.getSize());
  }

  /**
   * Tests the getSize method of the ShipType enum for the DESTROYER ship.
   * The test verifies that the getSize method returns the correct size for the DESTROYER ship.
   */
  @Test
  public void testDestroyerSize() {
    ShipType destroyer = ShipType.DESTROYER;
    int expectedSize = 4;
    assertEquals(expectedSize, destroyer.getSize());
  }

  /**
   * Tests the getSize method of the ShipType enum for the SUBMARINE ship.
   * The test verifies that the getSize method returns the correct size for the SUBMARINE ship.
   */
  @Test
  public void testSubmarineSize() {
    ShipType submarine = ShipType.SUBMARINE;
    int expectedSize = 3;
    assertEquals(expectedSize, submarine.getSize());
  }
}
