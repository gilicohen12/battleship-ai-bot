package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameBoardInterface;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.ManualShots;
import cs3500.pa03.model.ManualShotsInterface;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.SalvoView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The SalvoController class is responsible for controlling the flow of the BattleSalvo game.
 * It coordinates the interactions between the players, game boards, shots, and the view.
 */
public class SalvoController {
  private Player ai;
  private Player manual;
  private final ManualShotsInterface ms;
  private GameBoardInterface aiBoard;
  private GameBoardInterface manualBoard;
  private final SalvoView view;
  private GameResult result;
  private int height;
  private int width;

  /**
   * Instantiates a SalvoController object with the specified view.
   *
   * @param view the view for displaying the game interface
   */
  public SalvoController(SalvoView view) {
    ms = new ManualShots();
    this.view = view;
  }

  /**
   * Instantiates a SalvoController object with the specified view, manual shots, players, and
   * game boards.
   * Used for testing
   *
   * @param view        the view for displaying the game interface
   * @param ms          the manual shots interface for managing manual shots
   * @param ai          the AI player
   * @param manual      the manual player
   * @param aiBoard     the game board for the AI player
   * @param manualBoard the game board for the manual player
   */
  public SalvoController(SalvoView view, ManualShotsInterface ms, Player ai, Player manual,
                         GameBoardInterface aiBoard, GameBoardInterface manualBoard) {
    this.view = view;
    this.ms = ms;
    this.ai = ai;
    this.manual = manual;
    this.aiBoard = aiBoard;
    this.manualBoard = manualBoard;
  }

  /**
   * Runs the game, controlling the flow of gameplay.
   *
   * @param mock indicates whether to run the game with mock objects or not
   */
  public void runGame(boolean mock) {
    view.displayWelcomeMessage();

    int[] dimensions = validateDimensions(view.promptForDimensions());
    height = dimensions[0];
    width = dimensions[1];
    if (!mock) {
      this.initialize(height, width);
    }

    int maxFleet = Math.min(width, height);
    Map<ShipType, Integer> fleet = validateFleet(view.promptForFleet(maxFleet), maxFleet);

    manual.setup(height, width, fleet);
    ai.setup(height, width, fleet);

    boolean gameOver = false;

    while (!gameOver) {
      gameOver = playRound();
    }

    view.displayEndGame(result);
  }

  /**
   * Plays a round of the game, including taking shots, reporting damage, and determining game
   * over condition.
   *
   * @return true if the game is over, false otherwise
   */
  private boolean playRound() {
    ms.clearShots();
    ms.addShots(validateShots(view.promptForShots(manualBoard,
        manualBoard.getRemainingShipsCount()), height, width));
    List<Coord> manualShots = manual.takeShots();
    List<Coord> aiShots = ai.takeShots();
    List<Coord> successfulManualShots = ai.reportDamage(manualShots);
    List<Coord> successfulAiShots = manual.reportDamage(aiShots);
    manual.successfulHits(successfulManualShots);
    ai.successfulHits(successfulAiShots);

    if (manualBoard.getRemainingShipsCount() == 0 || aiBoard.getRemainingShipsCount() == 0) {
      setGameResult();
      return true;
    }
    return false;
  }

  /**
   * Sets the game result based on the remaining ships on the game boards.
   */
  private void setGameResult() {
    if (manualBoard.getRemainingShipsCount() == 0 && aiBoard.getRemainingShipsCount() == 0) {
      result = GameResult.DRAW;
    } else if (manualBoard.getRemainingShipsCount() == 0) {
      result = GameResult.LOSE;
    } else if (aiBoard.getRemainingShipsCount() == 0) {
      result = GameResult.WIN;
    }
  }

  /**
   * Validates the dimensions of the game board.
   *
   * @param dimensions the dimensions provided by the user
   * @return the validated dimensions
   */
  private int[] validateDimensions(int[] dimensions) {
    int h = dimensions[0];
    int w = dimensions[1];

    while (w < 6 || w > 15 || h < 6 || h > 15) {
      dimensions = view.promptForCorrectDimensions();
      h = dimensions[0];
      w = dimensions[1];
    }

    return dimensions;
  }

  /**
   * Validates the fleet sizes provided by the user.
   *
   * @param sizes      the fleet sizes provided by the user
   * @param fleetSize  the maximum fleet size
   * @return a map containing the ship types and their respective sizes
   */
  private Map<ShipType, Integer> validateFleet(int[] sizes, int fleetSize) {
    int sum = 0;
    for (int size : sizes) {
      if (size < 1) {
        return validateFleet(view.promptForCorrectFleet(fleetSize), fleetSize);
      }
      sum += size;
    }

    if (sum > fleetSize) {
      return validateFleet(view.promptForCorrectFleet(fleetSize), fleetSize);
    }

    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, sizes[0]);
    fleet.put(ShipType.BATTLESHIP, sizes[1]);
    fleet.put(ShipType.DESTROYER, sizes[2]);
    fleet.put(ShipType.SUBMARINE, sizes[3]);

    return fleet;
  }

  /**
   * Validates the shot coordinates provided by the user.
   *
   * @param shots  the shot coordinates provided by the user
   * @param height the height of the game board
   * @param width  the width of the game board
   * @return the validated shot coordinates
   */
  private int[] validateShots(int[] shots, int height, int width) {
    for (int i = 0; i < shots.length; i = i + 2) {
      int x = shots[i];
      int y = shots[i + 1];

      if (x < 0 || x >= width || y < 0 || y >= height) {
        return validateShots(view.promptForShots(manualBoard,
            manualBoard.getRemainingShipsCount()), height, width);
      }
    }

    return shots;
  }

  /**
   * Initializes the game boards and players.
   *
   * @param height the height of the game board
   * @param width  the width of the game board
   */
  private void initialize(int height, int width) {
    aiBoard = new GameBoard(height, width);
    manualBoard = new GameBoard(height, width);

    ai = new AiPlayer("ai", new Random(), aiBoard);
    manual = new ManualPlayer("manual", new Random(), manualBoard, ms);
  }

}
