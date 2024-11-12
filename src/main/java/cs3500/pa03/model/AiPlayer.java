package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The AiPlayer class represents an AI player in the game.
 * It extends the abstract AbstractPlayer class and implements the takeShots method.
 */
public class AiPlayer extends AbstractPlayer {
  private final List<Coord> shotsMade;

  /**
   * Instantiates an AiPlayer object with the specified name, random number generator, and game
   * board.
   *
   * @param name   the name of the AI player
   * @param random the random number generator for generating shots
   * @param board  the game board of the AI player
   */
  public AiPlayer(String name, Random random, GameBoardInterface board) {
    super(name, random, board);
    shotsMade = new ArrayList<>();
  }

  /**
   * Takes shots on the opponent's game board.
   * The number of shots taken is equal to the remaining ships count on the AI player's game board.
   *
   * @return a list of shot coordinates
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();

    for (int i = 0; i < board.getRemainingShipsCount(); i++) {
      if (shotsMade.size() == (board.getHeight() * board.getWidth())) {
        return shots;
      } else {
        Coord shot = getRandomShot();
        shots.add(shot);
      }
    }

    board.updateOpponentBoard(shots, Cell.M);
    return shots;
  }

  /**
   * Generates a random shot coordinate that hasn't been made before.
   *
   * @return a random shot coordinate
   */
  private Coord getRandomShot() {
    int x = random.nextInt(board.getWidth());
    int y = random.nextInt(board.getHeight());
    Coord shot = new Coord(x, y);

    for (Coord coord : shotsMade) {
      if (coord.getX() == x && coord.getY() == y) {
        return getRandomShot();
      }
    }

    shotsMade.add(shot);
    return shot;
  }

}
