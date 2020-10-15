import java.util.List;

/**
 * Class to represent the Board-Posn type objects in Java after being deserialized
 * from JSON.
 * Board-Posn objects in JSON form are { "position" : Position, "board" : Board}
 * where Position is a JSON array of 2 natural numbers
 * and Board is a JSON array of numbers from 0 to 5.
 */
public class BoardPosn {
  private List<Integer> position;
  private List<List<Integer>> board;

  /**
   * Returns the 2-integer position array Java equivalent of the JSON Position.
   * @return The list of values.
   */
  public List<Integer> getPosition() {
    return position;
  }

  /**
   * Getter for the Board list-of-lists.
   * @return Returns the Board Java-equivalent of the JSON array of arrays.
   */
  public List<List<Integer>> getBoard() {
    return board;
  }
}
