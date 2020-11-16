/**
 * Class to represent the [String, Depth] arrays within a Game Description's "players" array
 * after being deserialized from JSON.
 */
public class ExternalPlayer {
  private final String name;
  private final int depth;

  /**
   * Constructor for an ExternalPlayer taking explicit arguments for the fields.
   * @param name The name to assign.
   * @param depth The probing depth to assign.
   */
  public ExternalPlayer(String name, int depth) {
    this.name = name;
    this.depth = depth;
  }

  /**
   * Gets the name of this ExternalPlayer.
   * @return the name as a String.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the depth to which this ExternalPlayer should be searching.
   * @return the depth to use for finding minimax actions.
   */
  public int getDepth() {
    return depth;
  }
}
