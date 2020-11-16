import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import game.model.Board;
import game.model.GameState;
import game.model.IBoard;
import game.model.Penguin;
import game.model.Player;
import player.FixedDepthPlayerComponent;
import player.IPlayerComponent;
import referee.Referee;

/**
 * Xref integration testing harness for milestone 8. Consumes JSON from STDIN and outputs results
 * to STDOUT. Expected input is a "Game Description" which is a
 * {
 *   "row" : Natural in [2,5],
 *   "column": Natural in [2,5],
 *   "players" : [[String, Depth], ..., [String, Depth]]
 *   "fish"    : Natural in [1,5]
 * }
 * where Depth is as specified in a previous integration task, a Natural in [1,2].
 * The "players" array is length [2,4].
 * The Strings in "players" are pairwise distinct (no repeats among all).
 * Players in the array are in ascending order of age.
 * Runs a game on a uniform board of "row" rows, "column" columns, and "fish" fish on every tile.
 * Every player uses the predefined Strategy strategy from milestone 5 with the depth specified
 * in the associated array from "players".
 * Expected output is [String, ... String] of the winners of the game, in ascending lexographical
 * order.
 */
public class Xref {

  /**
   * Main function to handle an integration test as specified above.
   * @param args Unused arguments parameter.
   */
  public static void main(String[] args) {
    Gson gson = new GsonBuilder().create();
    String rawJson = parseInput(System.in);

    GameDescription gDesc = gson.fromJson(rawJson, GameDescription.class);
    int rows = gDesc.getRow();
    int cols = gDesc.getColumn();
    ArrayList<ExternalPlayer> externalPlayers = gDesc.getPlayers();
    int fish = gDesc.getFish();

    IBoard uniformBoard = new Board(rows, cols, fish);
    HashSet<Player> players = new HashSet<>();
    List<IPlayerComponent> playerComponents = new ArrayList<>();
    ArrayList<Penguin.PenguinColor> availableColors =
            new ArrayList<>(Arrays.asList(Penguin.PenguinColor.values()));
    HashMap<IPlayerComponent, String> playerToName = new HashMap<>();

    for (ExternalPlayer extPlayer : externalPlayers) {
      int age = externalPlayers.indexOf(extPlayer);
      Penguin.PenguinColor color = availableColors.remove(0);
      Player p = new Player(age, color);
      players.add(p);
      IPlayerComponent pc = new FixedDepthPlayerComponent(age, extPlayer.getDepth(), color);
      playerComponents.add(pc);
      playerToName.put(pc, extPlayer.getName());
    }

    GameState gs = new GameState(players, uniformBoard);
    Referee ref = new Referee(gs, playerComponents);
    ref.runGame();
    List<IPlayerComponent> winners = ref.getWinners();

    List<String> jsonWinners = new ArrayList<>();
    for (IPlayerComponent winner : winners) {
      jsonWinners.add(playerToName.get(winner));
    }
    Collections.sort(jsonWinners);
    System.out.println(gson.toJson(jsonWinners));
    System.exit(0);
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
