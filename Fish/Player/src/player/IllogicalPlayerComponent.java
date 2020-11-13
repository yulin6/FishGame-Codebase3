package player;

import game.model.*;

/**
 * See IPlayer.java and PlayerComponent.java for documentation on what a player component must
 * fulfill.
 *
 * This is a player component that returns trivial (and wrong) feedback when called in order to
 * generate logical errors in Fish gameplay ("cheating" behavior).
 */
public class IllogicalPlayerComponent implements IPlayerComponent {

  /**
   * Constructor for a cheating player.
   */
  public IllogicalPlayerComponent() {

  }

  @Override
  public void startPlaying(Penguin.PenguinColor color) {
    // do nothing
  }

  @Override
  public Place placePenguin(GameTreeNode gt) {
    return new Place(new BoardPosition(-1, 0), new Player(0, Penguin.PenguinColor.RED));
  }

  @Override
  public Action takeTurn(GameTreeNode gt) {
    return new Pass(gt.getGameState().getCurrentPlayer());
  }

  @Override
  public void finishPlaying() {
    // do nothing
  }

  @Override
  public int getAge() {
    return 0;
  }

  @Override
  public Penguin.PenguinColor getColor() {
    return Penguin.PenguinColor.BLACK;
  }
}
