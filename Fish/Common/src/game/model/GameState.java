package game.model;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class to represent a game state for a game of Fish.
 * Contains:
 * - state of the board for the game to be played with
 * - current placements of penguins
 * - information about the players in the game
 * - order in which they play TODO this
 */
public class GameState implements IState {
  private final IBoard board;
  private final HashMap<BoardPosition, Penguin> penguins;
  private final HashSet<Player> players;
  // TODO: determine data storage for player order

  public GameState(int numPlayers, IBoard b) {
    board = b;
    penguins = new HashMap<>();
    players = new HashSet<>();
  }

  @Override
  public void placeAvatar(BoardPosition bp, Player p) {
    if (penguins.containsKey(bp)) {
      throw new IllegalArgumentException("Cannot place avatar here, already contains one.");
    }
    // input checking for board positions validity
    if(!board.isValidPosn(bp)) {
      throw new IllegalArgumentException("Cannot place avatar outside the bounds of the board.");
    }
    if(board.getSpace(bp).isHole()) {
      throw new IllegalArgumentException("Cannot place avatar in a hole on the board.");
    }
    // add to list of avatar positions if valid
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
      if(penguins.get(from).getColor() == p.getColor()) {
        penguins.put(to, penguin);
        board.removeTile(from);
        penguins.remove(from);
      }
      else {
        throw new IllegalArgumentException("Player cannot move another player's penguins!");
      }
    }
    // find position in avatarPositions list & update
  }

  @Override
  public boolean movesPossible() {
    // iterate through all avatar positions and check valid moves on the board
    // if all valid moves arraylists returned are empty, no moves possible -> false
    // else true
    return true;
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
    // determine the current player based on...
    // need some way to track the current player in the gamestate
    return null;
  }

  @Override
  public void addPlayer(Player p) {
    players.add(p);
  }

  @Override
  public void removePlayer(Player p) {
    players.remove(p);
  }
}
