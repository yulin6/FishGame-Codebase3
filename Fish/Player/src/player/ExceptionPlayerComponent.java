package player;

import game.model.*;

/**
 * See IPlayer.java and PlayerComponent.java for documentation on what a player component must
 * fulfill.
 *
 * This is a player component that throws exceptions when called, used to test functionality in
 * Referee objects to ensure they appropriately handle exceptions thrown in player components
 * (which cannot be inherently trusted, since they're made by untrusted sources).
 */
public class ExceptionPlayerComponent implements IPlayerComponent {
  private final boolean throwInGetAge;
  private final boolean throwInJoinTournament;

  /**
   * Constructor for an exception-throwing player.
   * @param throwInGetAge Boolean to throw in getAge or not. If true, will fail in getAge, during
   *                     constructor of a Referee; otherwise, makes it past construction,
   *                      allowing for manipulation of the Referee for testing.
   */
  public ExceptionPlayerComponent(boolean throwInGetAge, boolean throwInJoinTournament) {
    this.throwInGetAge = throwInGetAge;
    this.throwInJoinTournament = throwInJoinTournament;
  }

  @Override
  public void joinTournament() {
    if (throwInJoinTournament) {
      throw new IllegalArgumentException("Dummy exception for testing - joinTournament");
    }
  }

  @Override
  public void leaveTournament() {
    throw new IllegalArgumentException("Dummy exception for testing - leaveTournament");
  }

  @Override
  public void startPlaying(Penguin.PenguinColor color) {
    throw new IllegalArgumentException("Dummy exception for testing - startPlaying");
  }

  @Override
  public Place placePenguin(GameTreeNode gt) {
    throw new IllegalArgumentException("Dummy exception for testing - placePenguin");
  }

  @Override
  public Action takeTurn(GameTreeNode gt) {
    throw new IllegalArgumentException("Dummy exception for testing - takeTurn");
  }

  @Override
  public void finishPlaying() {
    throw new IllegalArgumentException("Dummy exception for testing - finishPlaying");
  }

  @Override
  public int getAge() {
    if (throwInGetAge) {
      throw new IllegalArgumentException("Dummy exception for testing - getAge");
    }
    return 0;
  }

  @Override
  public Penguin.PenguinColor getColor() {
    throw new IllegalArgumentException("Dummy exception for testing - getColor");
  }
}
