package pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the AiPlayer class.
 */
class AiPlayerTest {
  private GameBoard board;
  private AiPlayer aiPlayer;
  private Ship carrier;
  private Ship battleship;
  private Ship destroyer;
  private Ship submarine;
  private final List<Coord> carrierLocations = Arrays.asList(new Coord(0, 0),
      new Coord(0, 1), new Coord(0, 2), new Coord(0, 3), new Coord(0, 4),
      new Coord(0, 5));

  private final List<Coord> battleshipLocations = Arrays.asList(new Coord(1, 2),
      new Coord(2, 2), new Coord(3, 2), new Coord(4, 2), new Coord(5, 2));

  private final List<Coord> destroyerLocations = Arrays.asList(new Coord(1, 4),
      new Coord(2, 4), new Coord(3, 4), new Coord(4, 4));

  private final List<Coord> submarineLocations = Arrays.asList(new Coord(5, 3),
      new Coord(5, 4), new Coord(5, 5));

  /**
   * Sets up the test environment by creating ships and a game board.
   */
  @BeforeEach
  public void setUp() {
    carrier = new Ship(ShipType.CARRIER, carrierLocations, Direction.VERTICAL);
    battleship = new Ship(ShipType.BATTLESHIP, battleshipLocations, Direction.HORIZONTAL);
    destroyer = new Ship(ShipType.DESTROYER, destroyerLocations, Direction.HORIZONTAL);
    submarine = new Ship(ShipType.SUBMARINE, submarineLocations, Direction.VERTICAL);

    board = new GameBoard(6, 6);
    aiPlayer = new AiPlayer("AI Player", new Random(123), board);

    board.placeShips(new ArrayList<>(List.of(carrier, battleship, destroyer, submarine)));

  }

  /**
   * Tests the takeShots method of the AiPlayer class.
   */
  @Test
  public void testTakeShots() {
    List<Coord> shots = aiPlayer.takeShots();

    assertEquals(board.getRemainingShipsCount(), shots.size());

    for (Coord shot : shots) {
      assertFalse(shot.getX() < 0 || shot.getX() >= board.getWidth()
          || shot.getY() < 0 || shot.getY() >= board.getHeight());
    }

    String opponentBoard =
              "\t_ _ _ _ _ _ \n"
            + "\tM _ _ _ _ _ \n"
            + "\t_ _ M _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ M M _ _ \n";

    assertEquals(opponentBoard, board.opponentBoardToString());

  }


  /**
   * Tests the successfulHits method of the AiPlayer class.
   */
  @Test
  public void testSuccessfullHits() {
    Coord hit1 = new Coord(0, 0);
    Coord hit2 = new Coord(1, 0);
    Coord hit3 = new Coord(2, 0);
    Coord hit4 =  new Coord(3, 0);
    List<Coord> hits = List.of(hit1, hit2, hit3, hit4);
    aiPlayer.successfulHits(hits);

    String opponentBoard =
        "\tH H H H _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n";

    assertEquals(opponentBoard, board.opponentBoardToString());
  }

}