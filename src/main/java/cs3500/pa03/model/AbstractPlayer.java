package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The AbstractPlayer abstract class provides a base implementation for a player in the game.
 * It implements common functionality and defines abstract methods to be implemented by subclasses.
 */
public abstract class AbstractPlayer implements Player {
  protected String name;
  protected Random random;
  protected GameBoardInterface board;

  /**
   * Instantiates an AbstractPlayer object with the specified name, random number generator, and
   * game board.
   *
   * @param name   the name of the player
   * @param random the random number generator for generating ship locations and shots
   * @param board  the game board of the player
   */
  AbstractPlayer(String name, Random random, GameBoardInterface board) {
    this.name = name;
    this.random = random;
    this.board = board;
  }

  /**
   * Returns the name of the player.
   *
   * @return the player's name
   */
  public String name() {
    return name;
  }

  /**
   * Sets up the player's ships on the game board based on the given height, width, and ship
   * specifications.
   *
   * @param height         the height of the game board
   * @param width          the width of the game board
   * @param specifications a map of ship types and their corresponding count
   * @return a list of the placed ships
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> ships = new ArrayList<>();

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      ShipType shipType = entry.getKey();
      int count = entry.getValue();

      for (int i = 0; i < count; i++) {
        Ship ship = generateShip(shipType, height, width, ships);
        ships.add(ship);
      }
    }

    board.placeShips(ships);
    return ships;
  }

  /**
   * Generates a ship of the specified type with random location and orientation.
   *
   * @param type           the type of the ship
   * @param height         the height of the game board
   * @param width          the width of the game board
   * @param existingShips  a list of existing ships on the board
   * @return a generated ship with valid location and orientation
   */
  private Ship generateShip(ShipType type, int height, int width, List<Ship> existingShips) {
    List<Coord> locations = new ArrayList<>();

    int x = random.nextInt(width);
    int y = random.nextInt(height);
    boolean vertical = random.nextBoolean();
    Direction direction;
    if (vertical) {
      direction = Direction.VERTICAL;
    } else {
      direction = Direction.HORIZONTAL;
    }

    while (locations.size() < type.getSize()) {
      Coord coord = new Coord(x, y);

      if (outOfBounds(coord, height, width) || overlaps(coord, existingShips)) {
        return generateShip(type, height, width, existingShips);
      }
      locations.add(coord);

      if (vertical) {
        y++;
      } else {
        x++;
      }
    }

    return new Ship(type, locations, direction);
  }

  /**
   * Checks if the given coordinate is out of bounds of the game board.
   *
   * @param coord  the coordinate to check
   * @param height the height of the game board
   * @param width  the width of the game board
   * @return true if the coordinate is out of bounds, false otherwise
   */
  private boolean outOfBounds(Coord coord, int height, int width) {
    int x = coord.getX();
    int y = coord.getY();

    return x < 0 || x >= width || y < 0 || y >= height;
  }

  /**
   * Checks if the given coordinate overlaps with any existing ships on the game board.
   *
   * @param coord the coordinate to check
   * @param existingShips a list of existing ships on the board
   * @return true if the coordinate overlaps with an existing ship, false otherwise
   */
  private boolean overlaps(Coord coord, List<Ship> existingShips) {
    for (Ship ship : existingShips) {
      for (Coord location : ship.getLocations()) {
        if (location.getX() == coord.getX() && location.getY() == coord.getY()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Takes shots on the opponent's game board.
   * This method needs to be implemented by subclasses.
   *
   * @return a list of shot coordinates
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Reports the damage caused by the opponent's shots on the player's ships.
   *
   * @param opponentShotsOnBoard a list of opponent's shots on the player's game board
   * @return a list of hit coordinates
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hits = new ArrayList<>();

    for (Coord shot : opponentShotsOnBoard) {
      for (Ship ship : board.getShips()) {
        for (Coord location : ship.getLocations()) {
          int x = location.getX();
          int y = location.getY();
          if (x == shot.getX() && y == shot.getY()) {
            hits.add(shot);
          }
        }
      }
    }

    board.updatePlayerBoard(opponentShotsOnBoard);
    board.updateShips();
    return hits;
  }

  /**
   * Updates the opponent's game board with the successful hits made by this player.
   *
   * @param shotsThatHitOpponentShips a list of shots that hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    board.updateOpponentBoard(shotsThatHitOpponentShips, Cell.H);
  }

  /**
   * Handles the end of the game and any necessary actions.
   *
   * @param result the result of the game
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}
