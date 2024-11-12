package pa03.controller;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameBoardInterface;
import cs3500.pa03.model.Ship;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the GameBoardInterface interface used for testing purposes.
 * This class allows tracking of method invocations by appending the method names to a
 * StringBuilder object.
 */
public class MockGameBoard implements GameBoardInterface {
  private StringBuilder output;
  private int counter;

  /**
   * Instantiates a MockGameBoard object with the specified output and counter.
   *
   * @param output  the StringBuilder object to store the output messages
   * @param counter the counter value used for getRemainingShipsCount method
   */
  MockGameBoard(StringBuilder output, int counter) {
    this.output = output;
    this.counter = counter;
  }

  /**
   * Appends placeShips
   *
   * @param ships the list of Ship objects to be placed
   */
  public void placeShips(List<Ship> ships) {
    output.append("placeShips\n");
  }

  /**
   * Appends updatePlayerBoard
   *
   * @param shots the list of Coord objects representing the shots
   */
  public void updatePlayerBoard(List<Coord> shots) {
    output.append("updatePlayerBoard\n");
  }

  /**
   * Appends updateShips
   */
  public void updateShips() {
    output.append("updateShips\n");
  }

  /**
   * Appends updateOpponentBoard
   *
   * @param shots the list of Coord objects representing the shots
   * @param cell  the Cell value to update the opponent board with
   */
  public void updateOpponentBoard(List<Coord> shots, Cell cell) {
    output.append("updateOpponentBoard\n");
  }

  /**
   * Appends getRemainingShipsCount
   *
   * @return the count of remaining ships
   */
  public int getRemainingShipsCount() {
    output.append("getRemainingShipsCount\n");
    if (counter != 0) {
      counter--;
      return counter;
    }
    return 0;
  }

  /**
   * Appends playerBoardToString
   *
   * @return an empty string
   */
  public String playerBoardToString() {
    output.append("playerBoardToString\n");
    return "";
  }

  /**
   * Appends opponentBoardToString
   *
   * @return an empty string
   */
  public String opponentBoardToString() {
    output.append("opponentBoardToString\n");
    return "";
  }

  /**
   * Appends getHeight
   *
   * @return 1
   */
  public int getHeight() {
    output.append("getHeight\n");
    return 1;
  }

  /**
   * Appends getWidth
   *
   * @return 1
   */
  public int getWidth() {
    output.append("getWidth\n");
    return 1;
  }

  /**
   * Appends getShips
   *
   * @return an empty ArrayList
   */
  public List<Ship> getShips() {
    output.append("getShips\n");
    return new ArrayList<>();
  }

}
