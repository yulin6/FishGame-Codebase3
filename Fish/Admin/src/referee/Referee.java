package referee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import game.controller.FishController;
import game.model.Action;
import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTreeNode;
import game.model.Penguin;
import game.model.Player;
import game.observer.Observer;
import player.IPlayerComponent;
import player.PlayerComponent;

/**
 * Class implementing a referee component for a game of Fish. As specified in the referee.IReferee
 * interface, a referee component should:
 * - accept a list of players for a game to be run
 * - notify players that a game is beginning and ending
 * - handle communicating with players ("run" the game) for their interactions with the game
 *    - during game running, remove failing/cheating players as appropriate
 *      - cheating players are ones which make logically invalid actions in gameplay
 *      - failing players cause runtime issues - such as returning null objects when asked for
 *      positions for penguin placement or actions to take on a given turn, which are not
 *      logically invalid moves but still create issues in running the game. Additional behavior,
 *      such as failing to respond in a given time window, may be added during the implementation
 *      phase of communications.
 * - report outcome of the game to other components (tournament manager, etc.)
 *
 * The data representation for a Referee includes:
 * - a mapping of penguin colors to player components
 * - a GameTree representing the current state of the overseen game
 * - a list of player components that won the game (empty at initialization, filled at game end)
 * - a list of player components that failed during gameplay (empty at initialization, filled at
 * game end)
 * - a list of player components that cheated during gameplay (empty at initialization, filled at
 * game end)
 * - a number of players associated with the game
 * - a number of penguins that should be assigned to each player (6-N, where N is the number of
 * players)
 * - a constant for time (in seconds) to wait for a player component's response
 * - a constant for the "max" number of penguins (initial # of penguins to subtract # of players
 * from)
 * - an Observer which is used for visualizing the game process, notifying listeners when GameState has changed.
 */
public class Referee implements IReferee {
  private final Map<Penguin.PenguinColor, IPlayerComponent> playerMap;
  private GameTreeNode gt;
  private final List<IPlayerComponent> winners;
  private final List<IPlayerComponent> failures;
  private final List<IPlayerComponent> cheaters;
  private GamePhase phase;
  private int numPlayers;
  private final int penguinsPerPlayer;
  public static final int COMMS_TIMEOUT = 3;
  public static final int PENGUIN_MAX = 6;

  // Entirely arbitrary value to use in constructor of PlayerComponent for testing
  private static final int TEST_SEED = 100;

  private Observer observer;

  /**
   * Constructor for a Referee that takes a list of players, a number of rows and a number of
   * columns for a game board, and sets up a game for which the players will play in, with the
   * Referee handling all interactions between players and the game.
   * @param players The list of players. Assumes that the list of players given is in ascending
   *                order of player age.
   * @param rows the number of rows the referee is instructed to create the board with
   * @param cols the number of columns the referee is instructed to create the board with
   */
  public Referee(List<IPlayerComponent> players, int rows, int cols) {
    this.playerMap = new HashMap<>();
    this.winners = new ArrayList<>();
    this.failures = new ArrayList<>();
    this.cheaters = new ArrayList<>();
    this.numPlayers = players.size();
    this.penguinsPerPlayer = PENGUIN_MAX - this.numPlayers;
    if (rows * cols < this.numPlayers * this.penguinsPerPlayer) {
      throw new IllegalArgumentException("Board with specified parameters cannot handle the given" +
              " number of players");
    }
    GameState gs = makeNewState(players, rows, cols);
    this.gt = new GameTreeNode(gs);
    this.phase = GamePhase.SETUP;
    this.observer = new Observer();
  }

