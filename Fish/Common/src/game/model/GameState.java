package game.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class to represent a game state for a game of Fish.
 * Contains:
 * - state of the board for the game to be played with
 * - current placements of penguins
 * - information about the players in the game
 * - order in which they play
 */
public class GameState implements IState {
  private final IBoard board;
  private final HashMap<BoardPosition, Penguin> penguins;
  private final HashSet<Player> players;
  private Player currentPlayer;

  public GameState(HashSet<Player> playerSet, IBoard b) {
    board = b;
    penguins = new HashMap<>();
    players = new HashSet<>();
    players.addAll(playerSet);
    checkPlayerColors();
    setNextPlayer();
  }

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
    if(penguins.containsKey(from) && !penguins.containsKey(to)) {
      Penguin penguin = penguins.get(from);
      if(penguin.getColor() == p.getColor()) {
          penguins.put(to, penguin);
          board.removeTile(from);
          penguins.remove(from);
      }
      else {
        throw new IllegalArgumentException("Player cannot move another player's penguins!");
      }
    }
    else {
      throw new IllegalArgumentException("There is either a penguin at destination or not one at" +
              "the source position.");
    }
  }

  @Override
  public boolean movesPossible() {
    ArrayList<BoardPosition> penPositions = new ArrayList<>();
    penPositions.addAll(penguins.keySet());
    for(BoardPosition bp : penPositions) {
      if(!board.getValidMoves(bp, penPositions).isEmpty()) {
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
  public void removePlayer(Player p) {
    players.remove(p);
  }

  @Override
  public void setNextPlayer() {
    if(currentPlayer == null) {
      currentPlayer = getYoungestPlayer();
    }
    else {
      currentPlayer = getNextPlayer();
    }
  }

  @Override
  public Penguin getPenguinAtPosn(BoardPosition bp) {
    if(penguins.containsKey(bp)) {
      return penguins.get(bp);
    }
    else {
      throw new IllegalArgumentException("No penguin at this board space!");
    }
  }

  /**
   * Gets the player that will move next based on increasing age.
   * @return The next player that will make a move
   */
  private Player getNextPlayer() {
    if(players.size() == 0) {
      throw new IllegalArgumentException("Cannot find next youngest player without any players!");
    }
    Player nextYoungest = null;
    for(Player p : players) {
        if (p.getAge() > currentPlayer.getAge()) {
          if (nextYoungest == null) {
            nextYoungest = p;
          }
          else if (p.getAge() < nextYoungest.getAge()) {
            nextYoungest = p;
          }
        }
    }
    if(nextYoungest == null) {
      return getYoungestPlayer();
    }
    return nextYoungest;
  }

  /**
   * Gets the youngest player in the game
   * @return The player in the game with the lowest age
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
      }
    return youngest;
  }

}
