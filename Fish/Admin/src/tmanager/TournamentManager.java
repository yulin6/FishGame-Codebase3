package tmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import player.IPlayerComponent;
import referee.Referee;

/**
 * Class representing a tournament manager, used to run a single game tournament until a winner
 * (or winners) occur. Performs the task of notifying the players the tournament is beginning,
 * then runs the tournament with a knockout elimination system in rounds.
 *
 * A TournamentManager composes:
 * - activePlayers: a list of active (still-playing) player components
 * - referees: a list of Referee objects which are used to represent the games run by the manager
 * - phase: an enum to represent the phase a tournament is in (running or over)
 * - firstRoundRun: a boolean which if false is the first round hasn't been run, true otherwise
 *      (used to determine tournament-end conditions).
 *
 * It also contains constants relating to max and min players in a game and a max bound on board
 * dimensions.
 */
public class TournamentManager {

  private final List<IPlayerComponent> activePlayers;
  private List<Referee> referees;
  private TournamentPhase phase;
  private boolean firstRoundRun = false;

  private static final int MAX_PLAYERS = 4;
  private static final int MIN_PLAYERS = 2;
  private static final int BOARD_DIMENSION_MAX = 5;

  /**
   * Constructor for a TournamentManager object. Accepts a list of external player components and
   * a number to modify the parameters of Referees' boards with, and constructs a
   * TournamentManager. The TournamentManager informs the players that a tournament is beginning
   * (handling failures appropriately) and sets up games to be run.
   * @param players The list of external player components to notify and then assign to games
   *                under new Referees. Assumed to be sorted in age-ascending order when passed
   *                in, as they have already signed up.
   */
  public TournamentManager(List<IPlayerComponent> players) {
    if (players.size() < MIN_PLAYERS) {
      throw new IllegalArgumentException("Not enough players to form a tournament.");
    }
    this.activePlayers = new ArrayList<>(players);
    informPlayers(InformType.START);
    generateGames();
    this.phase = TournamentPhase.RUNNING;
  }

  /**
   * Generates games out of the list of active players by creating Referees with subsets of the
   * list of active players and assigns them to the referees list. No communication with the player
   * components occurs in this method, so no failure handling is present, and the active players
   * listing should be post-player-notification so as to prevent assigning failing players to games.
   */
  private void generateGames() {
    referees = new ArrayList<>();
    int playersPerGame = MAX_PLAYERS;
    List<IPlayerComponent> playersToAssign = new ArrayList<>(activePlayers);
    while (playersToAssign.size() > 0) {
      if (playersToAssign.size() < playersPerGame) {
        playersPerGame--;
        if (playersPerGame < MIN_PLAYERS) {
          throw new IllegalArgumentException("Reached below a valid amount of players.");
        }
      }

      List<IPlayerComponent> newGamePlayers = new ArrayList<>();
      if (playersToAssign.size() < playersPerGame) {
        continue;
      }
      for (int i = 0; i < playersPerGame; i++) {
        newGamePlayers.add(playersToAssign.remove(0));
      }

      if (MIN_PLAYERS > playersToAssign.size() && playersToAssign.size() > 0) {
        for (int j = newGamePlayers.size() - 1; j >= 0; j--) {
          playersToAssign.add(0, newGamePlayers.remove(j));
        }
        playersPerGame--;
      }
      else {
        addNewReferee(newGamePlayers);
      }
    }
  }

  /**
   * Adds a referee to the list of referees held by this TournamentManager using the given list
   * of players as the list of players for the referee's game.
   * @param players List of players to add to a new referee's game, sorted in ascending order of
   *                age.
   */
  private void addNewReferee(List<IPlayerComponent> players) {
    Random rng = new Random();
    int rows = 0;
    int cols = 0;
    int minTiles = players.size() * (Referee.PENGUIN_MAX - players.size());

    while (rows * cols < minTiles) {
      // nextInt() generates an integer in range [0, PARAM).
      rows = rng.nextInt(BOARD_DIMENSION_MAX) + 1;
      cols = rng.nextInt(BOARD_DIMENSION_MAX) + 1;
    }

    Referee newRef = new Referee(players, rows, cols);
    referees.add(newRef);
  }

