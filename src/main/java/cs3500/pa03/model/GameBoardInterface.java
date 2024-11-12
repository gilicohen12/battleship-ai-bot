package cs3500.pa03.model;

import java.util.List;

/**
 * The GameBoardInterface interface defines the methods for a game board.
 * It provides functionality for placing ships, updating boards, retrieving board information,
 * and more.
 */
public interface GameBoardInterface {
  /**
   * Places the given ships on the game board.
   *
   * @param ships the list of ships to be placed
   */
  void placeShips(List<Ship> ships);

  /**
   * Updates the player's board with the given shots.
   *
   * @param shots the list of shots to be updated on the player's board
   */
  void updatePlayerBoard(List<Coord> shots);

  /**
   * Updates the status of the ships on the game board based on the player's board.
   * Marks ships as sunk if all their locations are hit.
   */
  void updateShips();

  /**
   * Updates the opponent's board with the given shots and the corresponding cell value.
   *
   * @param shots the list of shots to be updated on the opponent's board
   * @param cell the cell value to update the opponent's board with (M for miss, H for hit)
   */
  void updateOpponentBoard(List<Coord> shots, Cell cell);

  /**
   * Returns the count of remaining ships that are not yet sunk on the game board.
   *
   * @return the count of remaining ships
   */
  int getRemainingShipsCount();

  /**
   * Returns a string representation of the player's board.
   *
   * @return a string representing the player's board
   */
  String playerBoardToString();

  /**
   * Returns a string representation of the opponent's board.
   *
   * @return a string representing the opponent's board
   */
  String opponentBoardToString();

  /**
   * Returns the height of the game board.
   *
   * @return the height of the game board
   */
  int getHeight();

  /**
   * Returns the width of the game board.
   *
   * @return the width of the game board
   */
  int getWidth();

  /**
   * Returns the list of ships placed on the game board.
   *
   * @return the list of ships
   */
  List<Ship> getShips();
}
