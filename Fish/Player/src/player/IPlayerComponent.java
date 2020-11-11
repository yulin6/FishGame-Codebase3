package player;

import game.model.*;

/**
 * Interface for player components of the Fish game, each of which represents a single player. Used
 * to communicate with referee components in order to request game functionality, such as placement
 * of penguins, actions (passing or moving), and so on. Uses a strategy component to perform the
 * decision-making logic for these game functions.
 */
public interface IPlayerComponent {

  /**
   * Sets up the player component with the game by storing its assigned color, which is given
   * to it by the referee when beginning the game, prior to placing any penguins. Should only be
   * called if the player is not already in a game; if it is, should throw an exception.
   * @param color The assigned color for this player, to be held within it.
   */
  void startPlaying(Penguin.PenguinColor color);

  /**
   *
   * When this function is called during the penguin-placement phase of the game, the player
   * component should return the appropriate board position on which its penguin should be
   * placed, following the logic of the strategy component of the player if it is a computer
   * player, or if the player is playing manually, their selected position.
   * Should not be called before setup is called or in the penguin-movement phase, only during
   * the penguin-placement phase, and only as many times as this player has penguins to assign.
   * @param gt The game tree with the current state of the game as its root node, for use in
   *           determining the position at which to place a penguin.
   * @return The Place action that is deemed appropriate by the player to place a penguin.
   */
  Place placePenguin(GameTreeNode gt);

  /**
   * Returns the action that consists of this player's turn when called during the penguin-movement
   * phase of the game, according to the decision of a strategy component or other similar
   * decision-making logic. This action can either be a Move or a Pass.
   * Should not be called in any other phase of the game, and can be called (very frequently) as
   * many times as necessary until the game reaches an ended state, after which finishPlaying
   * should be called.
   * @param gt The game tree with the current state of the game as its root node, for use in
   *           determining the action for the player's turn.
   * @return The action that the player is taking during this turn.
   */
  Action takeTurn(GameTreeNode gt);

  /**
   * Completes playing a game for this given player, being called after the game has ended. If the
   * player is not playing a game, should throw an exception.
   */
  void finishPlaying();

  /**
   * Returns the age of the player represented by the player component as an integer.
   * @return The age value of the player.
   */
  int getAge();

  /**
   * Returns the assigned color of the player for its game of Fish.
   * @return The color of the player (and its penguins).
   */
  Penguin.PenguinColor getColor();

}