  /**
   * Runs the entire tournament as a series of rounds, as long as the tournament remains in the
   * running phase.
   * Also, when the number of participants has become small enough to run a single game, runs it
   * as the last game and the list of winners is determined from the results of the game.
   */
  public void runTournament() {
    while (phase == TournamentPhase.RUNNING) {
      if (activePlayers.size() <= MAX_PLAYERS) {
        runTournamentRound();
        phase = TournamentPhase.END;
        break;
      }
      else {
        runTournamentRound();
      }
    }
    informPlayers(InformType.END);
  }

  /**
   * Runs a round of the tournament by having all of the referees run their individual games.
   * Checks for the tourney-end conditions; if the tournament hasn't ended, generates a new set of
   * games from the remaining players. Otherwise, notifies players of the tournament's end.
   */
  public void runTournamentRound() {
    if (phase == TournamentPhase.RUNNING) {
      List<IPlayerComponent> winners = runGames();
      boolean sameWinnersAsLastRound = !activePlayers.retainAll(winners);
      if (isTournamentOver(winners, sameWinnersAsLastRound)) {
        phase = TournamentPhase.END;
      }
      else {
        firstRoundRun = true;
        generateGames();
      }
    }
    else {
      throw new IllegalStateException("Cannot run a round while the game is not in the running " +
              "phase.");
    }
  }

  /**
   * Notifies the players of the beginning of their games, runs the games, and
   * notifies the players of the end of their games for every Referee in a given round. Compiles
   * the winners of each game into a single list, which is returned. The list won't have any
   * duplicates, as each player participates in a single game per round.
   * @return A complete list of the winning players from each game in the round.
   */
  private List<IPlayerComponent> runGames() {
    List<IPlayerComponent> winners = new ArrayList<>();
    for (Referee referee : referees) {
      referee.notifyGameStart();
      referee.runGame();
      referee.notifyGameEnd();
      winners.addAll(referee.getWinners());
    }
    return winners;
  }

  /**
   * Checks two of three ending conditions for the tournament, as listed below.
   * - two tournament rounds of games in a row produce the exact same winners
   * - there are too few players for a single game
   * @param winners The winners of a round of the tournament, used to check if the entire
   *                tournament is over.
   * @param repeatWinners Boolean indicating whether two rounds of the tournament generated the
   *                      exact same set of winners.
   * @return True if the tournament is now over, else false.
   */
  private boolean isTournamentOver(List<IPlayerComponent> winners, boolean repeatWinners) {
    if (repeatWinners && firstRoundRun) {
      return true;
    }
    return winners.size() < MIN_PLAYERS;
  }

  /**
   * Returns the list of external player components that won the game, if the tournament is over. If
   * the tournament is not over, throws an exception for requesting at the wrong time.
   *
   * @return The list of winning players, if the tournament is over. (May be empty.)
   */
  public List<IPlayerComponent> getWinners() {
    if (phase == TournamentPhase.END) {
      return new ArrayList<>(activePlayers);
    } else {
      throw new IllegalStateException("Tournament has not ended, cannot request winners.");
    }
  }

  /**
   * Informs the players of their entry into a tournament or the end of the tournament they've
   * entered, depending on the input InformType. Removes players in the event that they fail (throw
   * an exception or get stuck in an infinite loop in their called method).
   *
   * @param type The type of information to broadcast to players (start or end).
   */
  private void informPlayers(InformType type) {
    Future informFuture;
    ExecutorService es;
    Runnable informCall;

    List<IPlayerComponent> informedPlayers = new ArrayList<>(activePlayers);

    for (IPlayerComponent player : informedPlayers) {
      if (type == InformType.START) {
        informCall = () -> player.joinTournament();
      } else if (type == InformType.END) {
        informCall = () -> player.leaveTournament();
      } else {
        throw new IllegalArgumentException("Invalid InformType received in informPlayers.");
      }

      try {
        es = Executors.newSingleThreadExecutor();
        informFuture = es.submit(informCall);
        informFuture.get(Referee.COMMS_TIMEOUT, TimeUnit.SECONDS);
      } catch (InterruptedException | ExecutionException | TimeoutException e) {
        activePlayers.remove(player);
      }
    }
  }

  /**
   * Enum to describe the phases of a game - running, or ended.
   */
  public enum TournamentPhase {
    RUNNING,
    END
  }

  /**
   * Enum to describe the possible informational messages that may be sent to players (tournament is
   * starting, tournament is ending).
   */
  private enum InformType {
    START,
    END
  }

}