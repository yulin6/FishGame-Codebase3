package gamestate.model;

import java.awt.*;

/**
 * Interface for spaces on a board.
 *
 * A BoardSpace is either a Tile or a Hole. A board contains BoardSpaces
 */
public interface BoardSpace {

  /**
   * Returns the number of fish on the Tile.
   * @return number of fish
   */
  int getNumFish();

  /**
   * Determines if a BoardSpace is a hole
   * @return boolean representing if a tile is a hole
   */
  boolean isHole();

  /**
   * Renders this tile.
   * @param p The position of the tile on the board that we are rendering
   * @param g the Graphics component of our BoardPanel to render the tile with
   */
  void render(BoardPosition p, Graphics g);

}
