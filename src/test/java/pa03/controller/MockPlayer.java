package pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A mock implementation of the Player interface used for testing purposes.
 * This class allows tracking of method invocations by appending the method names to a
 * StringBuilder object.
 */
public class MockPlayer implements Player {
  StringBuilder output;

  /**
   * Instantiates a MockPlayer object with the specified output StringBuilder.
   *
   * @param output the StringBuilder object to store the output messages
   */
  MockPlayer(StringBuilder output) {
    this.output = output;
  }

  /**
   * Appends name
   *
   * @return an empty string
   */
  public String name() {
    output.append("name\n");
    return "";
  }

  /**
   * Appends setup
   *
   * @param height         the height of the game board
   * @param width          the width of the game board
   * @param specifications a map of ship types to their corresponding quantities
   * @return an empty ArrayList
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    output.append("setup\n");
    return new ArrayList<>();
  }

  /**
   * Appends takeShots
   *
   * @return an empty ArrayList
   */
  public List<Coord> takeShots() {
    output.append("takeShots\n");
    return new ArrayList<>();
  }

  /**
   * Appends reportDamage
   *
   * @param opponentShotsOnBoard the list of Coord objects representing the opponent's shots on the
   *                            player's board
   * @return an empty ArrayList
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    output.append("reportDamage\n");
    return new ArrayList<>();
  }

  /**
   * Appends successfulHits
   *
   * @param shotsThatHitOpponentShips the list of Coord objects representing the successful hits
   *                                  on the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    output.append("successfulHits\n");
  }

  /**
   * Appends endGame
   *
   * @param result the GameResult enum representing the result of the game (WIN, LOSE, or DRAW)
   * @param reason the reason for the end of the game
   */
  public void endGame(GameResult result, String reason) {
    output.append("endGame\n");
  }
}
