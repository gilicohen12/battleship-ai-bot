package pa03.view;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;

/**
 * A mock implementation of the Readable interface for testing purposes.
 * This class allows for simulating reading from an input source.
 */
public class MockReadable implements Readable {
  ArrayList<String> input;

  /**
   * Instantiates a new instance of the MockReadable class with the specified list of input strings.
   *
   * @param list the list of input strings to be used for reading
   */
  public MockReadable(ArrayList<String> list) {
    input = list;
  }

  /**
   * Reads characters into the provided CharBuffer.
   * The method appends the next available input string from the list to the CharBuffer.
   *
   * @param cb the CharBuffer to read characters into
   * @return the number of characters read, or -1 if the end of the input list is reached
   * @throws IOException if an I/O error occurs
   */
  @Override
  public int read(CharBuffer cb) throws IOException {
    if (input.size() == 0) {
      return -1;
    }
    cb.append(input.remove(0));
    return 0;
  }

  /**
   * Sets input equal to the given input
   *
   * @param input a list of string input
   */
  public void setInput(ArrayList<String> input) {
    this.input = input;
  }
}
