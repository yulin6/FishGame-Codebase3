import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

import game.model.Action;
import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.Move;
import game.model.Pass;
import game.model.Penguin;
import game.model.Player;
import state.State;
import state.TestPlayer;

public class Xstate {

  public static void main(String[] args) {
    Gson gson = new GsonBuilder().create();
    String rawJson = parseInput(System.in);

    State s = gson.fromJson(rawJson, State.class);

    List<TestPlayer> tplayers = s.getTestPlayers();
    LinkedHashSet<Player> realPlayers = s.getPlayers();
    LinkedHashMap<Penguin.PenguinColor, Player> colorPlayers = new LinkedHashMap<>();
    for (Player p : realPlayers) {
      colorPlayers.put(p.getColor(), p);
    }
    Board b = s.getBoard();

    if (realPlayers.isEmpty()) {
      System.out.println(gson.toJson(false));
      return;
    }

    GameState gs = new GameState(realPlayers, b);
    for (TestPlayer tp : tplayers) {
      colorPlayers.get(tp.getColor()).addFish(tp.getScore());
      for (BoardPosition bp : tp.getPlaces()) {
        gs.placeAvatar(bp, colorPlayers.get(tp.getColor()));
      }
    }

    if (tplayers.isEmpty()) {
      System.out.println(gson.toJson(false));
      return;
    }
    Player mover = colorPlayers.get(tplayers.get(0).getColor());
    if (tplayers.get(0).getPlaces().isEmpty()) {
      System.out.println(gson.toJson(false));
      return;
    }
    BoardPosition start = tplayers.get(0).getPlaces().get(0);
    List<Action> moves = gs.getPossibleActions();

    if (moves.contains(new Pass(mover))) {
      System.out.println(gson.toJson(false));
      return;
    } else {
      Move north = new Move(generateMove(start, DIRECTION.N), start, mover);
      Move northeast = new Move(generateMove(start, DIRECTION.NE), start, mover);
      Move southeast = new Move(generateMove(start, DIRECTION.SE), start, mover);
      Move south = new Move(generateMove(start, DIRECTION.S), start, mover);
      Move southwest = new Move(generateMove(start, DIRECTION.SW), start, mover);
      Move northwest = new Move(generateMove(start, DIRECTION.NW), start, mover);
      if (moves.contains(north)) {
        north.perform(gs);
      } else if (moves.contains(northeast)) {
        northeast.perform(gs);
      } else if (moves.contains(southeast)) {
        southeast.perform(gs);
      } else if (moves.contains(south)) {
        south.perform(gs);
      } else if (moves.contains(southwest)) {
        southwest.perform(gs);
      } else if (moves.contains(northwest)) {
        northwest.perform(gs);
      }
    }

    // get here - have performed a move
    State outState = new State(gs, realPlayers, b);
    System.out.println(gson.toJson(outState));

  }

  /**
   * Helper method to parse all JSON input from an input stream
   * and create the appropriate JSON objects to return.
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

  public static BoardPosition generateMove(BoardPosition bp, DIRECTION d) {
    VERTICAL vertical;
    HORIZONTAL horizontal;
    switch (d) {
      case N:
        vertical = VERTICAL.UP;
        horizontal = HORIZONTAL.ZERO;
        break;
      case S:
        vertical = VERTICAL.DOWN;
        horizontal = HORIZONTAL.ZERO;
        break;
      case NE:
        vertical = VERTICAL.UP;
        horizontal = HORIZONTAL.RIGHT;
        break;
      case NW:
        vertical = VERTICAL.UP;
        horizontal = HORIZONTAL.LEFT;
        break;
      case SE:
        vertical = VERTICAL.DOWN;
        horizontal = HORIZONTAL.RIGHT;
        break;
      case SW:
        vertical = VERTICAL.DOWN;
        horizontal = HORIZONTAL.LEFT;
        break;
      default:
        throw new IllegalArgumentException("check cases");
    }

    int nextRow = 0, nextCol = 0;
    switch (vertical) {
      case UP:
        nextRow = bp.getRow() - 2;
        break;
      case DOWN:
        nextRow = bp.getRow() + 2;
        break;
      default:
        break;
    }
    switch(horizontal) {
      case ZERO:
        nextCol = bp.getCol();
        break;
      case LEFT:
        nextCol = (bp.getRow() % 2 == 0) ? bp.getCol() - 1 : bp.getCol();
        nextRow += (nextRow - bp.getRow()) / -2;
        break;
      case RIGHT:
        nextCol = (bp.getRow() % 2 == 1) ? bp.getCol() + 1 : bp.getCol();
        nextRow += (nextRow - bp.getRow()) / -2;
        break;
      default:
        break;
    }
    return new BoardPosition(nextRow, nextCol);
  }

  public enum DIRECTION {
    N,
    NE,
    SE,
    S,
    SW,
    NW
  }

  public enum VERTICAL {
    UP,
    DOWN
  }

  private enum HORIZONTAL {
    ZERO,
    LEFT,
    RIGHT
  }


}