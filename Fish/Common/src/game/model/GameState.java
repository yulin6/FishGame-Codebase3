package game.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to represent a game state for a game of Fish.
 * Contains:
 * - state of the board for the game to be played with
 * - current placements of penguins
 * - information about the players in the game
 * - order in which they play TODO this
 */
public class GameState implements IState {
  private IBoard board;
  private HashMap<BoardPosition, Penguin> penguins;
  private ArrayList<Player> players;
  // TODO: determine data storage for player order

  public GameState(int numPlayers) {
    board = null;
    penguins = new HashMap<>();
    players = new ArrayList<>();
  }

  @Override
  public void placeAvatar(BoardPosition bp, Player p) {
    if (penguins.containsKey(bp)) {
      throw new IllegalArgumentException("Cannot place avatar here, already contains one.");
    }
    // input checking for board positions validity
    // add to list of avatar positions if valid
  }

  @Override
  public void moveAvatar(BoardPosition to, BoardPosition from, Player p) {
    // check board position validity
    // check that from has an avatar & to does not
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
}
