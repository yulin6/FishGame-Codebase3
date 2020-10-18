package game.model;

import java.awt.Graphics;

/**
 * A state implementing the IState interface that expresses upon being returned that the action
 * generating this state was not legal, as it placed the game into an illegal state.
 */
public class IllegalState implements IState {
  @Override
  public void placeAvatar(BoardPosition bp, Player p) {
    throw new IllegalStateException("Cannot place avatars onto an illegal game state.");
  }

  @Override
  public void moveAvatar(BoardPosition to, BoardPosition from, Player p) {
    throw new IllegalStateException("Cannot move avatars in an illegal game state.");
  }

  @Override
  public boolean movesPossible() {
    throw new IllegalStateException("Cannot check possibility for moves of an illegal game state.");
  }

  @Override
  public void render(Graphics g) {
    throw new IllegalStateException("Cannot render an illegal game state.");
  }

  @Override
  public Player getCurrentPlayer() {
    throw new IllegalStateException("Cannot get current player of an illegal game state.");
  }

  @Override
  public void removePlayer(Player p) {
    throw new IllegalStateException("Cannot remove player from an illegal game state.");
  }

  @Override
  public void setNextPlayer() {
    throw new IllegalStateException("Cannot set next player of an illegal game state.");
  }

  @Override
  public Penguin getPenguinAtPosn(BoardPosition bp) {
    throw new IllegalStateException("Cannot get penguins from an illegal game state.");
  }
}
