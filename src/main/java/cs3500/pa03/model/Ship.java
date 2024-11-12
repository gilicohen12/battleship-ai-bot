package cs3500.pa03.model;

import java.util.List;

/**
 * The Ship class represents a ship in the game.
 * It holds information about the ship's type, locations, sinking status, and orientation.
 */
public class Ship {
  private final ShipType type;
  private final List<Coord> locations;
  private boolean sunk;
  private final Direction direction;

  /**
   * Instantiates a new Ship object with the specified ship type, locations, and orientation.
   *
   * @param type      the ship type
   * @param locations the list of coordinates representing the ship's locations
   * @param direction  direction of the ship's orientation
   */
  public Ship(ShipType type, List<Coord> locations, Direction direction) {
    this.type = type;
    this.locations = locations;
    this.sunk = false;
    this.direction = direction;
  }

  /**
   * Returns the list of coordinates representing the ship's locations.
   *
   * @return the list of ship locations
   */
  public List<Coord> getLocations() {
    return locations;
  }

  /**
   * Checks if the ship is sunk.
   *
   * @return {@code true} if the ship is sunk, {@code false} otherwise
   */
  public boolean isSunk() {
    return sunk;
  }

  /**
   * Sets the ship as sunk.
   */
  public void setSunk() {
    sunk = true;
  }

  public ShipType getType() {
    return type;
  }

  public Direction getDirection() {
    return direction;
  }
}
