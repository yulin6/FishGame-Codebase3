package gamestate.model;

import java.awt.*;

import gamestate.view.BoardPanel;

/**
 *
 */
public class Tile implements ITile {
  final int HEIGHT = 50;
  final int WIDTH = 50;

  int fish;
  TileStatus occupant;
  boolean occupied;
  Position p;

  /**
   * Constructs a Tile given a number of fish to be on it.
   * @param numFish Number of fish contained on the tile
   */
  private Tile(int numFish, Position p) {
    this.fish = numFish;
    occupant = TileStatus.NONE;
    occupied = false;
    this.p = p;
  }

  public static class TileBuilder {
    private int fish;
    private Position p;

    TileBuilder() {
    }

    public TileBuilder setFish(int numFish) {
      this.fish = numFish;
      return this;
    }

    public TileBuilder setPosition(Position p) {
      this.p = p;
      return this;
    }

    public Tile build() {
      return new Tile(fish, p);
    }
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
    occupant = TileStatus.NONE;
    occupied = false;
  }

  @Override
  public void setHole() { occupant = TileStatus.HOLE; }

  @Override
  public boolean isHole() { return (occupant == TileStatus.HOLE); }

  @Override
  public int getNumFish() {
    return fish;
  }

  @Override
  public boolean isOccupied() {
    return (occupant != TileStatus.NONE);
  }

  @Override
  public void render(BoardPanel bp, Graphics g) {
    //Zero-indexed: Evens on left, odds shifted to right

  }

  private void drawHexagon(BoardPanel bp, Graphics g) {
    Polygon hex = new Polygon();

    //Determines if this tile is offset
    int shift = p.getRow() % 2;
    //hex.addPoint(p.getRow(), );

    //LEFT HALF
    //(4/3)w * col + (1/3)w, (1/2)h [TL]
    //(4/3)w * col + (2/3)w, (1/2)h [TR]
    //(4/3)w * col + 1, (row + 1)/2 * h [MR]
    //(4/3)w * col, (row + 1)/2 * h [ML]
    //(4/3)w * col + (1/3)w, (r*h) / 2 [BL]
    //(4/3)w * col + (2/3)w, (r*h) / 2 [BR]

    //RIGHT HALF
    //SAME AS LEFT HALF, BUT SHIFT 2/3w OVER AND 1/2h DOWN

  }

  /**
   * Enum to determine the tile's status - a hole, no penguins, or the color of a penguin.
   */
  public enum TileStatus {
    RED,
    WHITE,
    BROWN,
    BLACK,
    NONE,
    HOLE
  }
}
