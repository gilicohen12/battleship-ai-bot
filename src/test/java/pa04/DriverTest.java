package pa04;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cs3500.pa04.Driver;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * Represents tests that check the functionality of the Driver class
 */
class DriverTest {

  /**
   * Tests the main method in Driver when called with the server command-line arguments
   */
  @Test
  void testMain() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    String[] args = new String[2];
    args[0] = "0.0.0.0";
    args[1] = "35001";

    Driver.main(args);
    PrintStream originalOut = System.out;

    System.setOut(originalOut);

    assertDoesNotThrow(() -> Driver.main(args));
  }

}