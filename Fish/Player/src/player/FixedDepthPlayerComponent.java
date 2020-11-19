package player;


import game.model.Action;
import game.model.GameTreeNode;
import game.model.Penguin;
import game.model.Place;

/**
 * Player component used in the testing task for milestone 8, which always searches at a given
 * depth in the minimax-action generating strategy.
 */
public class FixedDepthPlayerComponent implements IPlayerComponent {
  private final IStrategy strategy;
  private final int age;
  private final int depth;
  private final Penguin.PenguinColor color;

  /**
   * Creates a new FixedDepthPlayerComponent with the existing Strategy implementation, an
   * integer value represent age, and an integer value for depth to predict at.
   * @param age The age of the player represented by this player component.
   * @param depth The depth at which to look for minimax actions with.
   * @param color The color that is assigned to this player component.
   */
  public FixedDepthPlayerComponent(int age, int depth, Penguin.PenguinColor color) {
    this.strategy = new Strategy();
    this.age = age;
    this.depth = depth;
    this.color = color;
  }

  @Override
  public void joinTournament() {
    // No behavior; integration-testing-use player
  }

  @Override
  public void leaveTournament() {
    // No behavior; integration-testing-use player
  }

  @Override
  public void startPlaying(Penguin.PenguinColor color) {
    // do nothing
  }

  @Override
  public Place placePenguin(GameTreeNode gt) {
    return new Place(strategy.placePenguin(gt), gt.getGameState().getCurrentPlayer());
  }

  @Override
  public Action takeTurn(GameTreeNode gt) {
    return strategy.getMinMaxAction(gt, depth);
  }

  @Override
  public void finishPlaying() {
    // do nothing
  }

  @Override
  public int getAge() {
    return this.age;
  }

  @Override
  public Penguin.PenguinColor getColor() {
    return this.color;
  }
}
