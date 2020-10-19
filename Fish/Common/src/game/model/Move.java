package game.model;

/**
 * Class representing a player's choice to move a penguin from one board location to another
 * (only valid if the player can make the move, the validity of which is checked in
 * Board/GameState/etc.).
 */
public class Move implements Action {

  private BoardPosition from;
  private BoardPosition to;
  private Player p;

  public Move(BoardPosition from, BoardPosition to, Player p) {
    this.from = from;
    this.to = to;
    this.p = p;
  }

  @Override
  public void perform(GameState g) {
    //TODO: Implement
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
}
