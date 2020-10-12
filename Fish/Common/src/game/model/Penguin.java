package game.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class to represent a penguin.
 * TODO: Improve description
 */
public class Penguin {
  private final PenguinColor color;
  static Image PENGUIN_RED = null;
  static Image PENGUIN_WHITE = null;
  static Image PENGUIN_BROWN = null;
  static Image PENGUIN_BLACK = null;

  /**
   * Constructor for a Penguin with its assigned color.
   * If the static image representations of the penguins are not yet
   * @param pc Color of the penguin.
   */
  public Penguin(PenguinColor pc) {
    this.color = pc;

    BufferedImage image;

    if (PENGUIN_RED == null || PENGUIN_WHITE == null || PENGUIN_BLACK == null
            || PENGUIN_BROWN == null) {
      // Fish/Common/
      File pathRed = new File("resources/redpenguin.png");
      File pathWhite = new File("resources/whitepenguin.png");
      File pathBrown = new File("resources/brownpenguin.png");
      File pathBlack = new File("resources/blackpenguin.png");

      try {
        image = ImageIO.read(pathRed);
        PENGUIN_RED = image;
        image = ImageIO.read(pathWhite);
        PENGUIN_WHITE = image;
        image = ImageIO.read(pathBrown);
        PENGUIN_BROWN = image;
        image = ImageIO.read(pathBlack);
        PENGUIN_BLACK = image;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Returns the color of the penguin.
   * @return the penguin's color
   */
  public PenguinColor getColor() {
    return color;
  }

  public void render(BoardPosition bp, Graphics g) {
    int shift = bp.getRow() % 2;
    int shiftRight = (int) (shift * 2.0 / 3.0 * Tile.WIDTH);

    int xcoord = (int) (Tile.COLUMN_WIDTH * bp.getCol() + 1.0 / 3.0 * Tile.WIDTH + shiftRight)
            + Tile.R_OFFSET;
    int ycoord = (int) (bp.getRow() / 2.0 * Tile.HEIGHT + Tile.D_OFFSET) + Tile.HEIGHT / 2;

    switch (this.color) {
      case WHITE:
        g.drawImage(PENGUIN_WHITE, xcoord, ycoord, null);
        break;
      case BLACK:
        g.drawImage(PENGUIN_BLACK, xcoord, ycoord, null);
        break;
      case RED:
        g.drawImage(PENGUIN_RED, xcoord, ycoord, null);
        break;
      case BROWN:
        g.drawImage(PENGUIN_BROWN, xcoord, ycoord, null);
        break;
      default:
    }
  }

  /**
   * Enum for colors of a penguin.
   */
  public enum PenguinColor {
    RED,
    WHITE,
    BROWN,
    BLACK
  }
}
