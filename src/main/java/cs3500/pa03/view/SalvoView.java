package cs3500.pa03.view;

import cs3500.pa03.model.GameBoardInterface;
import cs3500.pa03.model.GameResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The SalvoView class handles the user interface for the BattleSalvo game.
 * It provides methods to display messages, prompt for user input, and handle input validation.
 */
public class SalvoView {
  private final Readable input;
  private final Appendable output;

  /**
   * Instantiates a SalvoView object with the specified input and output streams.
   *
   * @param input  the input stream to read user input from
   * @param output the output stream to write messages to
   */
  public SalvoView(Readable input, Appendable output) {
    this.input = input;
    this.output = output;
  }

  /**
   * Displays a welcome message to the user.
   */
  public void displayWelcomeMessage() {
    printLine("Hello! Welcome to the OOD BattleSalvo Game!");
  }

  /**
   * Prompts the user to enter the game board dimensions and returns the entered values as an array.
   *
   * @return an array containing the height and width entered by the user
   */
  public int[] promptForDimensions() {
    printLine("Please enter a valid height and width below:");
    printBreakLine();

    try {
      return readIntegers(2);
    } catch (IllegalArgumentException e) {
      return promptForCorrectDimensions();
    }

  }

  /**
   * Displays an error message for incorrect dimensions and prompts the user to enter correct
   * dimensions.
   *
   * @return an array containing the corrected height and width entered by the user
   */
  public int[] promptForCorrectDimensions() {
    printLine("Uh Oh! You've entered invalid dimensions. Please remember that the height\n"
              + "and width of the game must be in the range (6, 15), inclusive. Try again!");
    printBreakLine();

    try {
      return readIntegers(2);
    } catch (IllegalArgumentException e) {
      return promptForCorrectDimensions();
    }
  }

  /**
   * Prompts the user to enter the fleet positions and returns the entered values as an array.
   *
   * @param expectedSize the expected size of the fleet
   * @return an array containing the fleet positions entered by the user
   */
  public int[] promptForFleet(int expectedSize) {
    printLine("Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    printLine("Remember, your fleet may not exceed size " + expectedSize + ".");
    printBreakLine();

    try {
      return readIntegers(4);
    } catch (IllegalArgumentException e) {
      return promptForCorrectFleet(expectedSize);
    }
  }

  /**
   * Displays an error message for an incorrect fleet and prompts the user to enter a correct fleet.
   *
   * @param expectedSize the expected size of the fleet
   * @return an array containing the corrected fleet positions entered by the user
   */
  public int[] promptForCorrectFleet(int expectedSize) {
    printLine("Uh Oh! You've entered an invalid fleet. Please try again!");
    return promptForFleet(expectedSize);

  }

  /**
   * Displays the opponents and players board from the given GameBoard
   *
   * @param board    the game board to display
   */
  private void displayBoard(GameBoardInterface board) {
    printLine("Opponent Board Data:");
    printLine(board.opponentBoardToString());
    printLine("Your Board:");
    printLine(board.playerBoardToString());
  }

  /**
   * Prompts the user to enter shot coordinates and returns the entered values as an array.
   *
   * @param board    the game board to display
   * @param shotNum  the number of shots to prompt for
   * @return an array containing the shot coordinates entered by the user
   */
  public int[] promptForShots(GameBoardInterface board, int shotNum) {
    printLine();
    displayBoard(board);
    printLine("Please Enter " + shotNum + " shots:");
    printBreakLine();

    try {
      return readIntegers(shotNum * 2);
    } catch (IllegalArgumentException e) {
      return promptForShots(board, shotNum);
    }
  }

  /**
   * Displays the end game message based on the game result.
   *
   * @param result the result of the game
   */
  public void displayEndGame(GameResult result) {
    switch (result) {
      case WIN -> printLine("Congratulations! You won the game!");
      case LOSE -> printLine("Game over! The AI won the game.");
      case DRAW -> printLine("It's a draw! The game ended in a tie.");
    }
  }

  /**
   * Reads a specified number of integers from the input.
   *
   * @param expectedCount the expected number of integers to read
   * @return an array of integers read from the input
   * @throws IllegalArgumentException if the input does not contain the expected number of integers
   */
  private int[] readIntegers(int expectedCount) {
    Scanner sc = new Scanner(input);
    List<Integer> integers = new ArrayList<>();

    while (integers.size() < expectedCount && sc.hasNextLine()) {
      String line = sc.nextLine();
      Scanner lineScanner = new Scanner(line);
      while (lineScanner.hasNext()) {
        if (lineScanner.hasNextInt()) {
          integers.add(lineScanner.nextInt());
        } else {
          throw new IllegalArgumentException();
        }
      }
    }

    if (integers.size() != expectedCount) {
      throw new IllegalArgumentException();
    }

    return integers.stream().mapToInt(Integer::intValue).toArray();
  }

  /**
   * Displays a message without a line break.
   *
   * @param message the message to be displayed
   */
  private void print(String message) {
    try {
      output.append(message);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  /**
   * Displays a message with a line break.
   *
   * @param line the line to be displayed
   */
  private void printLine(String line) {
    print(line + System.lineSeparator());
  }

  /**
   * Displays an empty line.
   */
  private void printLine() {
    print(System.lineSeparator());
  }

  /**
   * Displays a break line to separate sections.
   */
  private void printBreakLine() {
    printLine("------------------------------------------------------");
  }

}
