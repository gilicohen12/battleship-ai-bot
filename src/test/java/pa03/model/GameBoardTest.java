package pa03.model;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the GameBoard class.
 */
class GameBoardTest {
  GameBoard board;
  Ship carrier;
  Ship battleship;
  Ship destroyer;
  Ship submarine;

  /**
   * Sets up the test environment by creating ships and a game board.
   */
  @BeforeEach
  public void setUp() {
    board = new GameBoard(6, 6);
    List<Coord> carrierLocations = Arrays.asList(
        new Coord(0, 0),
        new Coord(0, 1),
        new Coord(0, 2),
        new Coord(0, 3),
        new Coord(0, 4),
        new Coord(0, 5)
    );

    List<Coord> battleshipLocations = Arrays.asList(
        new Coord(1, 2),
        new Coord(2, 2),
        new Coord(3, 2),
        new Coord(4, 2),
        new Coord(5, 2)
    );

    List<Coord> destroyerLocations = Arrays.asList(
        new Coord(1, 4),
        new Coord(2, 4),
        new Coord(3, 4),
        new Coord(4, 4)
    );

    carrier = new Ship(ShipType.CARRIER, carrierLocations, Direction.VERTICAL);
    battleship = new Ship(ShipType.BATTLESHIP, battleshipLocations, Direction.HORIZONTAL);
    destroyer = new Ship(ShipType.DESTROYER, destroyerLocations, Direction.HORIZONTAL);

    List<Coord> submarineLocations = Arrays.asList(
        new Coord(5, 3),
        new Coord(5, 4),
        new Coord(5, 5)
    );

    submarine = new Ship(ShipType.SUBMARINE, submarineLocations, Direction.VERTICAL);

    board.placeShips(new ArrayList<>(List.of(carrier, battleship, destroyer, submarine)));

  }

  /**
   * Tests the getHeight method of the GameBoard class.
   */
  @Test
  public void testGetHeight() {
    assertEquals(6, board.getHeight());
  }

  /**
   * Tests the getWidth method of the GameBoard class.
   */
  @Test
  public void testGetWidth() {
    assertEquals(6, board.getWidth());
  }

  /**
   * Tests the getShips method of the GameBoard class.
   */
  @Test
  public void testGetShips() {
    assertArrayEquals(new ArrayList<>(List.of(carrier, battleship, destroyer, submarine)).toArray(),
        board.getShips().toArray());
  }

  /**
   * Tests the getRemainingShipsCount method of the GameBoard class.
   */
  @Test
  public void testGetRemainingShipsCount() {
    assertEquals(4, board.getRemainingShipsCount());
    carrier.setSunk();
    assertEquals(3, board.getRemainingShipsCount());
  }


  /**
   * Tests the updateBoards method of the GameBoard class.
   */
  @Test
  public void testUpdateBoards() {
    board.updatePlayerBoard(submarine.getLocations());
    String playerBoard =
          "\tS _ _ _ _ _ \n"
        + "\tS _ _ _ _ _ \n"
        + "\tS S S S S S \n"
        + "\tS _ _ _ _ H \n"
        + "\tS S S S S H \n"
        + "\tS _ _ _ _ H \n";
    assertEquals(playerBoard, board.playerBoardToString());

    board.updatePlayerBoard(new ArrayList<>(List.of(new Coord(5, 0))));
    playerBoard =
        "\tS _ _ _ _ M \n"
            + "\tS _ _ _ _ _ \n"
            + "\tS S S S S S \n"
            + "\tS _ _ _ _ H \n"
            + "\tS S S S S H \n"
            + "\tS _ _ _ _ H \n";
    assertEquals(playerBoard, board.playerBoardToString());

    board.updateOpponentBoard(submarine.getLocations(), Cell.M);
    String opponentBoard =
              "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ M \n"
            + "\t_ _ _ _ _ M \n"
            + "\t_ _ _ _ _ M \n";
    assertEquals(opponentBoard, board.opponentBoardToString());

    board.updateShips();
    assertTrue(submarine.isSunk());
    assertEquals(3, board.getRemainingShipsCount());
  }

}