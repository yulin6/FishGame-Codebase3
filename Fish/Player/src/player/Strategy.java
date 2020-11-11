package player;


import java.util.ArrayList;
import java.util.HashMap;

import game.model.Action;
import game.model.Move;
import game.model.Pass;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTreeNode;
import game.model.IBoard;
import game.model.Penguin;

/**
 * A Strategy contains the functionality that allows players to select the placement of penguins
 * based on the first available position on the game board following a zig zag pattern (traversing
 * left to right through each row, moving to the next row if all spaces in the row are ineligible).
 * Please see Board documentation for interpretation of rows and columns.
 *
 * A Strategy also contains the functionality that allows players to select the next
 * move that will allow for a minimal maximum gain given N > 0 number of the performing player's
 * turns to look ahead. A minimal maximum gain after N turns is the highest score a player can earn
 * after playing the specified number of turns if all other players pick one of the moves that
 * minimize the player's gain.
 */
public class Strategy implements IStrategy {

  @Override
  public BoardPosition placePenguin(GameTreeNode gt) {
    GameState gs = gt.getGameState();
    IBoard b = gs.getBoard();

    for(int r = 0; r < b.getRows(); r++) {
      for(int c = 0; c < b.getCols(); c++) {
        BoardPosition bp = new BoardPosition(r, c);
        if(!b.getSpace(bp).isHole() && !gs.isPenguinAtPosn(bp)) {
          //gs.placeAvatar(bp, gs.getCurrentPlayer());
          //gs.setNextPlayer();
          return bp;
        }
      }
    }
    throw new IllegalArgumentException("Game board needs to accommodate all penguins!");
  }

  @Override
  public Action getMinMaxAction(GameTreeNode gt, int numTurns) {
    if (numTurns <= 0) {
      throw new IllegalArgumentException("Need to look ahead at least 1 turn.");
    }

    HashMap<Action, Integer> actionToFish = new HashMap<>();

    GameState gs = gt.getGameState();
    ArrayList<Action> actions = gs.getPossibleActions();
    if(actions.get(0).equals(new Pass(gs.getCurrentPlayer()))) {
      return actions.get(0);
    }
    for(Action a : actions) {
      int maxFish;
      Move m = (Move)a;
      BoardPosition source = m.getStart();
      if(numTurns > 1) {
        maxFish = gs.getBoard().getSpace(source).getNumFish() + getMinMaxValue(gt.lookAhead(a),
              numTurns - 1, gt.getGameState().getCurrentPlayer().getColor());
      }
      else {
        maxFish = gs.getBoard().getSpace(source).getNumFish();
      }
      actionToFish.put(a, maxFish);
    }

    ArrayList<Action> highestActions = findBestActions(actionToFish);
    if(highestActions.size() > 1) {
      return doTiebreak(highestActions);
    }
    else {
      return highestActions.get(0);
    }
  }

  /**
   * Find the list of best actions given a mapping of actions to their calculated minimax gains.
   * @param actionToFish The mapping of Action (Move/Pass) objects to their calculated gain.
   * @return The optimal action (maximal minimax gain).
   */
  private ArrayList<Action> findBestActions(HashMap<Action, Integer> actionToFish) {
    int highestMin = 0;
    ArrayList<Action> highestActions = new ArrayList<>();
    for(Action a : actionToFish.keySet()) {
      int fish = actionToFish.get(a);
      if(fish > highestMin) {
        highestMin = fish;
        highestActions.clear();
        highestActions.add(a);
      }
      else if(fish == highestMin) {
        highestActions.add(a);
      }
    }
    return highestActions;
  }

