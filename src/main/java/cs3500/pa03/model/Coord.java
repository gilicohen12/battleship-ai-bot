package cs3500.pa03.model;

/**
 * The Coord class represents a coordinate on a game board.
 * It encapsulates the x and y values of a coordinate.
 */
public class Coord {
  private final int x;
  private final int y;

  /**
   * Instantiates a Coord object with the specified x and y coordinates.
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the x-coordinate of this Coord object.
   *
   * @return the x-coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the y-coordinate of this Coord object.
   *
   * @return the y-coordinate
   */
  public int getY() {
    return y;
  }
}
