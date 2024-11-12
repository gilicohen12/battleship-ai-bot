package pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.SalvoView;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the SalvoView class.
 */
class SalvoViewTest {
  private MockReadable input;
  private StringBuilder output;
  private SalvoView salvoView;
  private GameBoard board;
  private List<Coord> locations;

  /**
   * Sets up the test environment
   */
  @BeforeEach
  public void setup() {
    input = new MockReadable(new ArrayList<>());
    output = new StringBuilder();
    salvoView = new SalvoView(input, output);
    board = new GameBoard(6, 6);
    locations = new ArrayList<>(List.of(new Coord(0, 0), new Coord(0, 1),
        new Coord(0, 2)));

    board.placeShips(new ArrayList<>(List.of(new Ship(ShipType.SUBMARINE, locations,
        Direction.VERTICAL))));
  }

  /**
   * Tests the displayWelcomeMessage method of the SalvoView class.
   * Verifies that the welcome message is correctly displayed.
   */
  @Test
  public void testDisplayWelcomeMessage() {
    salvoView.displayWelcomeMessage();
    assertEquals("Hello! Welcome to the OOD BattleSalvo Game!\n", output.toString());
  }

  /**
   * Tests the promptForDimensions method of the SalvoView class with valid input.
   * Verifies that the method prompts for dimensions and returns the correct dimensions.
   */
  @Test
  public void testPromptForDimensions_ValidInput() {
    input.setInput(new ArrayList<>(List.of("6 10\n")));
    int[] dimensions = salvoView.promptForDimensions();
    assertEquals("Please enter a valid height and width below:\n"
                  + "------------------------------------------------------\n", output.toString());
    assertEquals(6, dimensions[0]);
    assertEquals(10, dimensions[1]);
  }

