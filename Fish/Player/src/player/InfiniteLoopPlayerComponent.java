package player;

import game.model.*;

/**
 * See IPlayer.java and PlayerComponent.java for documentation on what a player component must
 * fulfill.
 *
 * This is a player component that gets stuck in an infinite loop when it tries to perform some
 * behavior requested of it by the Referee, used to test that the Referee handles
 * infinitely-looping player components properly.
 */
public class InfiniteLoopPlayerComponent implements IPlayerComponent {
  private final boolean loopInGetAge;
  private final boolean loopInJoinTournament;

  /**
   * Constructor for a player with infinite-looping behavior implemented.
   * @param loopInGetAge Boolean to loop in getAge or not. If true, will fail in getAge, during
   *                     constructor of a Referee; otherwise, makes it past construction,
   *                      allowing for manipulation of the Referee for testing.
   */
  public InfiniteLoopPlayerComponent(boolean loopInGetAge, boolean loopInJoinTournament) {
    this.loopInGetAge = loopInGetAge;
    this.loopInJoinTournament = loopInJoinTournament;
  }

  @Override
  public void joinTournament() {
    while (loopInJoinTournament) {

    }
  }

  @Override
  public void leaveTournament() {
    while (true) {

    }
  }

  @Override
  public void startPlaying(Penguin.PenguinColor color) {
    while (true) {

    }
  }

  @Override
  public Place placePenguin(GameTreeNode gt) {
    while (true) {

    }
  }

  @Override
  public Action takeTurn(GameTreeNode gt) {
    while (true) {

    }
  }

  @Override
  public void finishPlaying() {
    while (true) {

    }
  }

  @Override
  public int getAge() {
    while (loopInGetAge) {

    }
    return 0;
  }

  @Override
  public Penguin.PenguinColor getColor() {
    while (true) {

    }
  }
}
