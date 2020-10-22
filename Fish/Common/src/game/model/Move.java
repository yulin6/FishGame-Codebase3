package game.model;

import java.util.Objects;

/**
 * Class representing a player's choice to move a penguin from one board location to another
 * (only valid if the player can make the move, the validity of which is checked in
 * Board/GameState/etc.).
 * Contains the board positions the move is to and from, as well as the player making the move.
 */
public class Move implements Action {

  private final BoardPosition from;
  private final BoardPosition to;
  private final Player p;

  /**
   * Constructs a Move action, which composes the source and destination positions, as well as
   * the Player making the move.
   * @param to The board position the move is being made to.
   * @param from The board position the move is being made from.
   * @param p The player making the move.
   */
  public Move(BoardPosition to, BoardPosition from, Player p) {
    this.to = to;
    this.from = from;
    this.p = p;
  }

  @Override
  public void perform(GameState g) {
    g.moveAvatar(to, from, p);
    g.setNextPlayer();
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Move) {
      Move m = (Move)obj;
      return (this.from.equals(m.from)
              && this.to.equals(m.to)
              && this.p.equals(m.p));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, p);
  }
}
