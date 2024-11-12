package cs3500.pa03.model;

import java.util.Arrays;
import java.util.List;

/**
 * The GameBoard class represents a game board for placing ships and tracking shots.
 * It contains player board and opponent board, as well as the ships placed on the board.
 */
public class GameBoard implements GameBoardInterface {
  private final int height;
  private final int width;
  private final Cell[][] playerBoard;
  private final Cell[][] opponentBoard;
  private List<Ship> ships;

  /**
   * Instantiates a new GameBoard object with the specified height and width.
   *
   * @param height the height of the game board
   * @param width the width of the game board
   */
  public GameBoard(int height, int width) {
    this.height = height;
    this.width = width;
    this.playerBoard = new Cell[height][width];
    this.opponentBoard = new Cell[height][width];

    for (Cell[] row : playerBoard) {
      Arrays.fill(row, Cell.O);
    }

    for (Cell[] row : opponentBoard) {
      Arrays.fill(row, Cell.O);
    }

  }

  /**
   * Places the given ships on the game board.
   *
   * @param ships the list of ships to be placed
   */
  public void placeShips(List<Ship> ships) {
    for (Ship ship : ships) {
      List<Coord> locations = ship.getLocations();
      for (Coord coord : locations) {
        int x = coord.getX();
        int y = coord.getY();
        playerBoard[y][x] = Cell.S;
      }
    }

    this.ships = ships;
  }

  /**
   * Updates the player's board with the given shots.
   *
   * @param shots the list of shots to be updated on the player's board
   */
  public void updatePlayerBoard(List<Coord> shots) {
    for (Coord shot : shots) {
      int x = shot.getX();
      int y = shot.getY();

      if (playerBoard[y][x] == Cell.S) {
        playerBoard[y][x] = Cell.H;
      } else {
        playerBoard[y][x] = Cell.M;
      }
    }

  }

  /**
   * Updates the status of the ships on the game board based on the player's board.
   * Marks ships as sunk if all their locations are hit.
   */
  public void updateShips() {
    for (Ship ship : ships) {
      boolean allLocationsHit = true;

      for (Coord location : ship.getLocations()) {
        int x = location.getX();
        int y = location.getY();

        if (playerBoard[y][x] != Cell.H) {
          allLocationsHit = false;
          break;
        }
      }

      if (allLocationsHit) {
        ship.setSunk();
      }
    }
  }

  /**
   * Updates the opponent's board with the given shots and the corresponding cell value.
   *
   * @param shots the list of shots to be updated on the opponent's board
   * @param cell the cell value to update the opponent's board with (M for miss, H for hit)
   */
  public void updateOpponentBoard(List<Coord> shots, Cell cell) {
    for (Coord shot : shots) {
      int x = shot.getX();
      int y = shot.getY();
      opponentBoard[y][x] = cell;
    }
  }


  /**
   * Returns the count of remaining ships that are not yet sunk on the game board.
   *
   * @return the count of remaining ships
   */
  public int getRemainingShipsCount() {
    int count = 0;
    for (Ship ship : ships) {
      if (!ship.isSunk()) {
        count++;
      }
    }
    return count;
  }

  /**
   * Returns a string representation of the player's board.
   *
   * @return a string representing the player's board
   */
  public String playerBoardToString() {
    return boardToString(playerBoard);
  }

  /**
   * Returns a string representation of the opponent's board.
   *
   * @return a string representing the opponent's board
   */
  public String opponentBoardToString() {
    return boardToString(opponentBoard);
  }

  /**
   * Returns a string representation of the given cells.
   *
   * @param cells board of cells
   * @return a string representing the cells
   */
  private String boardToString(Cell[][] cells) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < height; i++) {
      sb.append("\t");
      for (int j = 0; j < width; j++) {
        if (cells[i][j] == Cell.O) {
          sb.append("_").append(" ");
        } else {
          sb.append(cells[i][j].toString()).append(" ");
        }
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Returns the height of the game board.
   *
   * @return the height of the game board
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the width of the game board.
   *
   * @return the width of the game board
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the list of ships placed on the game board.
   *
   * @return the list of ships
   */
  public List<Ship> getShips() {
    return ships;
  }

}
