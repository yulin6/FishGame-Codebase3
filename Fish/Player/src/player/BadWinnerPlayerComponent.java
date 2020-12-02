package player;

import game.model.Action;
import game.model.GameTreeNode;
import game.model.Penguin;
import game.model.Place;

/**
 * Player component used in unit tests for milestone 8, plays the game correctly and naively,
 * then fails when accepting a win.
 */
public class BadWinnerPlayerComponent implements IPlayerComponent {
  private final IStrategy strategy;
  private final int age;

  /**
   * Creates a new BadWinnerPlayerComponent with the existing Strategy implementation, and age.
   * @param age The age of the player component.
   */
  public BadWinnerPlayerComponent(int age) {
    this.strategy = new Strategy();
    this.age = age;
  }

  @Override
  public void joinTournament() {
    // No behavior; unit-testing-use player
  }

  @Override
  public void leaveTournament(Boolean winner) {
    throw new IllegalArgumentException("I'm a player component who is a sore winner.");
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
    return strategy.getMinMaxAction(gt, 1);
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
    return Penguin.PenguinColor.RED; // Not logically correct, but it's not used
  }
}