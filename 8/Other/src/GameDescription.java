import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class to represent deserializations of Game Description JSON objects. Used for integration
 * testing task of milestone 8. Interpretation of the data structure of a Game Description in
 * JSON can be found below.
 * {
 * "row"     : Natural in [2,5],
 * "column"  : Natural in [2,5],
 * "players" : [[String, D], ,,,, [String, D]],
 * "fish"    : Natural in [1,5]
 * }
 */
public class GameDescription {
  private int row;
  private int column;
  private ArrayList<ArrayList> players;
  private int fish;

  /**
   * Returns the number of rows for this game's board.
   * @return The number of rows for the uniform board to be made with.
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the number of columns for this game's board.
   * @return The number of columns for the uniform board to be made with.
   */
  public int getColumn() {
    return column;
  }

  /**
   * Returns the number of fish for this game's board.
   * @return The number of fish for every tile in the uniform board to be made with.
   */
  public int getFish() {
    return fish;
  }

  /**
   * Return the players attached to this referee's game.
   * @return The players as a list of ExternalPlayer objects.
   */
  public ArrayList<ExternalPlayer> getPlayers() {
    ArrayList<ExternalPlayer> retList = new ArrayList<>();
    for (ArrayList list : players) {
      String str = (String) list.get(0);
      int depth = (int) Math.round((double) list.get(1));
      ExternalPlayer ep = new ExternalPlayer(str, depth);
      retList.add(ep);
    }
    return retList;
  }
}
