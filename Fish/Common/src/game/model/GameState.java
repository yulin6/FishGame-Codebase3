package game.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HashSet;
import java.util.Map;

/**
 * Class to represent a game state for a game of Fish.
 * Contains:
 * - state of the board for the game to be played with
 * - current placements of penguins
 * - information about the players in the game
 * - current player information, which is used to determine next in order of play
 * - a list of players that have already made a move, before the turn order returns to the top
 */
public class GameState implements IState {
  private final IBoard board;
  private final HashMap<BoardPosition, Penguin> penguins;
  private final HashSet<Player> players;
  private Player currentPlayer;
  private final HashSet<Player> movedPlayers;

  /**
   * Constructor for objects of GameState type. Takes a set of players as well as an IBoard that
   * represents the board state of the given game of Fish.
   * @param playerSet The set of players to be stored in the GameState.
   * @param b The board to be represented within this GameState.
   */
  public GameState(HashSet<Player> playerSet, IBoard b) {
    board = b;
    penguins = new HashMap<>();
    players = new HashSet<>();
    players.addAll(playerSet);
    movedPlayers = new HashSet<>();
    checkPlayerColors();
    setNextPlayer();
  }

  /**
   * Copy constructor for objects of the GameState type, to be used for checking and
   * returning future moves without actually changing the current state of the game.
   * @param g The GameState object to make a copy of.
   */
  public GameState(GameState g) {
    this.board = new Board((Board) g.board);
    this.penguins = new HashMap<>();
    for (Map.Entry<BoardPosition, Penguin> entry : g.penguins.entrySet()) {
      this.penguins.put(entry.getKey(), entry.getValue());
    }
    this.players = new HashSet<>();
    for (Player p : g.players) {
      this.players.add(new Player(p));
    }
    this.movedPlayers = new HashSet<>();
    for (Player p : g.movedPlayers) {
      this.movedPlayers.add(new Player(p));
    }
    this.currentPlayer = new Player(g.currentPlayer);
  }

  /**
   * Checks that the player colors of the GameState are valid, throwing an exception if not.
   */
  private void checkPlayerColors() {
    ArrayList<Penguin.PenguinColor> colors = new ArrayList<>();
    for(Player p : players) {
      if(colors.contains(p.getColor())) {
        throw new IllegalArgumentException("Duplicate color assigned to a player!");
      }
      else {
        colors.add(p.getColor());
      }
    }
  }

  @Override
  public void placeAvatar(BoardPosition bp, Player p) {
    if (penguins.containsKey(bp)) {
      throw new IllegalArgumentException("Cannot place avatar here, already contains one.");
    }
    if(!board.isValidPosn(bp)) {
      throw new IllegalArgumentException("Cannot place avatar outside the bounds of the board.");
    }
    if(board.getSpace(bp).isHole()) {
      throw new IllegalArgumentException("Cannot place avatar in a hole on the board.");
    }

    Penguin penguin = new Penguin(p.getColor());
    penguins.put(bp, penguin);
  }

  @Override
  public void moveAvatar(BoardPosition to, BoardPosition from, Player p) {
    // check board position validity
    if (!board.isValidPosn(to) || !board.isValidPosn(from)) {
      throw new IllegalArgumentException("To or from board positions are outside board bounds.");
    }
    if(board.getSpace(to).isHole() || board.getSpace(from).isHole()) {
      throw new IllegalArgumentException("To or from board positions are a hole on the board.");
    }
    // check that from has an avatar & to does not
    if (penguins.containsKey(from) && !penguins.containsKey(to)) {
      Penguin penguin = penguins.get(from);
      if (penguin.getColor() == p.getColor()) {
          penguins.put(to, penguin);
          for (Player curr : players) {
            if (curr.getColor() == currentPlayer.getColor()) {
              curr.addFish(board.getSpace(from).getNumFish());
              currentPlayer = curr;
            }
          }
          board.removeTile(from);
          penguins.remove(from);
      }
      else {
        throw new IllegalArgumentException("Player cannot move another player's penguins!");
      }
    }
    else {
      throw new IllegalArgumentException("There is either a penguin at destination or not one at" +
              " the source position.");
    }
  }

