package gamestate.model;

/**
 * Class to represent positions in a Board.
 * BoardPositions have a row and a column value,
 * which correspond to array indices of the BoardSpace array in the Board.
 */
public class BoardPosition {
  private final int row;
  private final int col;

  /**
   * Constructs a new BoardPosition.
   * @param row The row at of the BoardPosition
   * @param col The column of the BoardPosition
   */
  public BoardPosition(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the row value of this BoardPosition.
   * @return the row as an integer value
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column value of this BoardPosition.
   * @return the column as an integer value
   */
  public int getCol() {
    return col;
  }

  /**
   * Checks equality of another object and this BoardPosition.
   * @param obj Other object to check equality with
   * @return True if the other object is a BoardPosition and the row and column of both are the
   * same, else false
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BoardPosition) {
      BoardPosition bp = (BoardPosition) obj;
      return (this.row == bp.getRow() && this.col == bp.getCol());
    } else {
      return false;
    }
  }
}
