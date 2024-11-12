package cs3500.pa03.model;

import java.util.List;
import java.util.Random;

/**
 * The ManualPlayer class represents a player who takes manual shots in the game.
 * It extends the AbstractPlayer abstract class and implements the Player interface.
 */
public class ManualPlayer extends AbstractPlayer {

  private final ManualShotsInterface manualShots;

  /**
   * Instantiates a ManualPlayer object with the specified name, random number generator,
   * game board, and manual shots interface.
   *
   * @param name         the name of the manual player
   * @param random       the random number generator to be used by the player
   * @param board        the game board interface
   * @param manualShots  the manual shots interface for obtaining the shots to be made by the
   *                     player
   */
  public ManualPlayer(String name, Random random, GameBoardInterface board,
                      ManualShotsInterface manualShots) {
    super(name, random, board);
    this.manualShots = manualShots;
  }

  /**
   * Takes the manual shots by updating the opponent's board with the shots obtained from the
   * manual shots interface.
   *
   * @return the list of shots made by the manual player
   */
  @Override
  public List<Coord> takeShots() {
    board.updateOpponentBoard(manualShots.getShots(), Cell.M);
    return manualShots.getShots();
  }

}