  /**
   * Alternative constructor for Referees used for testing. Accepts a fixed GameState instead of
   * various parameters with which to generate a new game.
   * @param gs GameState to make a new GameTree out of to represent this Referee's game.
   */
  public Referee(GameState gs) {
    this.playerMap = new HashMap<>();
    for (Player p : gs.getPlayers()) {
      playerMap.put(p.getColor(), new PlayerComponent(p.getAge(), TEST_SEED));
      playerMap.get(p.getColor()).startPlaying(p.getColor());
    }
    this.numPlayers = gs.getPlayers().size();
    this.penguinsPerPlayer = PENGUIN_MAX - this.numPlayers;
    this.gt = new GameTreeNode(gs);
    this.winners = new ArrayList<>();
    this.failures = new ArrayList<>();
    this.cheaters = new ArrayList<>();
    this.phase = GamePhase.SETUP;
    this.observer = new Observer();
  }

  /**
   * Another alternative constructor for Referees, used for integration testing. Accepts a fixed
   * GameState and a list of IPlayerComponents to map into the player map.
   * Not safe on player components that don't respond, but this is for a specific integration
   * test and does not target that issue.
   * @param gs GameState to make a new GameTree out of to represent this Referee's game.
   */
  public Referee(GameState gs, List<IPlayerComponent> playerComponents) {
    this.playerMap = new HashMap<>();
    for (Player p : gs.getPlayers()) {
      Penguin.PenguinColor playerColor = p.getColor();
      IPlayerComponent associatedPlayerComponent = null;
      for (IPlayerComponent pc : playerComponents) {
        if (pc.getColor() == playerColor) {
          associatedPlayerComponent = pc;
        }
      }
      if (associatedPlayerComponent == null) {
        throw new IllegalArgumentException("Broken interpretation of data for testing.");
      }
      playerMap.put(playerColor, associatedPlayerComponent);
    }
    this.numPlayers = gs.getPlayers().size();
    this.penguinsPerPlayer = PENGUIN_MAX - this.numPlayers;
    this.gt = new GameTreeNode(gs);
    this.winners = new ArrayList<>();
    this.failures = new ArrayList<>();
    this.cheaters = new ArrayList<>();
    this.phase = GamePhase.SETUP;
    this.observer = new Observer();
  }

  /**
   * Helper function to generate a new GameState object, representing a new game before any
   * penguins have been placed, with the passed-in list of players assigned to the game.
   *
   * @param players The list of players to assign to the game, sorted by ascending age.
   * @param rows the number of rows to create the board with
   * @param cols the number of columns to create the board with
   * @return The GameState that was created.
   */
  private GameState makeNewState(List<IPlayerComponent> players, int rows, int cols) {
    Board b = generateRandomBoard(rows, cols);
    HashSet<Player> playerSet = new HashSet<>();
    for (IPlayerComponent pcomponent : players) {
      Player p;
      try {
        p = assignColor(pcomponent);
      } catch (IllegalArgumentException e) {
        continue;
      }
      playerSet.add(p);
    }
    if (playerSet.size() == 0) {
      throw new IllegalArgumentException("Insufficient players to make a valid state.");
    }
    return new GameState(playerSet, b);

  }

  /**
   * Assigns one of the four possible colors to the player component, checking that the color is
   * not already being used as a key in the color to player-component mapping to avoid duplicates.
   * Handles communication with player components by using a Future and timeouts to check for
   * infinite loops/player communication timeouts, as well as exceptions with catching.
   * @param pcomponent The player component to assign a color to.
   */
  private Player assignColor(IPlayerComponent pcomponent) {
    Penguin.PenguinColor color = Penguin.PenguinColor.getRandomColor();
    while (playerMap.containsKey(color)) {
      color = Penguin.PenguinColor.getRandomColor();
    }

    Integer age;
    final ExecutorService es = Executors.newSingleThreadExecutor();
    final Callable<Integer> getAction = () -> pcomponent.getAge();
    Future<Integer> future = es.submit(getAction);

    try {
      age = future.get(COMMS_TIMEOUT, TimeUnit.SECONDS);
    } catch (TimeoutException | InterruptedException | ExecutionException e) {
      // All exceptions here indicate a player has failed.
      // Don't put the player into the game in the first place; directly add to failures list.
      failures.add(pcomponent);
      numPlayers--;
      throw new IllegalArgumentException("Player component did not appropriately respond.");
    }

    Player newPlayer = new Player(age, color);
    playerMap.put(color, pcomponent);
    return newPlayer;
  }

