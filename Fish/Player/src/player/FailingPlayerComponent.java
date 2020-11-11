package player;

import game.model.*;

/**
 * See IPlayer.java and PlayerComponent.java for documentation on what a player component must
 * fulfill.
 *
 * This is a player component that returns unusable feedback when called in order to
 * generate runtime errors in Fish gameplay ("failing" behavior).
 */
public class FailingPlayerComponent implements IPlayerComponent {

  /**
   * Constructor for a failing player.
   */
  public FailingPlayerComponent() {

  }

  @Override
  public void startPlaying(Penguin.PenguinColor color) {
    // do nothing
  }

  @Override
  public Place placePenguin(GameTreeNode gt) {
    return null;
  }

  @Override
  public Action takeTurn(GameTreeNode gt) {
    return null;
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
    return null;
  }
}
