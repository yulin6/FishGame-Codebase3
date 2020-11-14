package referee;

import java.util.List;

import player.IPlayerComponent;

/**
 * Interface for a referee component that supervises games of Fish for some given set of players.
 * A referee component should handle the following tasks:
 * - accept a list of players for a game to be run
 * - notify players that a game is beginning and ending
 * - handle communicating with players ("run" the game) for their interactions with the game
 *    - during game running, remove failing/cheating players as appropriate
 * - report outcome of the game to other components (tournament manager, etc.)
 */
public interface IReferee {

  /**
   * Notifies the players of the game that the game is beginning. This function should be called
   * once, as the first interaction with the players from the referee. Should give all players
   * the requisite information for starting the game.
   */
  void notifyGameStart();

  /**
   * Runs all phases of the game - penguin placement, penguin movement, and game end. Interacts
   * with the players in order to get their inputs, and handles invalid behavior appropriately
   * (removes players/their penguins from the game). Should be called once after
   * notifyGameStart is called.
   */
  void runGame();

  /**
   * Notifies the players of the game that the game is ending. This function should be called
   * once after runGame has completed, and should be the final interaction between a given
   * referee and its players, as at this time the game has ended.
   */
  void notifyGameEnd();

  /**
   * Returns the list of winning players from the game (all player(s) that have the greatest
   * number of accumulated fish during the game's playing).
   * @return The list of winning players represented as player component objects (player.IPlayer objects).
   */
  List<IPlayerComponent> getWinners();

  /**
   * Returns the list of "failed" players from the  game. "Failed" players are those who fail to
   * communicate properly with the referee, and are distinct from cheating players. Can be called
   * at any time as necessary by components managing the referee (tournament manager, etc.) after
   * runGame has completed.
   * @return The list of "failed" players, as represented by player component objects.
   */
  List<IPlayerComponent> getFailures();

  /**
   * Returns the list of players who cheated during the game. Cheating players are those who
   * attempt to interact with the game in an illegal way - placing penguins in illegal positions,
   * passing when they can move, attempting to move to/from invalid positions, moving when they
   * must pass, taking actions out of turn, etc. Can be called at any time as necessary by
   * components managing the referee (tournament manager, etc.) after runGame has completed.
   * @return The list of cheaters, as represented by player component objects.
   */
  List<IPlayerComponent> getCheaters();

}
