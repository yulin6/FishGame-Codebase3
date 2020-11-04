package player;

import java.util.Random;

import game.model.Action;
import game.model.BoardPosition;
import game.model.GameTree;
import game.model.Penguin;

/**
 * A PlayerComponent composes
 * - a strategy component of the IStrategy type, used to perform the decision-making logic for
 *   playing a game of Fish
 * - a color, initially null as it is unassigned, representing the player's assigned color within
 *   the game of Fish
 * - an age, which is communicated to components such as the tournament manager as necessary to
 *   allow it to make decisions regarding player order, etc. within games of Fish
 * - some constants representing numbers of turns, which are used in the implementation of this
 *   player component in takeTurn
 * - A Random object, which serves as the random number generator for decision making. This is
 *   specifically used in takeTurn.
 *
 * The purpose of a PlayerComponent is to represent an actual player of the game, who will have
 * actions requested of them from the referee, such as placing penguins in the placement phase and
 * moving penguins in the game playing phase.
 */
public class PlayerComponent implements IPlayer {
  private final IStrategy strategy;
  private Penguin.PenguinColor color;
  private final int age;
  private static final int MIN_LOOKAHEAD = 1;
  private static final int MAX_LOOKAHEAD = 3;
  private Random rng;

  /**
   * Creates a new PlayerComponent with the existing Strategy implementation of a strategy
   * component, as well as a given age that represents the age of the player.
   * @param age The age of the player represented by this player component.
   * @param seed the seed used for the Random object
   */
  public PlayerComponent(int age, int seed) {
    this.strategy = new Strategy();
    this.color = null;
    this.age = age;
    rng = new Random(seed);
  }

  @Override
  public void startPlaying(Penguin.PenguinColor color) {
    if (this.color != null) {
     throw new IllegalArgumentException("Already playing a game - cannot start playing another.");
    } else {
      this.color = color;
    }
  }

  @Override
  public BoardPosition placePenguin(GameTree gt) {
    return this.strategy.placePenguin(gt);
  }

  // In this implementation of taking a player's turn, the Strategy component requires a number
  // of turns to look ahead for its getMinMaxAction method, so it is randomly generated between
  // constants of this class.
  @Override
  public Action takeTurn(GameTree gt) {
    int random = rng.nextInt(MAX_LOOKAHEAD) + MIN_LOOKAHEAD;
    return this.strategy.getMinMaxAction(gt, random);
  }

  @Override
  public void finishPlaying() {
    if (this.color == null) {
      throw new IllegalArgumentException("Cannot finish playing a game; not playing one.");
    } else {
      this.color = null;
    }
  }

  @Override
  public int getAge() {
    return age;
  }

  @Override
  public Penguin.PenguinColor getColor() {
    if (this.color == null) {
      throw new IllegalArgumentException("Cannot get color of a player not playing a game.");
    }
    else {
      return color;
    }
  }
}
