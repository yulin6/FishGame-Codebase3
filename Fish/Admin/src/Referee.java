import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import game.model.Action;
import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTree;
import game.model.Penguin;
import game.model.Player;
import player.IPlayer;
import player.PlayerComponent;

/**
 * Class implementing a referee component for a game of Fish. As specified in the IReferee
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
 */
public class Referee implements IReferee {
  private final Map<Penguin.PenguinColor, IPlayer> playerMap;
  private GameTree gt;
  private final List<IPlayer> winners;
  private final List<IPlayer> failures;
  private final List<IPlayer> cheaters;
  private GamePhase phase;
  private int numPlayers;
  private final int penguinsPerPlayer;
  private static final int PENGUIN_MAX = 6;

  // Entirely arbitrary value to use in constructor of PlayerComponent for testing
  private static final int TEST_SEED = 100;

  /**
   * Constructor for a Referee that takes a list of players, a number of rows and a number of
   * columns for a game board, and sets up a game for which the players will play in, with the
   * Referee handling all interactions between players and the game.
   * @param players The list of players. Assumes that the list of players given is in ascending
   *                order of player age.
   * @param rows the number of rows the referee is instructed to create the board with
   * @param cols the number of columns the referee is instructed to create the board with
   */
  public Referee(List<IPlayer> players, int rows, int cols) {
    this.playerMap = new HashMap<>();
    this.numPlayers = players.size();
    this.penguinsPerPlayer = PENGUIN_MAX - this.numPlayers;
    if (rows * cols < this.numPlayers * this.penguinsPerPlayer) {
      throw new IllegalArgumentException("Board with specified parameters cannot handle the given" +
              " number of players");
    }
    GameState gs = makeNewState(players, rows, cols);
    this.gt = new GameTree(gs);
    this.winners = new ArrayList<>();
    this.failures = new ArrayList<>();
    this.cheaters = new ArrayList<>();
    this.phase = GamePhase.SETUP;
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
    this.gt = new GameTree(gs);
    this.winners = new ArrayList<>();
    this.failures = new ArrayList<>();
    this.cheaters = new ArrayList<>();
    this.phase = GamePhase.SETUP;
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
  private GameState makeNewState(List<IPlayer> players, int rows, int cols) {
    Board b = generateRandomBoard(rows, cols);
    HashSet<Player> playerSet = new HashSet<>();
    for (IPlayer pcomponent : players) {
      Player p = assignColor(pcomponent);
      playerSet.add(p);
    }

    return new GameState(playerSet, b);
  }

  /**
   * Assigns one of the four possible colors to the player component, checking that the color is
   * not already being used as a key in the color to player-component mapping to avoid duplicates.
   * @param p The player component to assign a color to.
   */
  private Player assignColor(IPlayer p) {
    Penguin.PenguinColor color = Penguin.PenguinColor.getRandomColor();
    while (playerMap.containsKey(color)) {
      color = Penguin.PenguinColor.getRandomColor();
    }
    Player newPlayer = new Player(p.getAge(), color);
    playerMap.put(color, p);
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
    for (Penguin.PenguinColor color : playerMap.keySet()) {
      playerMap.get(color).startPlaying(color);
    }
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
  }

  /**
   * Runs the penguin-placement phase of the game until it is determined that all players have
   * completed their penguin placements. Adds them to either the list of failed
   * players or cheating players in the event that there is cheating (logically invalid) or
   * failing (runtime-error creating) behavior.
   */
  private void doPlacingPhase() {
    while (this.phase == GamePhase.PLACING) {
      GameState gs = this.gt.getGameState();
      Player currPlayer = gs.getCurrentPlayer();
      Penguin.PenguinColor currColor = currPlayer.getColor();
      IPlayer currPComponent = playerMap.get(currColor);

      BoardPosition candidatePos = currPComponent.placePenguin(gt);
      if (candidatePos == null) {
        // player failed to provide an appropriate position
        invalidPlayer(gs, currPlayer, currPComponent,failures);
      }
      else {
        try {
          gs.placeAvatar(candidatePos, currPlayer);
        } catch (IllegalArgumentException iae) {
          // player made an illegal move
          invalidPlayer(gs, currPlayer, currPComponent, cheaters);
        }

        // Logic will need to be added here for invalid communication-related behavior
      }

      gs.setNextPlayer();
      if (gs.getPenguins().size() == penguinsPerPlayer * numPlayers) {
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
  private void doPlayingPhase() {
    while (this.phase == GamePhase.PLAYING) {
      GameState gs = this.gt.getGameState();
      Player currPlayer = gs.getCurrentPlayer();
      Penguin.PenguinColor currColor = currPlayer.getColor();
      IPlayer currPComponent = playerMap.get(currColor);

      Action currTurn = currPComponent.takeTurn(gt);
      if (currTurn == null) {
        // player failed to provide an appropriate action
        invalidPlayer(gs, currPlayer, currPComponent, failures);
        gs.setNextPlayer();
      }
      else {
        try {
          GameTree child = gt.lookAhead(currTurn);
          this.gt = child;
        } catch (IllegalStateException ise) {
          // player made an illegal move
          invalidPlayer(gs, currPlayer, currPComponent, cheaters);
          gs.setNextPlayer();
        }
        // Logic will need to be added here for invalid communication-related behavior
      }
      if (!this.gt.getGameState().movesPossible()) {
        this.phase = GamePhase.END;
        setWinningPlayers();
      }
    }
  }

  /**
   * Handles the response to either failing or invalid behavior by removing the player from all
   * of the places where it is used, from the GameState to the data representations in the
   * Referee. Moves the player component into the provided list (failures/cheaters).
   * Decrements the number of players actively playing in the game.
   * @param gs The GameState to remove the player (its penguins) from.
   * @param p The Player object to remove from the GameState.
   * @param pcomp The component (object implementing IPLayer interface) to mark as failing or
   *              cheating in this Referee.
   * @param list The list of player components (should be failures/cheaters) to add the component
   *            to.
   */
  private void invalidPlayer(GameState gs, Player p, IPlayer pcomp, List<IPlayer> list) {
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
      IPlayer pcomp = playerMap.get(p.getColor());
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