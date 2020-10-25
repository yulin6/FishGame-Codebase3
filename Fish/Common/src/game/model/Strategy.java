package game.model;

/**
 * A Strategy contains the functionality that allows players to select the placement of penguins
 * based on the first available position on the game board following a zig zag pattern (traversing
 * left to right through each row, moving to the next row if all spaces in the row are ineligible).
 * Please see Board documentation for interpretation of rows and columns.
 *
 * A Strategy also contains the functionality that allows players to select and perform the next
 * move that will allow for a minimal maximum gain given N > 0 number of the performing player's
 * turns to look ahead. A minimal maximum gain after N turns is the highest score a player can earn
 * after playing the specified number of turns if all other players pick one of the moves that
 * minimize the player's gain.
 */
public class Strategy implements IStrategy{
  @Override
  public void placePenguin(GameTree gt) {
    GameState gs = gt.getGameState();
    IBoard b = gs.getBoard();

    for(int r = 0; r < b.getRows(); r++) {
      for(int c = 0; c < b.getCols(); c++) {
        BoardPosition bp = new BoardPosition(r, c);
        if(!b.getSpace(bp).isHole() && !gs.isPenguinAtPosn(bp)) {
          gs.placeAvatar(bp, gs.getCurrentPlayer());
          gs.setNextPlayer();
        }
      }
    }
  }

  @Override
  public void doMinMaxAction(GameTree gt, int numTurns) {
    // Base Case - return move that corresponds to maximum result (i.e. most fish)
    // Map moves possible from lowest level to fish on target tile
    // Iterate through all keys (Actions) in map and return the one that has the highest value
    // If same fish in multiple moves, do tiebreak (lowest row number of source position of penguin,
    // then lowest column number if row numbers are the same, repeated for target position if source
    // positions are the same)
    // Returns a maximum number of fish

    //Propagate Upward - Fish of current move + fish from calculated max move from lower level


    //PSEUDOCODE
    /**
     * map(moves, fishValues)
     *
     * for all possible moves
     *  put(move, getMinMaxAction(gt.lookAhead(move), numTurns - 1,
     *      gt.getGameState().getCurrentPlayer().getColor())
     *
     * a is action that will allow for minmax given n turns ahead
     * a.perform(...)
     */


  }

  private int getMinMaxAction(GameTree gt, int numTurns, Penguin.PenguinColor c) {
    return 0;

    //PSEUDOCODE
    /**
     * gs = gt.getgamestate();
     *
     * if c is currentPlayer color
     *  - return 0 if possible moves are only pass
     *  - otherwise:
     *   if numTurns > 1
     *    for each possible move
     *      recurse over lookahead with move, decrement numTurns because player's turn
     *      return largest value out of all recursive lookaheads - made optimal minmax move in the
     *        future
     *
     * if c is not currentPlayer color
     *  for each possible move
     *    recurse over lookahead with move, decrement numTurns because player's turn
     *    return smallest value out of all recursive lookaheads - made most preventative move as
     *      enemy
     *
     * if numTurns == 1 && c is currentPlayer color [BASE CASE]
     *  return value of move that has highest fish at target tile out of gs.getPossibleMoves
     *
     *
     */
  }

  /**
  private int minMaxTurn(GameTree gt, int turnsLeft, int maxFish) {
    return 0;
  }

  private int maxOpponentTurns(GameTree gt, int turnsLeft, int maxFish) {
    return 0;
  }
   **/
}
