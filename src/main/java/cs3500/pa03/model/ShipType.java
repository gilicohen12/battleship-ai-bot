package cs3500.pa03.model;

/**
 * The ShipType enum represents the types of ships in the game.
 * Each ship type has a specific size associated with it.
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int size;

  /**
   * Instantiates a ShipType with the specified size.
   *
   * @param size the size of the ship type
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Returns the size of the ship type.
   *
   * @return the size of the ship type
   */
  public int getSize() {
    return size;
  }

}
