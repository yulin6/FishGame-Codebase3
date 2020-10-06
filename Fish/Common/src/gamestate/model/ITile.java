package gamestate.model;

import gamestate.view.BoardPanel;

/**
 * Interface for Tile type objects.
 */
public interface ITile {

  /**
   * Create a new Tile with the given number of fish on it.
   * @param numFish Number of fish on the tile
   * @return The built Tile
   */
  Tile buildTile(int numFish);

  /**
   * Sets this Tile as occupied.
   */
  void setOccupied();

  /**
   * Sets this Tile as unoccupied.
   */
  void setUnoccupied();

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

}
