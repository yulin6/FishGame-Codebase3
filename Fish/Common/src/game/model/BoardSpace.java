package game.model;

import java.awt.*;

/**
 * Interface for spaces on a board.
 * A BoardSpace is either a Tile or a Hole. A board consists of BoardSpaces.
 */
public interface BoardSpace {

  /**
   * Returns the number of fish on the BoardSpace.
   * @return number of fish
   */
  int getNumFish();

  /**
   * Determines if a BoardSpace is a hole.
   * @return true if the BoardSpace is a hole, else false
   */
  boolean isHole();

  /**
   * Renders this BoardSpace, given its position on a board and the graphics component
   * with which to render it.
   * @param p The position of the BoardSpace on the board to render
   * @param g the Graphics component of a BoardPanel to render the tile with
   */
  void render(BoardPosition p, Graphics g);

}