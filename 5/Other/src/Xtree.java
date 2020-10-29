import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import game.model.Action;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTree;
import game.model.Move;
import game.model.Pass;
import game.model.Player;

public class Xtree {

  public static void main(String[] args) {
    Gson gson = new GsonBuilder().create();
    String rawJson = parseInput(System.in);

    MovResQue mrq = gson.fromJson(rawJson, MovResQue.class);
    BoardPosition from = mrq.getFrom();
    BoardPosition to = mrq.getTo();

    GameState gs = mrq.getGameState();
    GameTree gt = new GameTree(gs);

    // Assume move is valid
    Move prevMove = new Move(to, from, gs.getCurrentPlayer());

    GameTree oneAhead = gt.lookAhead(prevMove);
    GameState futureState = oneAhead.getGameState();
    Player secondPlayer = futureState.getCurrentPlayer();

    // attempt to place a penguin from player 2
    ArrayList<Action> moves = futureState.getPossibleActions();
    if (moves.get(0) instanceof Pass) {
      System.out.println(gson.toJson(false));
      return;
    }

    HashMap<Xstate.DIRECTION, ArrayList<Move>> dirMoveMap = new HashMap<>();
    for (Xstate.DIRECTION d : Xstate.DIRECTION.values()) {
      dirMoveMap.put(d, new ArrayList<>());
    }

    for (Action a : moves) {
      Move m = (Move) a;
      BoardPosition secondFrom = m.getStart();

      for (Xstate.DIRECTION d : Xstate.DIRECTION.values()) {
        Move directionalMove = new Move(Xstate.generateMove(to, d), secondFrom, secondPlayer);
        if (moves.contains(directionalMove)) {
          dirMoveMap.get(d).add(directionalMove);
        }
      }
      /*
      Move north = new Move(
              Xstate.generateMove(to, Xstate.DIRECTION.N), secondFrom, secondPlayer);
      Move northeast = new Move(
              Xstate.generateMove(to, Xstate.DIRECTION.NE), secondFrom, secondPlayer);
      Move southeast = new Move(
              Xstate.generateMove(to, Xstate.DIRECTION.SE), secondFrom, secondPlayer);
      Move south = new Move(
              Xstate.generateMove(to, Xstate.DIRECTION.S), secondFrom, secondPlayer);
      Move southwest = new Move(
              Xstate.generateMove(to, Xstate.DIRECTION.SW), secondFrom, secondPlayer);
      Move northwest = new Move(
              Xstate.generateMove(to, Xstate.DIRECTION.NW), secondFrom, secondPlayer);
      if (moves.contains(north)) {
        dirMoveMap.get(Xstate.DIRECTION.N)
      } else if (moves.contains(northeast)) {
        viableMoves.add(northeast);
      } else if (moves.contains(southeast)) {
        viableMoves.add(southeast);
      } else if (moves.contains(south)) {
        viableMoves.add(south);
      } else if (moves.contains(southwest)) {
        viableMoves.add(southwest);
      } else if (moves.contains(northwest)) {
        viableMoves.add(northwest);
      }
      */

    }

    Move correctMove;
    if (!dirMoveMap.get(Xstate.DIRECTION.N).isEmpty()) {
      correctMove = doTiebreak(dirMoveMap.get(Xstate.DIRECTION.N));
    }
    else if (!dirMoveMap.get(Xstate.DIRECTION.NE).isEmpty()) {
      correctMove = doTiebreak(dirMoveMap.get(Xstate.DIRECTION.NE));
    }
    else if (!dirMoveMap.get(Xstate.DIRECTION.SE).isEmpty()) {
      correctMove = doTiebreak(dirMoveMap.get(Xstate.DIRECTION.SE));
    }
    else if (!dirMoveMap.get(Xstate.DIRECTION.S).isEmpty()) {
      correctMove = doTiebreak(dirMoveMap.get(Xstate.DIRECTION.S));
    }
    else if (!dirMoveMap.get(Xstate.DIRECTION.SW).isEmpty()) {
      correctMove = doTiebreak(dirMoveMap.get(Xstate.DIRECTION.SW));
    }
    else if (!dirMoveMap.get(Xstate.DIRECTION.NW).isEmpty()) {
      correctMove = doTiebreak(dirMoveMap.get(Xstate.DIRECTION.NW));
    }
    else {
      System.out.println(gson.toJson(false));
      return;
    }

    JsonArray out = new JsonArray();
    JsonArray src = new JsonArray();
    JsonArray dst = new JsonArray();
    src.add(correctMove.getStart().getRow());
    src.add(correctMove.getStart().getCol());
    dst.add(correctMove.getDestination().getRow());
    dst.add(correctMove.getDestination().getCol());
    out.add(src);
    out.add(dst);

    System.out.println(gson.toJson(out));
  }

  /**
   * Helper method to parse all JSON input from an input stream and create the appropriate JSON
   * objects to return.
   *
   * @param istream Input stream to read from.
   * @return raw JSON from the input stream as a String
   */
  private static String parseInput(InputStream istream) {
    // Open a scanner to accept all the JSON values
    // into one string to give to a parser
    Scanner input = new Scanner(istream);
    StringBuilder rawJson = new StringBuilder();
    while (input.hasNextLine()) {
      rawJson.append(input.nextLine());
      rawJson.append("\n");
    }

    return rawJson.toString();
  }

  /**
   * Gets the appropriate tie-breaking move from the list of moves that can move adjacent to the
   * tile that the previous player's penguin just moved to. The conditions are
   * lowest row number of start --> lowest column number of start --> lowest row number of
   * destination --> lowest column number of destination is the priority listing for tie breaking.
   * Should not ever be called on a list of Action objects, only Move objects.
   * @param moves The list of moves to search through and to tie-breaking on, if necessary.
   * @return The move that takes priority over all others in the list.
   */
  private static Move doTiebreak(ArrayList<Move> moves) {
    Move tiebreakingAction = null;
    for(Move m : moves) {
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
}

/*

Testing Task Create a test harness named xtree
The harness consumes its JSON input from STDIN and produces its results to STDOUT.
Create three tests and place them in the specified folder.

The tests are formulated as pairs of files: <n>-in.json, the input, and <n>-out.json,
the expected result, for an integer <n> greater or equal to 1.

Its inputs are objects with three fields:

    Move-Response-Query is
     { "state" : State,
       "from" : Position,
       "to" : Position }

    INTERPRETATION The object describes the current state and the move that the
    currently active player picked.
    CONSTRAINT The object is invalid, if the specified move is illegal in the given state.

Well-formed and Valid
You may assume that all inputs for your test harnesses will be well-formed JSON
and valid according to the homework descriptions.

Its expected output is the action that the next player can take to get a penguin to a
place that neighbors the one that the first player just conquered:

    Action is
    either
     false
    or
     [ Position, Position ]

    INTERPRETATION The array describes the opponent's move from the first
    position to the second; if the desired kind of move isn't possible, the
    harness delivers false.

The tie breaking needs a refinement for cases when two distinct penguins can move to the same spot:
if more than one position satisfies the "closeness" condition, a tie breaker algorithm picks
by the top-most row of the "from" position, the left-most column of the "from" position,
the top-most row of the "to" position, and the left-most column of the "to" position---in exactly this order.

Like in 4 — The Game Tree, the neighboring tiles are searched in the following order:
North, NorthEast, SouthEast, South, SouthWest, and NorthWest.

Can you use the query functionality from 4 — The Game Tree
to implement this evaluation of the game tree?

 */