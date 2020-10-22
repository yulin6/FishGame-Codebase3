package game.model;

import java.util.Objects;

/**
 * Class representing a player's choice to pass (only allowed if the player cannot move any
 * penguins on the board).
 * Contains the player making this pass action.
 */
public class Pass implements Action {

  private final Player p;

  /**
   * Constructor for a Pass object.
   * @param p The player who is making the pass.
   */
  public Pass(Player p) {
    this.p = p;
  }

  @Override
  public void perform(GameState g) {
    g.setNextPlayer();
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Pass) {
      return this.p.equals(((Pass) obj).p);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(p);
  }
}
