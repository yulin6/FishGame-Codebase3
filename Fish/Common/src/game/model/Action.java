package game.model;

/**
 * Interface to represent possible choices made by a player at a given turn - such as moving a
 * penguin, or passing if the player is unable to move any penguins.
 */
public interface Action {

  /**
   * Performs the action that the Action represents on the given GameState. Moves penguins,
   * advances current player, etc. as necessary on the GameState.
   * @param g The GameState to apply the Action to.
   */
  void perform(GameState g);

  /**
   * Equality checker between two actions. Different Move types are not equal.
   * @param obj The object to be compared against.
   * @return Whether this action and the given object are equal.
   */
  @Override
  boolean equals(Object obj);

}
