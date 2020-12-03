package tmanager;

import game.observer.StateChangeListener;
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
public class TournamentManager implements ITournamentManager {

  private List<IPlayerComponent> activePlayers;
  private List<Referee> referees;
  private List<StateChangeListener> listeners;
  private TournamentPhase phase;
  private boolean firstRoundRan = false;

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
    this.listeners = new ArrayList<>();
    informPlayers(this.activePlayers, InformType.START, false);
    generateGames();
    this.phase = TournamentPhase.RUNNING;
  }

  /**
   * Adds a listener to all games of Fish run in this tournament
   * @param listener the listener to add
   */
  public void addGameListener(StateChangeListener listener) {
    this.listeners.add(listener);
    for(Referee referee: referees) {
      referee.setListener(listener);
    }
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
        continue;
      }

      List<IPlayerComponent> newGamePlayers = new ArrayList<>();

      for (int i = 0; i < playersPerGame; i++) {
        newGamePlayers.add(playersToAssign.remove(0));
      }

      if (playersToAssign.size() > 0 && playersToAssign.size() < MIN_PLAYERS ) {
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

    try {
      Referee newRef = new Referee(players, rows, cols);
      for (StateChangeListener listener : this.listeners) {
        newRef.setListener(listener);
      }
      referees.add(newRef);
    } catch (IllegalArgumentException e) {
      // This means that the referee has no valid game to oversee; we don't add anything to the
      // list of referees.
    }
  }

  @Override
  public void runTournament() {
    while (phase == TournamentPhase.RUNNING) {
        runTournamentRound();
    }
    informPlayers(this.activePlayers, InformType.END, true);
  }

  /**
   * Runs a round of the tournament by having all of the referees run their individual games.
   * Checks for the tourney-end conditions; if the tournament hasn't ended, generates a new set of
   * games from the remaining players.
   */
  public void runTournamentRound() {
    if (phase == TournamentPhase.RUNNING) {
      List<IPlayerComponent> winners = runGames();

      if (isTournamentOver(winners)) {
        activePlayers = winners;
        phase = TournamentPhase.END;
      }
      else {
        firstRoundRan = true;
        activePlayers = winners;
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
    List<IPlayerComponent> failuresAndCheaters = new ArrayList<>();
    for (Referee referee : referees) {
      referee.notifyGameStart();
      referee.runGame();
      referee.notifyGameEnd();
      winners.addAll(referee.getWinners());
      failuresAndCheaters.addAll(referee.getFailures());
      failuresAndCheaters.addAll(referee.getCheaters());
    }
    informPlayers(failuresAndCheaters, InformType.END, false);
    return winners;
  }


  /**
   * helper method that will be called in runTournamentRound() for checking is the ending conditions of the
   * tournament. First condition is "no game possible" meaning the winners size from this round is less than
   * MIN_PLAYERS. Second condition is "only one game possible", which means the activePlayer size is less
   * than MAX_PLAYERS. Third condition is "repeated outcomes", it is true when the winners from this round contains
   * all the existing activePlayers and firstRoundRan is true (prevents comparing winners to original activePlayers).
   * @param winners the winners from this round of games
   * @return boolean determine whether the tournament has ended.
   */
  private boolean isTournamentOver(List<IPlayerComponent> winners) {
    boolean noGamePossible = winners.size() < MIN_PLAYERS;
    boolean oneGamePossible = activePlayers.size() < MAX_PLAYERS;
    boolean repeatedWinners = winners.containsAll(activePlayers) && firstRoundRan;

    return noGamePossible || oneGamePossible || repeatedWinners;
  }

  @Override
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
  private void informPlayers(List<IPlayerComponent> players, InformType type, boolean isWinner) {
    Future informFuture;
    ExecutorService es = null;
    Runnable informCall;

    List<IPlayerComponent> informedPlayers = new ArrayList<>(players);

    for (IPlayerComponent player : informedPlayers) {
      if (type == InformType.START) {
        informCall = () -> player.joinTournament();
      } else if (type == InformType.END) {
        informCall = () -> player.leaveTournament(isWinner);
      } else {
        throw new IllegalArgumentException("Invalid InformType received in informPlayers.");
      }

      try {
        es = Executors.newSingleThreadExecutor();
        informFuture = es.submit(informCall);
        informFuture.get(Referee.COMMS_TIMEOUT, TimeUnit.SECONDS);
      } catch (InterruptedException | ExecutionException | TimeoutException e) {
        es.shutdown();
        activePlayers.remove(player);
      }
    }
  }

  /**
   * Checks if the first round of the game has been run.
   * @return The boolean member variable that states whether or not the game's first round has
   * been run.
   */
  public boolean isFirstRoundRan() {
    return firstRoundRan;
  }

  /**
   * Get the list of referees in the current round of tournament.
   * @return a list of referees represents the games in the tournament round.
   */
  public List<Referee> getReferees() {
    return referees;
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