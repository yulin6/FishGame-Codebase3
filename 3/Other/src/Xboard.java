import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import game.model.Board;
import game.model.BoardPosition;

public class Xboard {

  public static void main(String[] args) {
    String rawJson = parseInput(System.in);

    Gson gson = new Gson();

    BoardPosn bp = gson.fromJson(rawJson, BoardPosn.class);

    int numRows = bp.getBoard().size();
    int numCols = bp.getBoard().get(0).size();

    Board b = new Board(numRows, numCols, bp.getBoard());
    BoardPosition pos = new BoardPosition(bp.getPosition().get(0), bp.getPosition().get(1));

    System.out.println(b.getValidMoves(pos, new ArrayList<>()).size());

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

}