package gamestate.model;

import java.awt.*;

import gamestate.view.BoardPanel;

/**
 * Interface for Tile type objects.
 */
public interface ITile {

  /**
   * Gets the BoardPosition of this tile.
   * @return the position representing the tile's location.
   */
  BoardPosition getPosition();

  /**
   * Sets this Tile as occupied.
   */
  void setOccupied();

  /**
   * Sets this Tile as unoccupied.
   */
  void setUnoccupied();

  /**
   * Sets this Tile as a hole
   */
  void setHole();

  /**
   * Checks if this tile is a hole in the board
   * @return true if hole, else false
   */
  boolean isHole();

  /**
   * Returns the number of fish on the Tile.
   * @return number of fish
   */
  int getNumFish();

  /**
   * Returns the occupied status of the Tile.
   * @return true if occupied, else false
   */
  boolean isOccupied();

  /**
   * Renders this tile.
   * @param bp the BoardPanel to render the tile on
   */
  void render(BoardPanel bp, Graphics g);

}
