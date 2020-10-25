package game.model;

public interface IStrategy {

  /**
   * Places a penguin in the first available board space based on a zig zag pattern starting from
   * the top left hand corner, searching left to right in each row until a free spot is found
   * @param gt The GameTree that will be used to search the game board found in its contained
   *           GameState.
   */
  void placePenguin(GameTree gt);

  /**
   * Given a number n > 0, performs the move for the performing player that will allow for a
   * minimal-maximal gain after looking ahead n turns for the player. The performing player is the
   * currentPlayer in the GameState contained in the root of the given GameTree.
   * @param gt The GameTree used to calculate the minimal-maximal gain.
   * @param numTurns The number of turns for the performing player to look ahead
   */
  void doMinMaxAction(GameTree gt, int numTurns);
}
