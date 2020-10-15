package game.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class to represent a tile in a game of Fish.
 * A Tile has a number of fish on it as well as knowledge of its graphical
 * representation. Tiles do not have knowledge of their position.
 */
public class Tile implements BoardSpace {
  public static final int HEIGHT = 100;
  public static final int WIDTH = 100;
  public static final double COLUMN_WIDTH = 4.0/3.0 * WIDTH;
  public static final int R_OFFSET = 20;
  public static final int D_OFFSET = 20;

  static Image FISH_ICON = null;
  final int FISH_ICON_HEIGHT = 20;

  final int fish;

  /**
   * Constructs a Tile given a number of fish to be on it.
   * If resources for Tile graphics are uninitialized, also obtains them and initializes.
   * @param numFish Number of fish contained on the tile
   */
  public Tile(int numFish) {
    if (numFish < Board.MIN_FISH || numFish > Board.MAX_FISH) {
      throw new IllegalArgumentException("Cannot construct tile with this number of Fish.");
    }

    BufferedImage image;
    if (FISH_ICON == null) {
      try {
        File pathToFishIcon = new File("C:/Users/Derek/Documents/Northeastern/CS3500" +
                "/projects/oakwood/Fish/Common/resources/fish33x20.png"); // FIX THIS
        image = ImageIO.read(pathToFishIcon);
        FISH_ICON = image;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.fish = numFish;
  }

  @Override
  public boolean isHole() { return false; }

  @Override
  public int getNumFish() {
    return fish;
  }

  @Override
  public void render(BoardPosition p, Graphics g) {
    drawHexagon(p, g);
    drawFish(p, g);
  }

  private void drawHexagon(BoardPosition p, Graphics g) {
    Polygon hex = new Polygon();

    //Determines if this tile is offset
    int shift = p.getRow() % 2; // 0 if left-half of column, 1 if right-half
    int shiftRight = (int) (shift * 2.0/3.0 * WIDTH); //Calculation for the offset if row % 2 == 1

    //Calculate top left vertex and calculate in terms of width and height to determine the
    //pixel positions of the other vertices
    PixelPosition topLeftPt = topLeftVertex(p, shiftRight);
    int x = topLeftPt.getX();
    int y = topLeftPt.getY();

    PixelPosition topRightPt = new PixelPosition(x + WIDTH/3, y);
    PixelPosition midRightPt = new PixelPosition(x + (2 * WIDTH / 3), y + HEIGHT/2);
    PixelPosition botRightPt = new PixelPosition(x + WIDTH/3, y + HEIGHT);
    PixelPosition botLeftPt = new PixelPosition(x, y + HEIGHT);
    PixelPosition midLeftPt = new PixelPosition(x - WIDTH/3, y + HEIGHT/2);

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

  private void drawFish(BoardPosition p, Graphics g) {
    int shift = p.getRow() % 2;
    int shiftRight = (int) (shift * 2.0/3.0 * WIDTH);
    for (int i = 0; i < fish; i++) {
      g.drawImage(FISH_ICON,
              (int) (COLUMN_WIDTH * p.getCol() + WIDTH/3.0 + shiftRight) + R_OFFSET,
              (int) (p.getRow() / 2.0 * HEIGHT + D_OFFSET) + FISH_ICON_HEIGHT * i,
              null);
    }
  }

  /**
   * Calculates the position of the top left vertex of the hexagon representing this tile.
   * @param shiftRight The amount to shift the x coordinate of this position right by.
   * @return The (x,y) coordinates of the position as a PixelPosition.
   */
  private PixelPosition topLeftVertex(BoardPosition p, int shiftRight) {
    return new PixelPosition(
            (int) (COLUMN_WIDTH * p.getCol() + WIDTH/3.0 + shiftRight) + R_OFFSET,
            (int) (p.getRow() / 2.0 * HEIGHT + D_OFFSET));
  }
}
