package player;

import game.model.Action;
import game.model.BoardPosition;
import game.model.GameTree;
import game.model.Pass;
import game.model.Penguin;

/**
 * See IPlayer.java and PlayerComponent.java for documentation on what a player component must
 * fulfill.
 *
 * This is a player component that returns trivial (and wrong) feedback when called in order to
 * generate logical errors in Fish gameplay ("cheating" behavior).
 */
public class IllogicalPlayerComponent implements IPlayer {

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
  public BoardPosition placePenguin(GameTree gt) {
    return new BoardPosition(0, 0);
  }

  @Override
  public Action takeTurn(GameTree gt) {
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
