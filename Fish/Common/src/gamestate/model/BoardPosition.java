package gamestate.model;

/**
 * Class to represent positions (of objects such as Tile(s)).
 */
public class BoardPosition {
  private int row;
  private int col;

  /**
   * Constructs a new Position.
   * @param row The row at of the Position
   * @param col The column of the Position
   */
  public BoardPosition(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the row value of this Position.
   * @return the row as an integer value
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column value of this Position.
   * @return the column as an integer value
   */
  public int getCol() {
    return col;
  }

}
