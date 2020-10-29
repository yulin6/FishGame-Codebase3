package game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class to represent a tree of all possible game states resulting from an initial game state,
 * which should be the state of the game immediately after all penguins have been placed onto
 * the game board. A GameTree stores the GameState object from which it was made, which
 * represent the current state at the root node of the tree; GameState is as described in
 * GameState.java. The children HashMap connects this GameTree nodes to all of the child
 * GameTree nodes by associating the child with the action that produces the child.
 *
 * The constructor initializes the children mapping to null. It is generated once when
 * getAllChidlren is called the first time. This is how we handle "lazily" generating the tree.
 *
 * During running of the game, there are three states that each GameTree node can represent:
 * player-can-move, player-cannot-move, and game-is-over. If there are one or more Move Actions in
 * the children map, this GameTree node is in a player-can-move state. If there is only one Pass
 * Action in the children map, the GameTree node is in a player-cannot-move state. If this GameTree
 * node represents a game-is-over status, this node is a leaf node, and the children map is empty.
 */
public class GameTree {
  private final GameState state;
  private HashMap<Action, GameTree> children;

  /**
   * Constructor for a GameTree. Takes an initial game state and makes a copy of it to be stored
   * as the root state for this tree. The GameTree represents a complete tree for a game state
   * after which all penguins have been added. The children HashMap is initialized to null.
   * @param root The state that represents the root of this tree.
   */
  public GameTree(GameState root) {
    state = new GameState(root);
    children = null;
  }

  /**
   * Gets the GameState in this GameTree.
   * @return the GameState that is in this GameTree.
   */
  public GameState getGameState() {
    return this.state;
  }

  /**
   * Given a potential action (the action may be invalid, and a action can either be a penguin move
   * or a pass), returns the resultant tree node that represents the state of the game after that
   * action has been performed.
   * @param a The action to apply to the state represented by this GameTree.
   * @return a child GameTree node that represents the state after the move has been performed;
   * if the move was illegal, throws an IllegalStateException.
   */
  public GameTree lookAhead(Action a) {
    GameState copy = new GameState(this.state);
    boolean isValid = isLegal(copy, a);
    if (isValid) {
      doAction(copy, a);
      return new GameTree(copy);
    }
    else {
      throw new IllegalStateException("Generated an illegal game state as a result of the move.");
    }
  }

  /**
   * Applies a function to all children of this GameTree node, and returns a list of GameTree
   * nodes representing the child nodes of this node after the function is applied.
   * @param func Function to apply to the list of GameTree children of this root node; a
   *             Consumer is a generic interface representing functions accepting 1 input type
   *             and 0 output types (void functions).
   * @return the list of child nodes that result from the given function being applied to each of
   * the children of this tree node.
   */
  public List<GameTree> applyAllChildren(Consumer<List<GameTree>> func) {
    List<GameTree> children = this.getAllChildren();
    func.accept(children);
    return children;
  }

  /**
   * Gets all the children of this GameTree's root node.
   * If the mapping of Actions to child GameTree nodes is null, this function first calls
   * generateChildren() to generate the children mapping of Actions to child GameTree nodes. This
   * should only happen once per GameTree object.
   * @return The list of GameTree objects representing the children of this GameTree.
   */
  private List<GameTree> getAllChildren() {
    if(children == null) {
      generateChildren();
    }
    return new ArrayList<>(children.values());
  }

  /**
   * Creates the mapping of Actions to child GameTree nodes for this GameTree node. If there are
   * no moves possible on the board, the children mapping is empty, making this GameTree node a leaf
   * node.
   * If there are one or more Move Actions in the children map, this GameTree node is in a
   * player-can-move state. If there is only one Pass Action in the children map, the GameTree node
   * is in a player-cannot-move state.
   */
  private void generateChildren() {
    if(!this.state.movesPossible()) {
      children = new HashMap<>();
    }
    for(Action a: this.state.getPossibleActions()) {
      GameState nextState = new GameState(this.state);
      this.doAction(nextState, a);
      children.put(a, new GameTree(nextState));
    }
  }

  /**
   * Checks the legality of a given action, given a GameState and an Action. Action objects have
   * players associated with them, so checking if the list of currently possible actions contains
   * the passed-in action suffices.
   * @param g The GameState to be checked if the action is legal on.
   * @param a The action to perform on the GameState - either a Move or a Pass.
   * @return Whether or not the action could be performed on the given game state.
   */
  public boolean isLegal(GameState g, Action a) {
    if (a != null) {
      return g.getPossibleActions().contains(a);
    }
    else {
      return false;
    }
  }

  /**
   * Applies a player's action to a passed-in GameState object. Returns true if successful, false
   * if not successful. A copy is not made of the passed-in GameState, so it should be
   * copy-constructed before the use of this function.
   * @param g The GameState to do the action on.
   * @param a The action to perform on the GameState - either a Move or a Pass.
   */
  private void doAction(GameState g, Action a) {
    if (isLegal(g, a)) {
      a.perform(g);
    }
  }
}