  /**
   * Creates a board for a game of Fish with enough tiles to place all penguins for each player,
   * and with randomly generated holes that fall within the bounds of the board.
   * @param rows the number of rows to create the board with
   * @param cols the number of columns to create the board with
   * @return A board generated randomly with the provided number of rows and columns that is
   * valid for the amount of players in the game.
   */
  private Board generateRandomBoard(int rows, int cols) {
    int minTiles = penguinsPerPlayer * numPlayers;

    Random rng = new Random();
    ArrayList<BoardPosition> holes = new ArrayList<>();

    if (rows * cols - minTiles > 0) {
      int numHoles = rng.nextInt(rows * cols - minTiles);
      for (int i = 0; i < numHoles; i++) {
        int randRow = rng.nextInt(rows);
        int randCol = rng.nextInt(cols);
        BoardPosition randPos = new BoardPosition(randRow, randCol);
        if (!holes.contains(randPos)) {
          holes.add(randPos);
        }
      }
    }

    return new Board(rows, cols, holes, 0);
  }

  @Override
  public void notifyGameStart() {
    if (this.phase != GamePhase.SETUP) {
      throw new IllegalArgumentException("Cannot notify players that a game is starting outside " +
              "of the setup phase.");
    }
    sendNotifToPlayers(NotifType.START);
  }

  @Override
  public void runGame() {
    if (this.phase != GamePhase.SETUP) {
      throw new IllegalArgumentException("Can't start a run of the game from any phase except " +
              "setup.");
    }
    else {
      this.phase = GamePhase.PLACING;
      doPlacingPhase();
      doPlayingPhase();
    }
    observer.notifyListener();
  }

  /**
   * Runs the penguin-placement phase of the game until it is determined that all players have
   * completed their penguin placements. Adds them to either the list of failed
   * players or cheating players in the event that there is cheating (logically invalid) or
   * failing (runtime-error creating) behavior.
   */
  public void doPlacingPhase() {
    while (this.phase == GamePhase.PLACING) {
      takeOneAction();
      if (gt.getGameState().getPenguins().size() == penguinsPerPlayer * numPlayers) {
        this.phase = GamePhase.PLAYING;
      }
    }
  }

  /**
   * Runs the penguin-movement phase of the game until it is determined that the game has reached
   * a state from which it cannot continue (leaf node of the GameTree). Adds them to either
   * the list of failed players or cheating players in the event of failing/cheating behavior,
   * which consists of runtime-error creating behavior for failing and logically invalid
   * move/pass actions for cheating.
   */
  public void doPlayingPhase() {
    while (this.phase == GamePhase.PLAYING) {
      takeOneAction();
      if (!this.gt.getGameState().movesPossible()) {
        this.phase = GamePhase.END;
        setWinningPlayers();
      }
    }
  }

  /**
   * Runs a round of the game, depending on the current phase of the game. If the game phase is
   * penguin placement, then a round of penguin placement is performed. If the game phase is
   * penguin movement, then a round of penguin movement is performed. Copies the current
   * GameTreeNode in order to prevent the player from modifying the Referee's trusted data
   * structures.
   */
  public void takeOneAction() {
    if (numPlayers > 0) {
      GameTreeNode copyTree = new GameTreeNode(gt.getGameState());
      GameState currState = this.gt.getGameState();
      Player currPlayer = currState.getCurrentPlayer();
      Penguin.PenguinColor currColor = currPlayer.getColor();
      IPlayerComponent currPComponent = playerMap.get(currColor);
      Action action;
      Future<Action> future = getFuture(copyTree, currPComponent);

      try {
        action = future.get(COMMS_TIMEOUT, TimeUnit.SECONDS);
      } catch (TimeoutException | InterruptedException | ExecutionException e) {
        // All exceptions here indicate a player has failed.
        invalidPlayer(currState, currPlayer, currPComponent, failures);
        this.gt = new GameTreeNode(currState);
        this.observer.notifyListener();
        return;
      }
      doPlayerAction(action, currState, currPlayer, currPComponent);
    }
  }

