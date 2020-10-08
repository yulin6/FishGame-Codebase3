package gamestate.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gamestate.view.BoardPanel;

/**
 *
 */
public class Tile implements ITile {
  public static final int HEIGHT = 150;
  public static final int WIDTH = 150;
  public static final double COLUMN_WIDTH = 4.0/3.0 * WIDTH;
  public static final int R_OFFSET = 20;
  public static final int D_OFFSET = 20;

  static Image FISH_ICON = null;
  final int FISH_ICON_HEIGHT = 20;

  int fish;
  TileStatus occupant;
  boolean occupied;
  BoardPosition p;

  /**
   * Constructs a Tile given a number of fish to be on it.
   * @param numFish Number of fish contained on the tile
   */
  private Tile(int numFish, BoardPosition p) {
    BufferedImage image;
    if (FISH_ICON == null) {
      try {
        File pathToFishIcon = new File("Fish/Common/src/assets/fish33x20.png");
        System.out.println(pathToFishIcon.getAbsolutePath());
        image = ImageIO.read(pathToFishIcon);
        FISH_ICON = image;
      } catch (IOException e) {
        System.out.println("Error loading fish icon image");
        e.printStackTrace();
      }
    }
    this.fish = numFish;
    occupant = TileStatus.NONE;
    occupied = false;
    this.p = p;
  }

  public static class TileBuilder {
    private int fish;
    private BoardPosition p;

    TileBuilder() {
    }

    public TileBuilder setFish(int numFish) {
      this.fish = numFish;
      return this;
    }

    public TileBuilder setPosition(BoardPosition p) {
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
    if (isHole()) {
      return;
    }
    drawHexagon(g);
    int shift = p.getRow() % 2;
    int shiftRight = (int) (shift * 2.0/3.0 * WIDTH);

    for (int i = 0; i < fish; i++) {
      g.drawImage(FISH_ICON,
              (int) (COLUMN_WIDTH * p.getCol() + 1.0/3.0 * WIDTH + shiftRight) + R_OFFSET,
              (int) (p.getRow() / 2.0 * HEIGHT + D_OFFSET) + FISH_ICON_HEIGHT * i,
              null);
    }
  }

  private void drawHexagon(Graphics g) {
    Polygon hex = new Polygon();

    //Determines if this tile is offset
    int shift = p.getRow() % 2; // 0 if left-half of column, 1 if right-half
    int shiftRight = (int) (shift * 2.0/3.0 * WIDTH);

    PixelPosition topLeftPt = topLeftVertex(shiftRight);
    PixelPosition topRightPt = topRightVertex(shiftRight);
    PixelPosition midRightPt = midRightVertex(shiftRight);
    PixelPosition botRightPt = botRightVertex(shiftRight);
    PixelPosition botLeftPt = botLeftVertex(shiftRight);
    PixelPosition midLeftPt = midLeftVertex(shiftRight);

    hex.addPoint(topLeftPt.getX(), topLeftPt.getY());
    hex.addPoint(topRightPt.getX(), topRightPt.getY());
    hex.addPoint(midRightPt.getX(), midRightPt.getY());
    hex.addPoint(botRightPt.getX(), botRightPt.getY());
    hex.addPoint(botLeftPt.getX(), botLeftPt.getY());
    hex.addPoint(midLeftPt.getX(), midLeftPt.getY());

    g.setColor(Color.LIGHT_GRAY);
    g.fillPolygon(hex);
    g.setColor(Color.BLACK);
    g.drawPolygon(hex);
  }

  /**
   * Calculates the position of the top left vertex of the hexagon representing this tile.
   * @param shiftRight The amount to shift the x coordinate of this position right by.
   * @return The (x,y) coordinates of the position as a PixelPosition.
   */
  private PixelPosition topLeftVertex(int shiftRight) {
    return new PixelPosition(
            (int) (COLUMN_WIDTH * p.getCol() + 1.0/3.0 * WIDTH + shiftRight) + R_OFFSET,
            (int) (p.getRow() / 2.0 * HEIGHT + D_OFFSET));
  }

  /**
   * Calculates the position of the top right vertex of the hexagon representing this tile.
   * @param shiftRight The amount to shift the x coordinate of this position right by.
   * @return The (x,y) coordinates of the position as a PixelPosition.
   */
  private PixelPosition topRightVertex(int shiftRight) {
    return new PixelPosition(
            (int) (COLUMN_WIDTH * p.getCol() + 2.0/3.0 * WIDTH + shiftRight) + R_OFFSET,
            (int) (p.getRow() / 2.0 * HEIGHT + D_OFFSET));
  }

  /**
   * Calculates the position of the middle right vertex of the hexagon representing this tile.
   * @param shiftRight The amount to shift the x coordinate of this position right by.
   * @return The (x,y) coordinates of the position as a PixelPosition.
   */
  private PixelPosition midRightVertex(int shiftRight) {
    return new PixelPosition(
            (int) (COLUMN_WIDTH * p.getCol() + WIDTH + shiftRight) + R_OFFSET,
            (int) ((p.getRow() + 1) / 2.0 * HEIGHT + D_OFFSET));
  }

  /**
   * Calculates the position of the bottom right vertex of the hexagon representing this tile.
   * @param shiftRight The amount to shift the x coordinate of this position right by.
   * @return The (x,y) coordinates of the position as a PixelPosition.
   */
  private PixelPosition botRightVertex(int shiftRight) {
    return new PixelPosition(
            (int) (COLUMN_WIDTH * p.getCol() + 2.0/3.0 * WIDTH + shiftRight) + R_OFFSET,
            (int) (p.getRow() * HEIGHT / 2.0 + HEIGHT + D_OFFSET));
  }

  /**
   * Calculates the position of the bottom left vertex of the hexagon representing this tile.
   * @param shiftRight The amount to shift the x coordinate of this position right by.
   * @return The (x,y) coordinates of the position as a PixelPosition.
   */
  private PixelPosition botLeftVertex(int shiftRight) {
    return new PixelPosition(
            (int) (COLUMN_WIDTH * p.getCol() + 1.0/3.0 * WIDTH + shiftRight) + R_OFFSET,
            (int) (p.getRow() * HEIGHT / 2.0 + HEIGHT + D_OFFSET));
  }

  /**
   * Calculates the position of the middle left vertex of the hexagon representing this tile.
   * @param shiftRight The amount to shift the x coordinate of this position right by.
   * @return The (x,y) coordinates of the position as a PixelPosition.
   */
  private PixelPosition midLeftVertex(int shiftRight) {
    return new PixelPosition(
            (int) (COLUMN_WIDTH * p.getCol() + shiftRight) + R_OFFSET,
            (int) ((p.getRow() + 1) / 2.0 * HEIGHT + D_OFFSET));
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
