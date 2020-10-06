package gamestate.model;

import gamestate.view.BoardPanel;

/**
 *
 */
public class Tile implements ITile {
  int fish;
  TileOccupant occupant;
  boolean occupied;

  /**
   * Constructs a Tile given a number of fish to be on it.
   * @param numFish Number of fish contained on the tile
   */
  private Tile(int numFish) {
    this.fish = numFish;
    occupant = TileOccupant.NONE;
    occupied = false;
  }

  @Override
  public Tile buildTile(int numFish) {
    return new Tile(numFish);
  }

  @Override
  public void setOccupied() {
    occupied = true;
  }

  /*
  For the future when Penguin has an implementation
  @Override
  public void setOccupied(Penguin p) {

  }
   */

  @Override
  public void setUnoccupied() {
    occupant = TileOccupant.NONE;
  }

  @Override
  public int getNumFish() {
    return 0;
  }

  @Override
  public boolean isOccupied() {
    return (occupant != TileOccupant.NONE);
  }

  /**
   * Enum to determine what's on the tile - nothing, or the color of a penguin.
   */
  public enum TileOccupant {
    RED,
    WHITE,
    BROWN,
    BLACK,
    NONE
  }
}
