package player;

import game.model.*;

/**
 * See IPlayer.java and PlayerComponent.java for documentation on what a player component must
 * fulfill.
 *
 * This is a player component that returns unusable (null) values when called in order to
 * generate runtime errors in Fish gameplay ("failing" in the format of its answer).
 */
public class NullReturnPlayerComponent implements IPlayerComponent {

  /**
   * Constructor for a failing player. Used to test player components that return null (an
   * abnormal condition).
   */
  public NullReturnPlayerComponent() {
    // Nothing to construct
  }

  @Override
  public void joinTournament() {
    // No behavior; fails by returning nulls
  }

  @Override
  public void leaveTournament(Boolean winner) {
    // No behavior; fails by returning nulls
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