  @Override
  public boolean movesPossible() {
    ArrayList<BoardPosition> penPositions = new ArrayList<>(penguins.keySet());
    for (BoardPosition bp : penPositions) {
      if (!board.getValidMoves(bp, penPositions).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void render(Graphics g) {
    this.board.render(g);
    for (BoardPosition bp : penguins.keySet()) {
      penguins.get(bp).render(bp, g);
    }
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public IBoard getBoard() {
    return this.board;
  }

  @Override
  public void removePlayer(Player p) {
    for (BoardPosition bp : penguins.keySet()) {
      if (penguins.get(bp).getColor() == p.getColor()) {
        penguins.remove(bp);
      }
    }
    players.remove(p);
  }

  @Override
  public void setNextPlayer() {
    if (currentPlayer == null) {
      currentPlayer = getYoungestPlayer();
    }
    else {
      movedPlayers.add(currentPlayer);
      currentPlayer = getNextPlayer();
    }
  }

  @Override
  public Penguin getPenguinAtPosn(BoardPosition bp) {
    if(isPenguinAtPosn(bp)) {
      return penguins.get(bp);
    }
    else {
      throw new IllegalArgumentException("No penguin at this board space!");
    }
  }

  @Override
  public boolean isPenguinAtPosn(BoardPosition bp) {
    return penguins.containsKey(bp);
  }

  /**
   * Return a copy of the HashMap of penguins. BoardPositions and Penguins are immutable, so
   * it's safe to not copy-construct these.
   * @return A copy of the mapping of positions and penguins.
   */
  public HashMap<BoardPosition, Penguin> getPenguins() {
    return new HashMap<>(penguins);
  }

  @Override
  public ArrayList<Action> getPossibleActions() {
    ArrayList<Action> actions = new ArrayList<>();

    ArrayList<BoardPosition> sourcePositions = new ArrayList<>();
    for(BoardPosition bp : penguins.keySet()) {
      if(penguins.get(bp).getColor().equals(currentPlayer.getColor())) {
        sourcePositions.add(bp);
      }
    }
    ArrayList<BoardPosition> penPositions = new ArrayList<>(penguins.keySet());
    for (BoardPosition from : sourcePositions) {
      ArrayList<BoardPosition> destinations = board.getValidMoves(from, penPositions);
      for(BoardPosition to : destinations) {
        Action a = new Move(to, from, currentPlayer);
        actions.add(a);
      }
    }

    if(actions.isEmpty()) {
      actions.add(new Pass(currentPlayer));
    }

    return actions;
  }

  @Override
  public HashSet<Player> getPlayers() {
    return players;
  }

  /**
   * Gets the player that will move next based on increasing age.
   * @return The next player that will make a move
   */
  private Player getNextPlayer() {
    if(players.size() == 0) {
      throw new IllegalArgumentException("Cannot find next youngest player without any players!");
    }
    if (movedPlayers.containsAll(players)) {
      movedPlayers.clear();
      return getYoungestPlayer();
    }
    Player nextYoungest = null;
    for (Player p : players) {
      if (movedPlayers.contains(p)) {
        continue;
      }
      else if (nextYoungest == null) {
        nextYoungest = p;
      }
      else if (p.getAge() < nextYoungest.getAge()) {
        nextYoungest = p;
      } else if (p.getAge() == nextYoungest.getAge()) {
        if (p.getColor().tieCode < nextYoungest.getColor().tieCode) {
          nextYoungest = p;
        }
      }
    }
    return nextYoungest;
  }

  /**
   * Gets the youngest player in the game.
   * @return The player in the game with the lowest age.
   */
  private Player getYoungestPlayer() {
    if(players.size() == 0) {
      throw new IllegalArgumentException("Cannot find next youngest player without any players!");
    }
    Player youngest = null;
    for(Player p : players) {
        if (youngest == null) {
          youngest = p;
        }
        else if (p.getAge() < youngest.getAge()) {
          youngest = p;
        }
        else if (p.getAge() == youngest.getAge()) {
          if (p.getColor().tieCode < youngest.getColor().tieCode) {
            youngest = p;
          }
        }
      }
    return youngest;
  }
}
