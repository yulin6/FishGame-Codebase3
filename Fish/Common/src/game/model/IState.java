package game.model;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Interface to handle varying representation of game states.
 * Can be used to allow for different implementations of the same data to be swapped.
 */
public interface IState {

  /**
   * Places an avatar on behalf of a player at the given board position.
   * @param bp The board position at which to place the avatar.
   * @param p The player on behalf of whom the avatar is being placed.
   */
  void placeAvatar(BoardPosition bp, Player p);

  /**
   * Moves the avatar from one BoardPosition to another on behalf on a player; this should
   * advance the current player to the next player.
   * No rule checking is performed within this - a referee will - though it does check
   * that an avatar is present on the source tile and that one is not already on the destination,
   * as well as that the avatar moved belongs to the player.
   * @param to The board position to move the avatar to.
   * @param from The board position to move the avatar from.
   * @param p The player on behalf of whom the move is being made.
   */
  void moveAvatar(BoardPosition to, BoardPosition from, Player p);

  /**
   * Determines whether any player can make any moves.
   * @return True if any moves are possible by any player, else false.
   */
  boolean movesPossible();

  /**
   * Renders this entire IState based on the state of the board and current penguin placements on
   * the board.
   * @param g The graphics element to be used to draw the state of the board and penguins.
   */
  void render(Graphics g);

  /**
   * Gets the player whose turn it currently is.
   * @return The currently active player.
   */
  Player getCurrentPlayer();

  /**
   * Gets the board for this game state
   * @return the Fish game board of this game state
   */
  IBoard getBoard();

  /**
   * Removes a player from the game state
   * @param p The player to remove
   */
  void removePlayer(Player p);

  /**
   * Sets the next player based on age order
   */
  void setNextPlayer();

  /**
   * Gets the penguin at the given BoardPosition. If no Penguin is there, throw an exception
   * @param bp The BoardPosition on the board where we want to find a penguin
   * @return The penguin at the position, or an exception if no penguin is on that board space.
   */
  Penguin getPenguinAtPosn(BoardPosition bp);

  /**
   * Determines if there is a penguin at a given BoardPosition on the game board
   * @param bp The board position being checked for penguin occupancy
   * @return True if penguin is on given board position, flase otherwise
   */
  boolean isPenguinAtPosn(BoardPosition bp);

  /**
   * Returns all the possible Actions from this game state.
   *
   * INVARIANT The list of all possible moves contains all of the possible moves for the current
   * player of the game state. They are guaranteed to be valid.
   * @return a list of all the Actions possible
   */
  ArrayList<Action> getPossibleActions();


}