  /**
   * Tests the promptForDimensions method of the SalvoView class with invalid input followed by
   * valid input.
   * Verifies that the method displays an error message for invalid input and returns the correct
   * dimensions for valid input.
   */
  @Test
  public void testPromptForDimensions_InvalidInputThenValidInput() {
    input.setInput(new ArrayList<>(List.of("abc 7\n", "6 10")));
    int[] dimensions = salvoView.promptForDimensions();
    assertEquals("Please enter a valid height and width below:\n"
        + "------------------------------------------------------\n"
        + "Uh Oh! You've entered invalid dimensions. Please remember that the height\n"
        + "and width of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "------------------------------------------------------\n", output.toString());
    assertEquals(6, dimensions[0]);
    assertEquals(10, dimensions[1]);
  }

  /**
   * Tests the promptForCorrectDimensions method of the SalvoView class with valid input.
   * Verifies that the method prompts for correct dimensions and returns the correct
   * dimensions.
   */
  @Test
  public void testPromptForCorrectDimensions_ValidInput() {
    input.setInput(new ArrayList<>(List.of("8\n", "12\n")));
    int[] dimensions = salvoView.promptForCorrectDimensions();
    assertEquals("Uh Oh! You've entered invalid dimensions. "
        + "Please remember that the height\n"
        + "and width of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "------------------------------------------------------\n", output.toString());
    assertEquals(8, dimensions[0]);
    assertEquals(12, dimensions[1]);
  }

  /**
   * Tests the promptForCorrectDimensions method of the SalvoView class with invalid input
   * followed by valid input.
   * Verifies that the method displays an error message for invalid input and returns the
   * correct dimensions for valid input.
   */
  @Test
  public void testPromptForCorrectDimensions_InvalidInputThenValidInput() {
    input.setInput(new ArrayList<>(List.of("abc\n", "6 8\n")));
    int[] dimensions = salvoView.promptForCorrectDimensions();
    assertEquals("Uh Oh! You've entered invalid dimensions. "
        + "Please remember that the height\n"
        + "and width of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "------------------------------------------------------\n"
        + "Uh Oh! You've entered invalid dimensions. Please remember that the height\n"
        + "and width of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "------------------------------------------------------\n", output.toString());
    assertEquals(6, dimensions[0]);
    assertEquals(8, dimensions[1]);
  }

  /**
   * Tests the promptForFleet method of the SalvoView class with valid input.
   * Verifies that the method prompts for the fleet and returns the correct fleet.
   */
  @Test
  public void testPromptForFleet_ValidInput() {
    input.setInput(new ArrayList<>(List.of("1\n", "2 3\n", "4\n")));
    int[] fleet = salvoView.promptForFleet(10);
    assertEquals("Please enter your fleet in the order [Carrier, "
        + "Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 10.\n"
        + "------------------------------------------------------\n", output.toString());
    assertEquals(1, fleet[0]);
    assertEquals(2, fleet[1]);
    assertEquals(3, fleet[2]);
    assertEquals(4, fleet[3]);
  }

  /**
   * Tests the promptForFleet method of the SalvoView class with invalid input followed by valid
   * input.
   * Verifies that the method displays an error message for invalid input and returns the correct
   * fleet for valid input.
   */
  @Test
  public void testPromptForFleet_InvalidInputThenValidInput() {
    input.setInput(new ArrayList<>(List.of("abc 2\n", " 1 2\n", "3\n", "4\n")));
    int[] fleet = salvoView.promptForFleet(10);
    assertEquals("Please enter your fleet in the order [Carrier, "
        + "Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 10.\n"
        + "------------------------------------------------------\n"
        + "Uh Oh! You've entered an invalid fleet. Please try again!\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 10.\n"
        + "------------------------------------------------------\n", output.toString());
    assertEquals(1, fleet[0]);
    assertEquals(2, fleet[1]);
    assertEquals(3, fleet[2]);
    assertEquals(4, fleet[3]);
  }

  /**
   * Tests the promptForCorrectFleet method of the SalvoView class with valid input.
   * Verifies that the method prompts for correct fleet and returns the correct fleet.
   */
  @Test
  public void testPromptForCorrectFleet_ValidInput() {
    input.setInput(new ArrayList<>(List.of("1 2 3 4\n")));
    int[] fleet = salvoView.promptForCorrectFleet(10);
    assertEquals("Uh Oh! You've entered an invalid fleet. Please try again!\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 10.\n"
        + "------------------------------------------------------\n", output.toString());
    assertEquals(1, fleet[0]);
    assertEquals(2, fleet[1]);
    assertEquals(3, fleet[2]);
    assertEquals(4, fleet[3]);
  }


  /**
   * Tests the promptForCorrectFleet method of the SalvoView class with invalid input followed
   * by valid input.
   * Verifies that the method displays an error message for invalid input and returns the
   * correct fleet for valid input.
   */
  @Test
  public void testPromptForCorrectFleet_InvalidInputThenValidInput() {
    input.setInput(new ArrayList<>(List.of("abc\n", "1 2\n", "3\n", "4\n")));
    int[] fleet = salvoView.promptForCorrectFleet(10);
    assertEquals("Uh Oh! You've entered an invalid fleet. Please try again!\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 10.\n"
        + "------------------------------------------------------\n"
        + "Uh Oh! You've entered an invalid fleet. Please try again!\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 10.\n"
        + "------------------------------------------------------\n", output.toString());
    assertEquals(1, fleet[0]);
    assertEquals(2, fleet[1]);
    assertEquals(3, fleet[2]);
    assertEquals(4, fleet[3]);
  }

  /**
   * Tests the promptForShots method of the SalvoView class with valid input.
   * Verifies that the method prompts for shots and returns the correct shots.
   */
  @Test
  public void testPromptForShots_ValidInput() {
    input.setInput(new ArrayList<>(List.of("1\n", "1\n")));
    int[] shots = salvoView.promptForShots(board, 1);
    String expectedOutput = "\n" + "Opponent Board Data:\n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n" + "\n"
        + "Your Board:\n"
        + "\tS _ _ _ _ _ \n"
        + "\tS _ _ _ _ _ \n"
        + "\tS _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n" + "\n"
        + "Please Enter 1 shots:\n"
        + "------------------------------------------------------\n";
    assertEquals(expectedOutput, output.toString());
    assertEquals(1, shots[0]);
    assertEquals(1, shots[1]);

  }

  /**
   * Tests the promptForShots method of the SalvoView class with invalid input followed
   * by valid input.
   * Verifies that the method displays an error message for invalid input and returns the
   * correct shots for valid input.
   */
  @Test
  public void testPromptForShots_InvalidInputThenValidInput() {
    input.setInput(new ArrayList<>(List.of("a 1\n", "5 5\n")));
    int[] shots = salvoView.promptForShots(board, 1);
    String expectedOutput = "\n" + "Opponent Board Data:\n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n" + "\n"
        + "Your Board:\n"
        + "\tS _ _ _ _ _ \n"
        + "\tS _ _ _ _ _ \n"
        + "\tS _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n" + "\n"
        + "Please Enter 1 shots:\n"
        + "------------------------------------------------------\n"
        + "\n" + "Opponent Board Data:\n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n" + "\n"
        + "Your Board:\n"
        + "\tS _ _ _ _ _ \n"
        + "\tS _ _ _ _ _ \n"
        + "\tS _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n"
        + "\t_ _ _ _ _ _ \n" + "\n"
        + "Please Enter 1 shots:\n"
        + "------------------------------------------------------\n";
    assertEquals(expectedOutput, output.toString());
    assertEquals(5, shots[0]);
    assertEquals(5, shots[1]);

  }

  /**
   * Tests the displayEndGame method of the SalvoView class with a WIN game result.
   * The test verifies that the method displays the correct message for a win game result.
   */
  @Test
  public void testDisplayEndGameWin() {
    salvoView.displayEndGame(GameResult.WIN);
    assertEquals("Congratulations! You won the game!\n", output.toString());
  }

  /**
   * Tests the displayEndGame method of the SalvoView class with a LOSE game result.
   * The test verifies that the method displays the correct message for a lose game result.
   */
  @Test
  public void testDisplayEndGameLose() {
    salvoView.displayEndGame(GameResult.LOSE);
    assertEquals("Game over! The AI won the game.\n", output.toString());
  }

  /**
   * Tests the displayEndGame method of the SalvoView class with a DRAW game result.
   * The test verifies that the method displays the correct message for a draw game result.
   */
  @Test
  public void testDisplayEndGameDraw() {
    salvoView.displayEndGame(GameResult.DRAW);
    assertEquals("It's a draw! The game ended in a tie.\n", output.toString());
  }

}