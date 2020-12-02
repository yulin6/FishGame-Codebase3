package tmanager;

import java.util.List;
import player.IPlayerComponent;

public interface ITournamentManager {

  /**
   * Runs the entire tournament as a series of rounds, as long as the tournament remains in the
   * running phase. When the phase of tournament is not running, it informs all the remaining
   * activePlayers that they are winners.
   */
  public void runTournament();

  /**
   * Returns the list of external player components that won the game, if the tournament is over. If
   * the tournament is not over, throws an exception for requesting at the wrong time.
   *
   * @return The list of winning players, if the tournament is over. (May be empty.)
   */
  public List<IPlayerComponent> getWinners();
}