  /**
   * Returns the Future used to get a player component's action, with the appropriate request
   * being passed to the player component depending on the current phase of the game (penguin
   * placing or penguin movement).
   * @param node GameTreeNode (a copy, in order to avoid player components mutating our data) passed
   *            to the current player component in order to get an Action they are attempting to perform.
   * @param currComp The current external player component of the game.
   * @return The Future to get the player component's action from.
   */
  private Future<Action> getFuture(GameTreeNode node, IPlayerComponent currComp) {
    Future<Action> future;
    final ExecutorService es = Executors.newSingleThreadExecutor();

    if (phase == GamePhase.PLACING) {
      final Callable<Action> getAction = () -> currComp.placePenguin(node);
      future = es.submit(getAction);
    } else if (phase == GamePhase.PLAYING) {
      final Callable<Action> getAction = () -> currComp.takeTurn(node);
      future = es.submit(getAction);
    } else {
      throw new IllegalStateException("Wrong game phase.");
    }
    return future;
  }

  /**
   * Performs a player component's returned action on the passed-in (current) GameState. Takes
   * additional player and player component in the event that they need to be moved to invalid
   * lists.
   * @param action The Action from the player component to be performed. May be invalid.
   * @param gs The gamestate to perform the player's action on.
   * @param currPlayer Internal representation of the current player.
   * @param currComponent External player component representing the current player.
   */
  private void doPlayerAction(Action action, GameState gs, Player currPlayer,
                              IPlayerComponent currComponent) {
    if (action == null) {
      invalidPlayer(gs, currPlayer, currComponent, failures);
      this.gt = new GameTreeNode(gs);
      this.observer.notifyListener();
    } else {
      try {
        if (phase == GamePhase.PLACING) {
          action.perform(gs);
          this.gt = new GameTreeNode(gs);
          this.observer.notifyListener();
        } else if (phase == GamePhase.PLAYING) {
          this.gt = gt.lookAhead(action);
          this.observer.notifyListener();
        }
      } catch (IllegalArgumentException iae) {
        // player made an illegal placement/movement
        invalidPlayer(gs, currPlayer, currComponent, cheaters);
        this.gt = new GameTreeNode(gs);
        this.observer.notifyListener();
      }
    }
  }

  /**
   * Handles the response to either failing or invalid behavior by removing the player from all
   * of the places where it is used, from the GameState to the data representations in the
   * referee.Referee. Moves the player component into the provided list (failures/cheaters).
   * Decrements the number of players actively playing in the game.
   * @param gs The GameState to remove the player (its penguins) from.
   * @param p The Player object to remove from the GameState.
   * @param pcomp The component (object implementing IPLayer interface) to mark as failing or
   *              cheating in this Referee.
   * @param list The list of player components (should be failures/cheaters) to add the component
   *            to.
   */
  private void invalidPlayer(GameState gs, Player p, IPlayerComponent pcomp, List<IPlayerComponent> list) {
    gs.removePlayer(p);
    list.add(pcomp);
    numPlayers--;
  }

  /**
   * Looks through the state of the game to identify the winning players and add them to the list
   * of winning players in this Referee.
   */
  private void setWinningPlayers() {
    int maxFish = 0;

    // Can never get a player that has failured or cheated here because they are removed from the
    // game state as well
    for (Player p : this.gt.getGameState().getPlayers()) {
      IPlayerComponent pcomp = playerMap.get(p.getColor());
      int numFish = p.getFish();

      if (numFish > maxFish) {
        maxFish = numFish;
        winners.clear();
        winners.add(pcomp);
      } else if (numFish == maxFish) {
        winners.add(pcomp);
      }
    }
  }

  @Override
  public void notifyGameEnd() {
    if (this.phase != GamePhase.END) {
      throw new IllegalArgumentException("Can't notify players of a game's end unless it has " +
              "ended.");
    }
    sendNotifToPlayers(NotifType.END);
  }

