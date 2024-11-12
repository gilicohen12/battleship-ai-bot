package cs3500.pa03.model;

/**
 * The Cell enum represents the possible states of a cell on a game board.
 * Each cell can be in one of the following states:
 * - O: Empty cell (not shot)
 * - M: Missed shot
 * - H: Successful hit
 * - S: Ship present
 */
public enum Cell {
  O,
  M,
  H,
  S;

}
