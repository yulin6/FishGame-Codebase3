/*
 * Deprecated. See ../Player/src/IPlayer.java
 */

/**
 * Interface for a player component to communicate with the referee.
 * In order to not expose internal objects of the Fish game to players, these functions return
 * String objects containing the JSON representation of various information about the game, as
 * specified in the protocol document (../Planning/player-protocol.md).
 * Contains functionality allowing for a player to:
 * - Get the game state
 * - Get the current game phase
 * - Check legality of a move
 * - See the resulting states from a given function being applied to current state's children
 * - Get information about the tournament
 * - Place an avatar
 * - Make a move
 */
public interface PlayerInterface {

  /**
   * Returns the String containing the JSON representation of the current state of the
   * game as a State, as defined in player-protocol.md
   * @return The current state of the game in serialized JSON, to be deserialized by the player
   */
  String getGameState();

  /**
   * Returns the String containing the JSON representation of the current phase of the
   * game as a Phase, as defined in player-protocol.md (one of "placing", "playing", "over")
   * @return The current phase of the game in serialized JSON, to be deserialized by the player
   */
  String getGamePhase();

  /**
   * Returns the String containing either the State of a game after the move is performed, or
   * else a JSON value of "false" indicating the given move could not be performed on the current
   * board.
   * @param to The String containing the JSON Posn of the position to be moved to.
   * @param from The String containing the JSON Posn of the position to be moved from.
   * @param color The String containing the JSON Color of the player making the move.
   * @return String containing either a JSON State or "false".
   */
  String checkMove(String to, String from, String color);

  /**
   * Returns the String containing either the State of a game after the given player's pass is
   * performed, or else a JSON value of "false" indicating the given pass could not be performed on
   * the current board.
   * @param color The String containing the JSON Color of the player passing their turn.
   * @return String containing either a JSON State or "false".
   */
  String checkPass(String color);

  /**
   * Applies the passed-in function to all the children of the current state.
   * @param func The function to apply to the list of GameTree objects that are children of the
   *             current game state.
   * @return The JSON array of State objects representing the children after the given function
   * was applied.
   */
  String applyToChildren(Consumer<List<GameTree>> func);

  /**
   * Returns the String containing information about the game's winner and which player will
   * advance to the next game of the tournament, if the game is over. Otherwise, returns "cannot
   * query now".
   * @return String containing JSON about tournament info or "cannot query now".
   */
  String tourneyInfo();

  /**
   * Places an avatar of the current player at the given position if the place is legal, and
   * returns a JSON value representing the success or failure of the place. If failure, player is
   * ejected.
   * @param position A String containing JSON representing a Posn to place the avatar at.
   * @return "true" if the place succeeded, else "false" (the player is ejected in "false" case)
   */
  String placeAvatar(String position);

  /**
   * Makes the move from the given source Posn to the destination Posn if the move is legal, and
   * returns a JSON value representing the success or failure of the move. If failure, moving
   * player is ejected.
   * @param to The String containing the JSON Posn to move to.
   * @param from The String containing the JSON Posn to move from.
   * @return "true" if the move succeeded, else "false" (the player is ejected in "false" case)
   */
  String makeMove(String to, String from);

  /**
   * Passes the turn of the player, returning a JSON value representing the success or failure of
   * the pass. If failure, passing player is ejected.
   * @return "true" if the pass succeeded, else "false" (the player is ejected in "false" case)
   */
  String doPass();

}