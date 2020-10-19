package game.model;

/**
 * Class representing a player's choice to pass (only allowed if the player cannot move any
 * penguins on the board).
 */
public class Pass implements Action {

  private Player p;

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
}