  /**
   * Performs a tiebreaker on a list of Actions that would provide the same minimal gain based on
   * lowest row number of start --> lowest column number of start --> lowest row number of
   * destination --> lowest column number of destination. It can be assumed that all the actions
   * in the ArrayList are moves since the calling function checks for Passes beforehand
   * @param highestActions The actions that provide the same minimal gain
   * @return The action that fulfills the lowest row/col start/dest as explained above best.
   */
  private Action doTiebreak(ArrayList<Action> highestActions) {
    Move tiebreakingAction = null;
    for(Action a : highestActions) {
      Move m = (Move)a;
      BoardPosition start = m.getStart();
      BoardPosition destination = m.getDestination();

      if(tiebreakingAction == null) {
        tiebreakingAction = m;
        continue;
      }

      BoardPosition lowestStart = tiebreakingAction.getStart();
      BoardPosition lowestDest = tiebreakingAction.getDestination();

      if(start.getRow() < lowestStart.getRow()) {
        tiebreakingAction = m;
      }
      else if(start.getRow() == lowestStart.getRow()) {
        if(start.getCol() < lowestStart.getCol()) {
          tiebreakingAction = m;
        }
        else if(start.getCol() == lowestStart.getCol()) {
          if(destination.getRow() < lowestDest.getRow()) {
            tiebreakingAction = m;
          }
          else if(destination.getRow() == lowestDest.getRow()) {
            if(destination.getCol() < lowestDest.getCol()) {
              tiebreakingAction = m;
            }
          }
        }
      }
    }
    return tiebreakingAction;
  }

  /**
   * Gets the minimal maximum value of a given tree and number of turns ahead for a given
   * player's color. Looks through all possible options for actions by both players and
   * opponents, assuming opponents will minimize the player's gain and the player will maximize
   * its own gain.
   * @param gt The root GameTree object to look through some amount of turns for.
   * @param numTurns The number of turns to look ahead (minimum 1).
   * @param c The color of the player to find the minimax gain of (the color that is not an
   *          "enemy" color.
   * @return The best minimax gain achievable from the tree in the given amount of turns.
   */
  private int getMinMaxValue(GameTreeNode gt, int numTurns, Penguin.PenguinColor c) {
    GameState gs = gt.getGameState();

    if(c == gs.getCurrentPlayer().getColor()) {
      if(gs.getPossibleActions().get(0) instanceof Pass) {
        return 0;
      }
      else {
        if(numTurns > 1) {
          return findMinOrMax(gt, numTurns, c, true);
        }
        else if(numTurns == 1) {
          return getMaxFish(gt);
        }
        throw new IllegalArgumentException("numTurns cannot be less than one!");
      }
    }

    else {
      return findMinOrMax(gt, numTurns, c, false);
    }
  }

  /**
   * Finds the minimum or maximum value of the possible actions looking ahead a given number of
   * turns, starting from the root state of the given GameTree. (If the current turn is an
   * opponent's turn, findMax should be false, and the minimum value of the child nodes is
   * return; findMax being true indicates it's the player's turn, and the maximum value should be
   * found).
   * @param gt The GameTree to search to find the appropriate minimax gain value.
   * @param numTurns The number of turns to look ahead.
   * @param c The player whose minimax gain should be determined.
   * @param findMax True if the maximum should be found, else false and it finds the minimum.
   * @return The highest or lowert value of minimax gain of the child nodes as determined by the
   * findMax parameter.
   */
  private int findMinOrMax(GameTreeNode gt, int numTurns, Penguin.PenguinColor c, boolean findMax) {
    int current = findMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    GameState gs = gt.getGameState();
    ArrayList<Action> possibleMoves = gs.getPossibleActions();
    IBoard b = gs.getBoard();
    numTurns = findMax ? numTurns - 1 : numTurns;

    for (Action a : possibleMoves) {
      int potential = getMinMaxValue(gt.lookAhead(a), numTurns, c);
      if (findMax) {
        Move m = (Move)a;
        int now = b.getSpace(m.getStart()).getNumFish();
        if(now + potential > current) {
          current = now + potential;
        }
      }
      else {
        if(potential < current) {
          current = potential;
        }
      }
    }
    return current;

  }

  /**
   * Gets the maximum number of fish attainable by any single move at the current phase of the
   * game. Checks the source tiles, not the destination tiles, for number of fish, as fish are
   * not obtained until a penguin moves off a given tile.
   * @param gt The GameTree object to look through to find the max number of fish from.
   * @return The maximum number of fish that can be obtained.
   */
  private int getMaxFish(GameTreeNode gt) {
    int maxFish = 0;
    GameState gs = gt.getGameState();
    ArrayList<Action> possibleMoves = gs.getPossibleActions();
    IBoard b = gs.getBoard();

    for(Action a : possibleMoves) {
      Move m = (Move)a;
      BoardPosition source = m.getStart();
      int fish = b.getSpace(source).getNumFish();
      if(fish > maxFish) {
        maxFish = fish;
      }
    }

    return maxFish;
  }
}