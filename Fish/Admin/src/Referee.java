import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.model.GameTree;
import game.model.Penguin;
import player.IPlayer;

/**
 * Class implementing a referee component for a game of Fish. As specified in the IReferee
 * interface, a referee component should:
 * - accept a list of players for a game to be run
 * - notify players that a game is beginning and ending
 * - handle communicating with players ("run" the game) for their interactions with the game
 *    - during game running, remove failing/cheating players as appropriate
 * - report outcome of the game to other components (tournament manager, etc.)
 *
 * The data representation for a Referee includes:
 * -
 */
public class Referee implements IReferee {
  private final Map<Penguin.PenguinColor, IPlayer> playerMap;
  private GameTree gt;
  private final List<IPlayer> winners;
  private final List<IPlayer> failures;
  private final List<IPlayer> cheaters;
  private final GamePhase phase;

  /**
   * Constructor for a Referee that takes a list of players and sets up a game for which they
   * will play in, with the Referee handling all interactions between players and the game.
   * @param players The list of players. Assumes that the list of players given is in ascending
   *                order of player age.
   */
  public Referee(List<IPlayer> players) {
    // TODO
    this.playerMap = new HashMap<>();
    // create gt
    this.winners = new ArrayList<>();
    this.failures = new ArrayList<>();
    this.cheaters = new ArrayList<>();
    this.phase = GamePhase.SETUP;
  }

  @Override
  public void notifyGameStart() {
    if (this.phase != GamePhase.SETUP) {
      // TODO - error because wrong timing
    }
    for (Penguin.PenguinColor color : playerMap.keySet()) {
      playerMap.get(color).startPlaying(color);
    }
  }

  @Override
  public void runGame() {
    if (this.phase != GamePhase.SETUP) {
      // TODO - error because wrong timing
    }

    // TODO
    // use PLACING/PLAYING here
  }

  @Override
  public void notifyGameEnd() {
    if (this.phase != GamePhase.END) {
      // TODO - error because wrong timing
    }
    for (Penguin.PenguinColor color : playerMap.keySet()) {
      playerMap.get(color).finishPlaying();
    }
  }

  @Override
  public List<IPlayer> getWinningPlayers() {
    if (this.phase == GamePhase.END) {
      return this.winners;
    }
    else {
      throw new IllegalArgumentException("Can't check the list of cheaters when the game hasn't " +
              "ended.");
    }
  }

  @Override
  public List<IPlayer> getFailures() {
    if (this.phase == GamePhase.END) {
      return this.failures;
    }
    else {
      throw new IllegalArgumentException("Can't check the list of cheaters when the game hasn't " +
              "ended.");
    }
  }

  @Override
  public List<IPlayer> getCheaters() {
    if (this.phase == GamePhase.END) {
      return this.cheaters;
    }
    else {
      throw new IllegalArgumentException("Can't check the list of cheaters when the game hasn't " +
              "ended.");
    }
  }

  /**
   * Enum to distinguish the states of the game the referee is managing.
   * - SETUP represents the setting-up phase of the game, which the contained game starts as, and
   * entails game setup - assigning players colors, board setup, etc.
   * - PLACING represents the penguin-placing phase of the game, during which N players must
   * place 6-N penguins on the board.
   * - PLAYING represents the penguin-movement phase of the game, during which the referee
   * communicates with the players to take turns until the game reaches a final state it cannot
   * progress from.
   * - END represents a game which has ended, and some number of players has won.
   */
  private enum GamePhase {
    SETUP,
    PLACING,
    PLAYING,
    END
  }
}