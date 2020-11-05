import java.util.List;

import player.IPlayer;

/**
 * The tournament manager component will handle all the players that have registered for a
 * tournament, referees for games, and any observers (spectators, etc.). It runs rounds of games,
 * where a round is a set of games at the same depth in a tournament (quarterfinals,
 * semifinals, finals, etc.). This component specifically interacts with players that have already
 * registered, and does not manage initial signups. The manager takes connected players and
 * organizes them into tournaments which consist of some series of games, and handles interacting
 * with the referee in order to have games played as well as advance winning players until a
 * tournament is won. Observers may request information about ongoing tournaments or games from
 * the tournament manager as well as statistics of participating players.
 */
interface ManagerInterface {

  /**
   * Creates a new referee for the games of the tournament as necessary, and determines the players
   * that will play in each of the games of the initial round of the tournament, setting up each of
   * the initial games. Returns a numeric value which identifies a specific tournament. May notify
   * player components of their entry into a tournament, depending on design decisions to be made
   * about the communications layer.
   * @param players The list of player components that will play in the tournament.
   * @param numRounds The number of rounds to play in the tournament (affects number of games per
   *                 round).
   * @return The tournament identification number for the tournament that was set up.
   */
  int setupTournament(List<IPlayer> players, int numRounds);

  /**
   * Runs each of the games within a given tournament in rounds, finishing all of the games
   * in a round before beginning the next round. Handles player advancement from won games to the
   * next game, setting up the next round’s games with the winners of the previous round.
   * Tournaments should be run in such a way that there is only one winner for a given tournament.
   * @param tournamentNo The identification number of the tournament to run.
   */
  void runTournament(int tournamentNo);

  /**
   * Gets the statistics of a player with the given age encapsulated in a PlayerStatistics
   * object. A player is age n if they were the nth signup via the communication layer, so each
   * player will be uniquely identifiable. A player’s stats includes, but is not limited to, a win
   * count, a loss count, a total number of fish earned throughout the tournament they are
   * participating in, and an average number of fish scored per game.
   * @param age The (unique) age of the player to get the statistics of.
   * @return The PlayerStatistics object composing game-playing information about the player.
   */
  PlayerStatistics getPlayerStats(int age);

  /**
   * Can be called freely on a tournament that has been completed (i.e. after it has been run)
   * to get the winner of the tournament.
   * @param tournamentNo The identification number of the tournament to get the winner of.
   * @return The winning player component of the given game.
   */
  IPlayer getTournamentWinner(int tournamentNo);

  /**
   * Sets up an observer to receive updates on a given tournament, with the observer identified by
   * its own ID number (which may be mapped to a port/etc. in the communication layer).
   * The observer will then receive broadcasted information about the tournament as it is running.
   * @param tournamentNo The tournament number for the observer to subscribe to.
   * @param observerNo The ID number of the observer to receive updates about the tournament.
   */
  void subscribeForUpdates(int tournamentNo, int observerNo);

}