  /**
   * Function to abstract the sending of notifications (start playing and finish playing) to the
   * players of the game. Creates a type implementing Callable to use to call on the player
   * components, which covers player timeouts in communication and player infinite loops with a
   * timeout, and covers player exceptions with catching.
   * @param type The type of notification (START/END) to send to the players.
   */
  private void sendNotifToPlayers(NotifType type) {
    Future<Void> sendNotif;
    ExecutorService es = null;
    Callable<Void> methodCall;

    /*
      the Callable class will be a notifier to a player when a game starts or ends.
      Since call() in a Callable class doesn't take in a parameter, so we have a
      constructor that takes in a PenguinColor, which is used for identifying
      the player who has the matching color.
     */
    class NotifFunc implements Callable<Void> {
      private final Penguin.PenguinColor color;

      @Override
      public Void call() {
        if (type == NotifType.START) {
          playerMap.get(color).startPlaying(color);
        }
        else if (type == NotifType.END) {
          playerMap.get(color).finishPlaying();
        }
        else {
          throw new IllegalArgumentException("Invalid notification type.");
        }
        return null;
      }

      /**
       * Constructor for a NotifFunc. Takes the color to use in the call.
       * @param color Color to call startPlaying or finishPlaying with.
       */
      NotifFunc(Penguin.PenguinColor color) {
        this.color = color;

      }
    }

    for (Penguin.PenguinColor color : playerMap.keySet()) {
      if (failures.contains(playerMap.get(color))) {
        continue;
      }
      try {
        es = Executors.newSingleThreadExecutor();
        methodCall = new NotifFunc(color);
        sendNotif = es.submit(methodCall);
        sendNotif.get(COMMS_TIMEOUT, TimeUnit.SECONDS);
      }
      catch (TimeoutException | InterruptedException | ExecutionException e) {
        // All exceptions here indicate a player has failed.
        GameState state = this.gt.getGameState();
        Player player = null;
        for (Player p : state.getPlayers()) {
          if (p.getColor() == color) {
            player = p;
            break;
          }
        }
        es.shutdown(); // doesn't actually shut down the thread if it's infinite looping
        IPlayerComponent failedPlayer = playerMap.get(color);
        invalidPlayer(state, player, failedPlayer, failures);
        this.gt = new GameTreeNode(state);
        this.observer.notifyListener();
      }
    }
  }

  @Override
  public List<IPlayerComponent> getWinners() {
    if (this.phase == GamePhase.END) {
      return this.winners;
    }
    else {
      throw new IllegalArgumentException("Can't check the list of winners when the game hasn't " +
              "ended.");
    }
  }

  @Override
  public List<IPlayerComponent> getFailures() {
    if (this.phase == GamePhase.END) {
      return this.failures;
    }
    else {
      throw new IllegalArgumentException("Can't check the list of failures when the game hasn't " +
              "ended.");
    }
  }

  @Override
  public List<IPlayerComponent> getCheaters() {
    if (this.phase == GamePhase.END) {
      return this.cheaters;
    }
    else {
      throw new IllegalArgumentException("Can't check the list of cheaters when the game hasn't " +
              "ended.");
    }
  }

  public void setListener(FishController controller){
    this.observer.addListener(controller);
  }

  /**
   * Returns the GameState of this Referee.
   * @return the GameState object from this Referee.
   */
  public GameState getGameState() {
    return this.gt.getGameState();
  }

  /**
   * set the game phase of the current game to the given game phase
   * @param gamePhase a GamePhase enum
   */
  public void setGamePhase(GamePhase gamePhase){
    this.phase = gamePhase;
  }

  /**
   * get the game phase of the current game
   * @return gamePhase a GamePhase enum
   */
  public GamePhase getPhase() {
    return phase;
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
  public enum GamePhase {
    SETUP,
    PLACING,
    PLAYING,
    END
  }

  /**
   * Enum to distinguish kinds of notifications that can be sent from the Referee to the
   * player components. Used in sendNotifToPlayers.
   * - START indicates that the referee is calling startPlaying on the components.
   * - END indicates that the referee is calling finishPlaying on the components.
   */
  private enum NotifType {
    START,
    END
  }
}