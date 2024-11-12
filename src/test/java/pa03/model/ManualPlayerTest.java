package pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.ManualShots;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the ManualPlayer class.
 */
class ManualPlayerTest {
  private ManualPlayer manualPlayer;
  private GameBoard gameBoard;
  private List<Coord> carrierLocations = Arrays.asList(
      new Coord(0, 0),
      new Coord(0, 1),
      new Coord(0, 2),
      new Coord(0, 3),
      new Coord(0, 4),
      new Coord(0, 5)
  );
  private List<Coord> battleshipLocations = Arrays.asList(
      new Coord(1, 2),
      new Coord(2, 2),
      new Coord(3, 2),
      new Coord(4, 2),
      new Coord(5, 2)
  );
  private List<Coord> destroyerLocations = Arrays.asList(
      new Coord(1, 4),
      new Coord(2, 4),
      new Coord(3, 4),
      new Coord(4, 4)
  );
  private List<Coord> submarineLocations = Arrays.asList(
      new Coord(5, 3),
      new Coord(5, 4),
      new Coord(5, 5)
  );
  Ship carrier;
  Ship battleship;
  Ship destroyer;
  Ship submarine;
  private ManualShots manualShots;

  /**
   * Sets up the test environment
   */
  @BeforeEach
  public void setUp() {
    gameBoard = new GameBoard(6, 6);
    manualShots = new ManualShots();
    Random rand = new Random(123);
    manualPlayer = new ManualPlayer("manual", rand, gameBoard, manualShots);
    carrier = new Ship(ShipType.CARRIER, carrierLocations, Direction.VERTICAL);
    battleship = new Ship(ShipType.BATTLESHIP, battleshipLocations, Direction.HORIZONTAL);
    destroyer = new Ship(ShipType.DESTROYER, destroyerLocations, Direction.HORIZONTAL);
    submarine = new Ship(ShipType.SUBMARINE, submarineLocations, Direction.VERTICAL);
  }

  /**
   * Tests the name method of the ManualPlayer class.
   */
  @Test
  public void testName() {
    assertEquals("manual", manualPlayer.name());
  }

  /**
   * Tests the setup method of the ManualPlayer class.
   */
  @Test
  public void testSetup() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 2);

    List<Ship> ships = manualPlayer.setup(6, 6, specifications);
    assertEquals(5, ships.size());

    List<Coord> allLocations = new ArrayList<>();
    int carrierCount = 0;
    int battleshipCount = 0;
    int destroyerCount = 0;
    int submarineCount = 0;

    for (Ship ship : ships) {
      switch (ship.getLocations().size()) {
        case 6 -> carrierCount++;
        case 5 -> battleshipCount++;
        case 4 -> destroyerCount++;
        case 3 -> submarineCount++;
        default ->
            throw new IllegalStateException("Unexpected value: " + ship.getLocations().size());
      }

      for (Coord location : ship.getLocations()) {
        int x = location.getX();
        int y = location.getY();
        assertTrue(x >= 0 && x < 6 && y >= 0 && y < 6);

        assertFalse(allLocations.contains(location));
        allLocations.add(location);
      }
    }

    assertEquals(1, carrierCount);
    assertEquals(1, battleshipCount);
    assertEquals(1, destroyerCount);
    assertEquals(2, submarineCount);
  }

  /**
   * Tests the takeShots method of the ManualPlayer class.
   */
  @Test
  public void testTakeShots() {
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 2));
    shots.add(new Coord(3, 4));

    manualShots.addShots(new int[]{1, 2, 3, 4});

    List<Coord> result = manualPlayer.takeShots();

    List<Coord> expected = new ArrayList<>(shots);

    assertEquals(expected.get(0).getX(), result.get(0).getX());
    assertEquals(expected.get(0).getY(), result.get(0).getY());
    assertEquals(expected.get(1).getX(), result.get(1).getX());
    assertEquals(expected.get(1).getY(), result.get(1).getY());
  }

  /**
   * Tests the reportDamage method of the ManualPlayer class.
   */
  @Test
  public void testReportDamage() {
    gameBoard.placeShips(new ArrayList<>(List.of(carrier, battleship, destroyer, submarine)));
    Coord hit1 = new Coord(0, 0);
    Coord miss1 = new Coord(1, 0);
    Coord miss2 = new Coord(2, 0);
    Coord miss3 =  new Coord(3, 0);
    List<Coord> opponentShotsOnBoard = List.of(hit1, miss1, miss2, miss3);

    List<Coord> hits = manualPlayer.reportDamage(opponentShotsOnBoard);
    assertEquals(1, hits.size());
    assertTrue(hits.contains(hit1));

    String playerBoard =
            "\tH M M M _ _ \n"
            + "\tS _ _ _ _ _ \n"
            + "\tS S S S S S \n"
            + "\tS _ _ _ _ S \n"
            + "\tS S S S S S \n"
            + "\tS _ _ _ _ S \n";

    assertEquals(playerBoard, gameBoard.playerBoardToString());

  }

  /**
   * Tests the successfulHits method of the ManualPlayer class.
   */
  @Test
  public void testSuccessfullHits() {
    Coord hit1 = new Coord(0, 0);
    Coord hit2 = new Coord(1, 0);
    Coord hit3 = new Coord(2, 0);
    Coord hit4 =  new Coord(3, 0);
    List<Coord> hits = List.of(hit1, hit2, hit3, hit4);
    manualPlayer.successfulHits(hits);

    String opponentBoard =
        "\tH H H H _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n"
            + "\t_ _ _ _ _ _ \n";

    assertEquals(opponentBoard, gameBoard.opponentBoardToString());
  }

  /**
   * Tests the endGame method of the ManualPlayer class.
   */
  @Test
  public void testEndGame() {
    manualPlayer.endGame(GameResult.WIN, "All opponent ships sunk");
  }

}