import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import game.model.Action;
import game.model.Board;
import game.model.BoardPosition;
import game.model.GameState;
import game.model.GameTree;
import game.model.Move;
import game.model.Pass;
import game.model.Penguin;
import game.model.Player;
import player.Strategy;

/**
 * Xstrategy testing harness for milestone 6. Accepts a Depth-State JSON input where a Depth is a
 * natural number in [1,2] and State is as previously defined in prior milestones. The expected
 * output is the Action that the strategy component (Strategy.java in the Player module of Fish)
 * should output according to its algorithm. Action is as previously defined.
 */
public class Xstrategy {

  /**
   * Main of Xstrategy that accepts JSON input from STDIN and outputs the JSON Action object as
   * appropriate to STDOUT.
   * @param args unused arguments to main
   */
  public static void main(String[] args) {
    Gson gson = new GsonBuilder().create();
    String rawJson = parseInput(System.in);
    JsonArray arr = (JsonArray) JsonParser.parseString(rawJson);
    int depth = arr.get(0).getAsInt();
    if (depth != 1 && depth != 2) {
      throw new IllegalArgumentException("Invalid depth given!");
    }
    JsonObject s = (JsonObject) arr.get(1);
    State state = gson.fromJson(s, State.class);

    Board b = state.getBoard();
    HashSet<Player> players = state.getPlayers();
    GameState gs = new GameState(players, b);
    List<TestPlayer> tplayers = state.getTestPlayers();
    LinkedHashMap<Penguin.PenguinColor, Player> colorPlayers = new LinkedHashMap<>();
    for (Player p : players) {
      colorPlayers.put(p.getColor(), p);
    }
    for (TestPlayer tp : tplayers) {
      colorPlayers.get(tp.getColor()).addFish(tp.getScore());
      for (BoardPosition bp : tp.getPlaces()) {
        gs.placeAvatar(bp, colorPlayers.get(tp.getColor()));
      }
    }
    GameTree gt = new GameTree(gs);

    Strategy strategy = new Strategy();
    Action a = strategy.getMinMaxAction(gt, depth);


    if (a instanceof Pass) {
        System.out.println(gson.toJson(false));
    }
    else {
      Move m = (Move) a;
      JsonArray out = new JsonArray();
      JsonArray src = new JsonArray();
      JsonArray dst = new JsonArray();
      src.add(m.getStart().getRow());
      src.add(m.getStart().getCol());
      dst.add(m.getDestination().getRow());
      dst.add(m.getDestination().getCol());
      out.add(src);
      out.add(dst);

      System.out.println(gson.toJson(out));
    }

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
}